package io.l0neman.arscparser.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@SuppressWarnings("ALL")
public class ClassUtils {
  private static final int MAX_CACHE_CLASS = 20;

  private static SimpleLru<Class<?>, Field[]> sFullDeclaredFieldsMap = new SimpleLru<>(MAX_CACHE_CLASS);
  private static SimpleLru<Class<?>, Integer> sClassSizeCache = new SimpleLru<>(20);

  public static Field[] fullDeclaredFields(Class<?> clazz) {
    Field[] fullDeclaredFields = sFullDeclaredFieldsMap.get(clazz);
    if (fullDeclaredFields == null) {
      fullDeclaredFields = buildFullDeclaredFields(clazz);
      sFullDeclaredFieldsMap.put(clazz, fullDeclaredFields);
    }

    return fullDeclaredFields;
  }

  private static Field[] buildFullDeclaredFields(Class<?> clazz) {
    List<Field> fullFields = new ArrayList<>();
    Field[] fields = clazz.getDeclaredFields();

    Class<?> parent = clazz.getSuperclass();
    while (parent != Object.class) {
      final Field[] parentFields = parent.getDeclaredFields();
      fullFields.addAll(Arrays.asList(parentFields));
      parent = parent.getSuperclass();
    }

    fullFields.addAll(Arrays.asList(fields));
    return (Field[]) fullFields.toArray();
  }

  public static int sizeOf(Class<?> clazz) {
    // 不支持数组类型，因为无法获取数组长度。
    if (clazz.isArray()) {
      throw new IllegalArgumentException("not support array type: " + clazz);
    }

    // 计算基本类型大小。
    if (clazz == byte.class || clazz == Byte.class) {
      return Byte.BYTES;
    }

    if (clazz == char.class || clazz == Character.class) {
      return Byte.BYTES;
    }

    if (clazz == short.class || clazz == Short.class) {
      return Short.BYTES;
    }

    if (clazz == int.class || clazz == Integer.class) {
      return Integer.BYTES;
    }

    if (clazz == float.class || clazz == Float.class) {
      return Float.BYTES;
    }

    if (clazz == double.class || clazz == Double.class) {
      return Double.BYTES;
    }

    if (clazz == long.class || clazz == Long.class) {
      return Long.BYTES;
    }

    final Integer cacheSize = sClassSizeCache.get(clazz);
    if (cacheSize != null) { return cacheSize; }

    int size = 0;
    Field[] fieldInfoList = fullDeclaredFields(clazz);
    for (Field field : fieldInfoList) {
      final Class<?> fieldType = field.getType();

      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }

      // 处理数组成员类型。
      if (fieldType.isArray()) {
        final Class<?> componentType = field.getType().getComponentType();
        size += sizeOf(componentType) * getArrayFieldLength(field, clazz);
        continue;
      }

      size += sizeOf(fieldType);
    }

    sClassSizeCache.put(clazz, size);
    return size;
  }

  public static int sizeOfUnion(Class<?> clazz) {

    if (clazz.isPrimitive()) {
      return sizeOf(clazz);
    }

    Field[] fieldInfoList = fullDeclaredFields(clazz);

    // 取出 union 类型。
    int[] typeSize = new int[fieldInfoList.length];
    for (int i = 0; i < typeSize.length; i++) {
      final Field field = fieldInfoList[i];
      final Class<?> fieldType = field.getType();

      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }

      // 处理数组成员类型。
      if (fieldType.isArray()) {
        typeSize[i] = sizeOf(fieldType.getComponentType()) * getArrayFieldLength(field, clazz);
        continue;
      }

      typeSize[i] = sizeOf(fieldType);
    }

    // 得到字节占用（最大成员的大小）。
    Arrays.sort(typeSize);
    return typeSize[typeSize.length - 1];
  }

  private static <T> T newObject(Class<T> target) {
    try {
      return target.newInstance();
    } catch (Exception e) {
      throw new IllegalArgumentException("Must implement the default constructor.", e);
    }
  }

  private static int getArrayFieldLength(Field field, Class<?> extra) {
    final Object helper = newObject(extra);
    try {
      final Object array = field.get(helper);
      final int length = Array.getLength(array);
      if (length == 0) {
        throw new IllegalArgumentException("array length is zero: [" + field.getType().getComponentType());
      }
      return length;
    } catch (Exception e) {
      throw new IllegalArgumentException("error", e);
    }
  }

  private static final class SimpleLru<K, V> {
    private final LinkedHashMap<K, V> lruMap;

    SimpleLru(final int max) {
      this.lruMap = new LinkedHashMap<K, V>(
          (int) (Math.ceil(max / 0.75F)) + 1, 0.75F, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
          return size() > max;
        }
      };
    }

    V get(K key) {
      return lruMap.get(key);
    }

    void put(K key, V value) {
      lruMap.put(key, value);
    }

    void clear() {
      lruMap.clear();
    }
  }
}
