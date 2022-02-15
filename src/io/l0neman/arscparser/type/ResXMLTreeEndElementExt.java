package io.l0neman.arscparser.type;

import io.l0neman.arscparser.util.objectio.FieldOrder;
import io.l0neman.arscparser.util.objectio.Struct;

/*
struct ResXMLTree_endElementExt
{
    // String of the full namespace of this element.
    struct ResStringPool_ref ns;

    // String name of this node if it is an ELEMENT; the raw
    // character data if this is a CDATA node.
    struct ResStringPool_ref name;
};
 */
public class ResXMLTreeEndElementExt implements Struct {
  /** 元素的命名空间的字符串在字符串池中的索引，未指定则为 -1 */
  @FieldOrder(n = 0) public ResStringPoolRef ns;
  /** 元素名称字符串在字符串池中的索引 */
  @FieldOrder(n = 1) public ResStringPoolRef name;

  @Override
  public String toString() {
    return Config.BEAUTIFUL ?
        "{" +
            "ns=" + ns +
            ", name=" + name +
            '}'
        :
        "ResXMLTreeEndElementExt{" +
            "ns=" + ns +
            ", name=" + name +
            '}';
  }
}
