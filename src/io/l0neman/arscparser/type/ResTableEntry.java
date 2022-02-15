package io.l0neman.arscparser.type;

/*
struct ResTable_entry
{
    // Number of bytes in this structure.
    uint16_t size;

    enum {
        // If set, this is a complex entry, holding a set of name/value
        // mappings.  It is followed by an array of ResTable_map structures.
        FLAG_COMPLEX = 0x0001,
        // If set, this resource has been declared public, so libraries
        // are allowed to reference it.
        FLAG_PUBLIC = 0x0002,
        // If set, this is a weak resource and may be overriden by strong
        // resources of the same name/type. This is only useful during
        // linking with other resource tables.
        FLAG_WEAK = 0x0004
    };
    uint16_t flags;

    // Reference into ResTable_package::keyStrings identifying this entry.
    struct ResStringPool_ref key;
};
 */

import io.l0neman.arscparser.util.objectio.FieldOrder;
import io.l0neman.arscparser.util.objectio.Struct;

/**
 * 资源项。
 */
public class ResTableEntry implements Struct {
  public static final int FLAG_COMPLEX = 0x0001;
  public static final int FLAG_PUBLIC = 0x0002;

  /** sizeOf(ResTableEntry.class) 资源项头部大小 */
  @FieldOrder(n = 0) public short size;
  /**
   * 资源项标志位。如果是一个 Bag 资源项，那么 FLAG_COMPLEX 位就等于 1，并且在 ResTable_entry 后面跟有一个 ResTable_map 数组，
   * 否则的话，在 ResTable_entry {@link ResTableEntry} 后面跟的是一个 Res_value。如果是一个可以被引用的资源项，那么 FLAG_PUBLIC 位就等于1
   */
  @FieldOrder(n = 1) public short flags;
  /**
   * 资源项名称在资源项名称字符串资源池的索引。
   */
  @FieldOrder(n = 2) public ResStringPoolRef key;

  @Override
  public String toString() {
    return Config.BEAUTIFUL ?
        "{" +
            "size=" + size +
            ", flags=" + flags +
            ", key=" + key +
            '}'
        :
        "ResTableEntry{" +
            "size=" + size +
            ", flags=" + flags +
            ", key=" + key +
            '}';
  }
}
