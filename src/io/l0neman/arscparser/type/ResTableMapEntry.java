package io.l0neman.arscparser.type;

import io.l0neman.arscparser.util.objectio.FieldOrder;

/*
struct ResTable_map_entry : public ResTable_entry
{
    // Resource identifier of the parent mapping, or 0 if there is none.
    // This is always treated as a TYPE_DYNAMIC_REFERENCE.
    ResTable_ref parent;
    // Number of name/value pairs that follow for FLAG_COMPLEX.
    uint32_t count;
};
 */
public class ResTableMapEntry extends ResTableEntry {
  /**
   * 指向父 ResTable_map_entry 的资源 ID，如果没有父 ResTable_map_entry，则等于 0。
   */
  @FieldOrder(n = 0) public ResTableRef parent;
  /** bag 项的个数。 */
  @FieldOrder(n = 1) public int count;

  @Override
  public String toString() {
    return Config.BEAUTIFUL ?
        "{" +
            "parent=" + parent +
            ", count=" + count +
            ", size=" + size +
            ", flags=" + flags +
            ", key=" + key +
            '}'
        :
        "ResTableMapEntry{" +
            "parent=" + parent +
            ", count=" + count +
            ", size=" + size +
            ", flags=" + flags +
            ", key=" + key +
            '}';
  }
}
