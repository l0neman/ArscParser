package io.l0neman.arscparser.type;

import io.l0neman.arscparser.util.Formatter;
import io.l0neman.arscparser.util.objectio.FieldOrder;
import io.l0neman.arscparser.util.objectio.Struct;

/*
struct ResTable_package
{
    struct ResChunk_header header;

    // If this is a base package, its ID.  Package IDs start
    // at 1 (corresponding to the value of the package bits in a
    // resource identifier).  0 means this is not a base package.
    uint32_t id;

    // Actual name of this package, \0-terminated.
    char16_t name[128];

    // Offset to a ResStringPool_header defining the resource
    // type symbol table.  If zero, this package is inheriting from
    // another base package (overriding specific values in it).
    uint32_t typeStrings;

    // Last index into typeStrings that is for public use by others.
    uint32_t lastPublicType;

    // Offset to a ResStringPool_header defining the resource
    // key symbol table.  If zero, this package is inheriting from
    // another base package (overriding specific values in it).
    uint32_t keyStrings;

    // Last index into keyStrings that is for public use by others.
    uint32_t lastPublicKey;
};
 */

/**
 * Package 资源项元信息头部。
 */
public class ResTablePackage implements Struct {
  /**
   * {@link ResChunkHeader#type} = {@link ResourceTypes#RES_TABLE_PACKAGE_TYPE}
   * <p>
   * {@link ResChunkHeader#headerSize} = sizeOf(ResTablePackage.class) 表示头部大小。
   * <p>
   * {@link ResChunkHeader#size} = head.headerSize + 类型字符串资源池大小 + 类型规范名称字符串池大小 +
   * 类型规范数据块大小 + 数据项信息数据块大小。
   */
  @FieldOrder(n = 0) public ResChunkHeader header;
  /** Package ID */
  @FieldOrder(n = 1) public int id;
  /** Package Name */
  @FieldOrder(n = 2) public byte[] name = new byte[256];
  /**
   * 类型字符串资源池相对头部的偏移位置。
   */
  @FieldOrder(n = 3) public int typeStrings;
  /**
   * 最后一个导出的 public 类型字符串在类型字符串资源池中的索引，目前这个值设置为类型字符串资源池的大小。
   */
  @FieldOrder(n = 4) public int lastPublicType;
  /**
   * 资源项名称字符串相对头部的偏移位置。
   */
  @FieldOrder(n = 5) public int keyStrings;
  /**
   * 最后一个导出的 public 资源项名称字符串在资源项名称字符串资源池中的索引，目前这个值设置为资源项名称字符串资源池的大小。
   */
  @FieldOrder(n = 6) public int lastPublicKey;

  @Override
  public String toString() {
    return Config.BEAUTIFUL ?
        "{" +
            "header=" + header +
            ", id=" + Formatter.toHex(Formatter.fromInt(id, false)) +
            ", name=" + Formatter.toUtf16String(name) +
            ", typeStrings=" + typeStrings +
            ", lastPublicType=" + lastPublicType +
            ", keyStrings=" + keyStrings +
            ", lastPublicKey=" + lastPublicKey +
            '}'
        :
        "ResTablePackage{" +
            "header=" + header +
            ", id=" + id +
            ", name=" + Formatter.toUtf16String(name) +
            ", typeStrings=" + typeStrings +
            ", lastPublicType=" + lastPublicType +
            ", keyStrings=" + keyStrings +
            ", lastPublicKey=" + lastPublicKey +
            '}';
  }
}
