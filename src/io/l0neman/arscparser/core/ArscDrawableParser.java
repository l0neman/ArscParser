package io.l0neman.arscparser.core;

import io.l0neman.arscparser.type.*;
import io.l0neman.arscparser.util.objectio.ObjectInput;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class ArscDrawableParser {
  private long mIndex;
  private String[] globalStringPool;
  private String[] resTypeStringPool;
  private String[] typeNameStringPool;
  private Map<String, String> drawableNamePathMap = new HashMap<>();

  private void parseResTableType(ObjectInput objectInput) throws IOException {
    final ResTableHeader tableType = objectInput.read(ResTableHeader.class, mIndex);

    // 向下移动资源表头部的大小。
    mIndex += tableType.header.headerSize;
  }

  private void parseStringPool(ObjectInput objectInput) throws IOException {
    final long stringPoolIndex = mIndex;
    ResStringPoolHeader stringPoolHeader = objectInput.read(ResStringPoolHeader.class, stringPoolIndex);

    StringPoolChunkParser stringPoolChunkParser = new StringPoolChunkParser();
    stringPoolChunkParser.parseStringPoolChunk(objectInput, stringPoolHeader, stringPoolIndex);

    String[] stringPool = stringPoolChunkParser.getStringPool();

    if (globalStringPool == null)
      globalStringPool = stringPool;
    else if (resTypeStringPool == null)
      resTypeStringPool = stringPool;
    else
      typeNameStringPool = stringPool;

    // 向下移动字符串池的大小。
    mIndex += stringPoolHeader.header.size;
  }

  private void parseTablePackageType(ObjectInput objectInput) throws IOException {
    final long tablePackageIndex = mIndex;
    final ResTablePackage tablePackage = objectInput.read(ResTablePackage.class, tablePackageIndex);

    // 向下移动资源表元信息头部的大小。
    mIndex += tablePackage.header.headerSize;
  }

  private void parseTableTypeSpecType(ObjectInput objectInput) throws IOException {
    final long typeSpecIndex = mIndex;
    ResTableTypeSpec tableTypeSpec = objectInput.read(ResTableTypeSpec.class, typeSpecIndex);

    // 向下移动资源表类型规范内容的大小。
    mIndex += tableTypeSpec.header.size;
  }

  private void parseTableTypeType(ObjectInput objectInput) throws IOException {
    final long tableTypeIndex = mIndex;
    final ResTableType tableType = objectInput.read(ResTableType.class, tableTypeIndex);

    String typeName = resTypeStringPool[tableType.id - 1];

    if (!"drawable".equals(typeName)) {
      mIndex += tableType.header.size;
      return;
    }

    int[] offsetArray = TableTypeChunkParser.parseTypeOffsetArray(objectInput, tableType, tableTypeIndex);

    final long tableEntryIndex = tableTypeIndex + tableType.entriesStart;

    for (int i = 0; i < offsetArray.length; i++) {
      if (offsetArray[i] == -1)
        continue;

      final long entryIndex = offsetArray[i] + tableEntryIndex;
      final ResTableEntry tableEntry = objectInput.read(ResTableEntry.class, entryIndex);

      String entryName = typeNameStringPool[tableEntry.key.index];

      if (tableEntry.flags != ResTableEntry.FLAG_COMPLEX) {
        // parse Res_value
        final int entrySize = ObjectInput.sizeOf(ResTableEntry.class);
        final ResValue value = objectInput.read(ResValue.class, entryIndex + entrySize);

        if (value.dataType == ResValue.TYPE_STRING) {
          String data = globalStringPool[value.data];
          drawableNamePathMap.put(entryName, data);
        }
      }
    }

    mIndex += tableType.header.size;
  }

  private void parse(ObjectInput objectInput) throws IOException {
    while (!objectInput.isEof(mIndex)) {
      ResChunkHeader header = objectInput.read(ResChunkHeader.class, mIndex);

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
    ObjectInput objectInput = null;

    try {
      objectInput = new ObjectInput(file, false);
      parse(objectInput);
    } finally {
      closeQuietly(objectInput);
    }
  }

  public Map<String, String> getDrawableNamePathMap() {
    return drawableNamePathMap;
  }
}
