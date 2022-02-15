package io.l0neman.arscparser.core;

import io.l0neman.arscparser.type.ResTableType;
import io.l0neman.arscparser.type.ResTableTypeSpec;
import io.l0neman.arscparser.util.objectio.ObjectInput;

import java.io.IOException;

public class TableTypeChunkParser {

  @SuppressWarnings("Duplicates")
  public static int[] parseSpecEntryArray(ObjectInput objectInput, ResTableTypeSpec tableTypeSpec, long typeSpecIndex)
      throws IOException {
    int[] entryArray = new int[tableTypeSpec.entryCount];
    long index = typeSpecIndex + tableTypeSpec.header.headerSize;

    for (int i = 0; i < entryArray.length; i++) {
      entryArray[i] = objectInput.readInt(index);
      index += Integer.BYTES;
    }
    return entryArray;
  }

  @SuppressWarnings("Duplicates")
  public static int[] parseTypeOffsetArray(ObjectInput objectInput, ResTableType tableType, long typeIndex)
      throws IOException {
    int[] entryArray = new int[tableType.entryCount];
    long index = typeIndex + tableType.header.headerSize;

    for (int i = 0; i < entryArray.length; i++) {
      entryArray[i] = objectInput.readInt(index);
      index += Integer.BYTES;
    }
    return entryArray;
  }
}
