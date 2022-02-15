package io.l0neman.arscparser.type;

import io.l0neman.arscparser.util.objectio.FieldOrder;
import io.l0neman.arscparser.util.objectio.Struct;

/*
struct ResXMLTree_namespaceExt
{
    // The prefix of the namespace.
    struct ResStringPool_ref prefix;

    // The URI of the namespace.
    struct ResStringPool_ref uri;
};
 */
public class ResXMLTreeNamespaceExt implements Struct {
  /** 命名空间字符串在字符串资源池中的索引 */
  @FieldOrder(n = 0) public ResStringPoolRef prefix;
  /** uri 字符串在字符串资源池中的索引 */
  @FieldOrder(n = 0) public ResStringPoolRef uri;

  @Override
  public String toString() {
    return Config.BEAUTIFUL ?
        "{" +
            "prefix=" + prefix +
            ", uri=" + uri +
            '}'
        :
        "ResXMLTreeNamespaceExt{" +
            "prefix=" + prefix +
            ", uri=" + uri +
            '}';
  }
}
