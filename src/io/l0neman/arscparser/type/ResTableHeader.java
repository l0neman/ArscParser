package io.l0neman.arscparser.type;

/*
struct ResTable_header
{
    struct ResChunk_header header;

    // The number of ResTable_package structures.
    uint32_t packageCount;
};
 */

import io.l0neman.arscparser.util.objectio.FieldOrder;
import io.l0neman.arscparser.util.objectio.Struct;

/**
 * 资源表头结构。
 */
public class ResTableHeader implements Struct {

  /**
   * {@link ResChunkHeader#type} = {@link ResourceTypes#RES_TABLE_TYPE}
   * <p>
   * {@link ResChunkHeader#headerSize} = sizeOf(ResTableHeader.class) 表示头部大小。
   * <p>
   * {@link ResChunkHeader#size} = 整个 resources.arsc 文件的大小。
   */
  @FieldOrder(n = 0) public ResChunkHeader header;
  /**
   * 被编译的资源包数量
   */
  @FieldOrder(n = 1) public int packageCount;

  @Override
  public String toString() {

    return Config.BEAUTIFUL ?
        "{" +
            "header=" + header +
            ", packageCount=" + packageCount +
            '}'
        :
        "ResTableHeader{" +
            "header=" + header +
            ", packageCount=" + packageCount +
            '}';
  }
}
