package io.l0neman.arscparser.type;

/*
struct ResTable_type
{
    struct ResChunk_header header;

    enum {
        NO_ENTRY = 0xFFFFFFFF
    };

    // The type identifier this chunk is holding.  Type IDs start
    // at 1 (corresponding to the value of the type bits in a
    // resource identifier).  0 is invalid.
    uint8_t id;

    // Must be 0.
    uint8_t res0;
    // Must be 0.
    uint16_t res1;

    // Number of uint32_t entry indices that follow.
    uint32_t entryCount;

    // Offset from header where ResTable_entry data starts.
    uint32_t entriesStart;

    // Configuration this collection of entries is designed for.
    ResTable_config config;
};
 */

import io.l0neman.arscparser.util.Formatter;
import io.l0neman.arscparser.util.objectio.FieldOrder;
import io.l0neman.arscparser.util.objectio.Struct;

/**
 * 类型资源项数据块。
 */
public class ResTableType implements Struct {
  public static final int NO_ENTRY = 0xFFFFFFFF;

  /**
   * {@link ResChunkHeader#type} = {@link ResourceTypes#RES_TABLE_TYPE_TYPE}
   * <p>
   * {@link ResChunkHeader#headerSize} = sizeOf(ResTableType.class) 表示头部大小。
   * <p>
   * {@link ResChunkHeader#size} = header.headerSize + {@link Integer#BYTES} * {@link #entryCount}
   */
  @FieldOrder(n = 0) public ResChunkHeader header;
  /** 资源 Type ID */
  @FieldOrder(n = 1) public byte id;
  /** 0，保留 */
  @FieldOrder(n = 2) public byte res0;
  /** 0，保留 */
  @FieldOrder(n = 3) public short res1;
  /** 本类型的资源项个数，即名称相同的资源项的个数 */
  @FieldOrder(n = 4) public int entryCount;
  /** 资源项数据块相对头部的偏移值 */
  @FieldOrder(n = 5) public int entriesStart;
  /** 描述配置信息 */
  @FieldOrder(n = 6) public ResTableConfig config;

  @Override
  public String toString() {
    return Config.BEAUTIFUL ?
        "{" +
            "header=" + header +
            ", id=" + Formatter.toHex(new byte[]{id}) +
            ", res0=" + res0 +
            ", res1=" + res1 +
            ", entryCount=" + entryCount +
            ", entriesStart=" + entriesStart +
            ", config=" + config +
            '}'
        :
        "ResTableType{" +
            "header=" + header +
            ", id=" + id +
            ", res0=" + res0 +
            ", res1=" + res1 +
            ", entryCount=" + entryCount +
            ", entriesStart=" + entriesStart +
            ", config=" + config +
            '}';
  }
}
