package io.l0neman.arscparser.core;

import io.l0neman.arscparser.type.*;
import io.l0neman.arscparser.util.objectio.ObjectInput;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class ArscParser {

  private long mIndex;
  private String[] globalStringPool;
  private String[] resTypeStringPool;
  private String[] typeNameStringPool;
  private Map<String, String> drawableNamePathMap = new HashMap<>();
  private int packageResId;
  private int stringTypeResId;
  private Map<Integer, String> idStringsMap = new HashMap<>();

  private void parseResTableType(ObjectInput objectInput) throws IOException {
    final ResTableHeader tableType = objectInput.read(ResTableHeader.class, mIndex);
    System.out.println("resource table header:");
    System.out.println(tableType);

    // 向下移动资源表头部的大小。
    mIndex += tableType.header.headerSize;
  }

  private void parseStringPool(ObjectInput objectInput) throws IOException {
    final long stringPoolIndex = mIndex;
    ResStringPoolHeader stringPoolHeader = objectInput.read(ResStringPoolHeader.class, stringPoolIndex);
    System.out.println("string pool header:");
    System.out.println(stringPoolHeader);

    StringPoolChunkParser stringPoolChunkParser = new StringPoolChunkParser();
    stringPoolChunkParser.parseStringPoolChunk(objectInput, stringPoolHeader, stringPoolIndex);

    System.out.println();
    System.out.println("string index array:");
    System.out.println(Arrays.toString(stringPoolChunkParser.getStringIndexArray()));

    System.out.println();
    System.out.println("style index array:");
    System.out.println(Arrays.toString(stringPoolChunkParser.getStyleIndexArray()));

    String[] stringPool = stringPoolChunkParser.getStringPool();

    if (globalStringPool == null) {
      globalStringPool = stringPool;
      System.out.println("Global String Pool");
    } else if (resTypeStringPool == null) {
      resTypeStringPool = stringPool;
      System.out.println("Res Type String Pool");
    } else {
      typeNameStringPool = stringPool;
      System.out.println("Type Name String Pool");
    }

    System.out.println();
    System.out.println("string pool:");
    System.out.println(Arrays.toString(stringPool));

    System.out.println();
    System.out.println("style pool:");
    final List<ResStringPoolSpan>[] stylePool = stringPoolChunkParser.getStylePool();

    System.out.println(Arrays.toString(stylePool));

    System.out.println();
    System.out.println("style detail:");
    for (List<ResStringPoolSpan> spans : stylePool) {
      System.out.println("---------");
      for (ResStringPoolSpan span : spans) {
        System.out.println(stringPool[span.name.index]);
      }
    }

    // 向下移动字符串池的大小。
    mIndex += stringPoolHeader.header.size;
  }

  private void parseTablePackageType(ObjectInput objectInput) throws IOException {
    final long tablePackageIndex = mIndex;
    final ResTablePackage tablePackage = objectInput.read(ResTablePackage.class, tablePackageIndex);

    packageResId = tablePackage.id;

    System.out.println("table package type:");
    System.out.println(tablePackage);

    // 向下移动资源表元信息头部的大小。
    mIndex += tablePackage.header.headerSize;
  }

  private void parseTableTypeSpecType(ObjectInput objectInput) throws IOException {
    final long typeSpecIndex = mIndex;
    ResTableTypeSpec tableTypeSpec = objectInput.read(ResTableTypeSpec.class, typeSpecIndex);

    System.out.println("table type spec type:");
    System.out.println(tableTypeSpec);

    int[] entryArray = TableTypeChunkParser.parseSpecEntryArray(objectInput, tableTypeSpec, typeSpecIndex);

    System.out.println();
    System.out.println("table type spec type entry array:");
    System.out.println(Arrays.toString(entryArray));

    // 向下移动资源表类型规范内容的大小。
    mIndex += tableTypeSpec.header.size;
  }

  private void parseTableTypeType(ObjectInput objectInput) throws IOException {
    final long tableTypeIndex = mIndex;
    final ResTableType tableType = objectInput.read(ResTableType.class, tableTypeIndex);

    System.out.println("table type type:");
    String typeName = resTypeStringPool[tableType.id - 1];
    System.out.println("type name: " + typeName);

    boolean isDrawable = false;
    if ("drawable".equals(typeName))
      isDrawable = true;

    boolean isString = false;
    if ("string".equals(typeName)) {
        stringTypeResId = tableType.id;
        isString = true;
    }

    System.out.println(tableType);

    int[] offsetArray = TableTypeChunkParser.parseTypeOffsetArray(objectInput, tableType, tableTypeIndex);

    System.out.println();
    System.out.println("offset array:");
    System.out.println(Arrays.toString(offsetArray));

    final long tableEntryIndex = tableTypeIndex + tableType.entriesStart;

    for (int i = 0; i < offsetArray.length; i++) {
      final long entryIndex = offsetArray[i] + tableEntryIndex;
      final ResTableEntry tableEntry = objectInput.read(ResTableEntry.class, entryIndex);

      System.out.println();
      System.out.println("table type type entry " + i + ":");
      System.out.println("header: " + tableEntry);
      String entryName = typeNameStringPool[tableEntry.key.index];
      System.out.println("entry name: " + entryName);

      if ((tableEntry.flags & ResTableEntry.FLAG_COMPLEX) == 1) {
        // parse ResTable_map
        final ResTableMapEntry tableMapEntry = objectInput.read(ResTableMapEntry.class, entryIndex);

        System.out.println(tableMapEntry);

        int index = 0;

        for (int j = 0; j < tableMapEntry.count; j++) {
          final long tableMapIndex = index + entryIndex + tableMapEntry.size;

          ResTableMap tableMap = objectInput.read(ResTableMap.class, tableMapIndex);
          System.out.println("table map " + j + ":");
          System.out.println(tableMap);

          index += ObjectInput.sizeOf(ResTableMap.class);
        }
      } else {
        // parse Res_value
        final int entrySize = ObjectInput.sizeOf(ResTableEntry.class);
        final ResValue value = objectInput.read(ResValue.class, entryIndex + entrySize);

        System.out.println(value);
        if (value.dataType == ResValue.TYPE_STRING) {
          String data = globalStringPool[value.data];
          System.out.println("value string: " + data);
          if (isDrawable)
            drawableNamePathMap.put(entryName, data);

          if (isString) {
              int resId = getResId(packageResId, stringTypeResId, i);
              idStringsMap.put(resId, data);
          }
        }
      }
    }

    mIndex += tableType.header.size;
  }

  private int getResId(int packageResId, int typeResId, int entryId) {
    return (packageResId << 24) | (typeResId << 16) | entryId;
  }

  public Map<Integer, String> getIdStringsMap() {
      return idStringsMap;
  }

  private void parse(ObjectInput objectInput) throws IOException {
    while (!objectInput.isEof(mIndex)) {
      ResChunkHeader header = objectInput.read(ResChunkHeader.class, mIndex);

      System.out.println();
      System.out.println("================================ " + ResourceTypes.nameOf(header.type) +
          " ================================");
      switch (header.type) {
        case ResourceTypes.RES_TABLE_TYPE:
          parseResTableType(objectInput);
          break;

        case ResourceTypes.RES_STRING_POOL_TYPE:
          parseStringPool(objectInput);
          break;

        case ResourceTypes.RES_TABLE_PACKAGE_TYPE:
          parseTablePackageType(objectInput);
          break;

        case ResourceTypes.RES_TABLE_TYPE_SPEC_TYPE:
          parseTableTypeSpecType(objectInput);
          break;

        case ResourceTypes.RES_TABLE_TYPE_TYPE:
          parseTableTypeType(objectInput);
          break;

        default:
      }
    }
  }

  private static void closeQuietly(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException ignore) {
      } catch (RuntimeException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void parse(String file) throws IOException{
    mIndex = 0;
    globalStringPool = null;
    ObjectInput objectInput = null;

    try {
      objectInput = new ObjectInput(file, false);
      parse(objectInput);
    } finally {
      closeQuietly(objectInput);
    }
  }
}
