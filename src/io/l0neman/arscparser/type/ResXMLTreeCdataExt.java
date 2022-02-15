package io.l0neman.arscparser.type;

import io.l0neman.arscparser.util.objectio.FieldOrder;
import io.l0neman.arscparser.util.objectio.Struct;

/*
struct ResXMLTree_cdataExt
{
    // The raw CDATA character data.
    struct ResStringPool_ref data;

    // The typed value of the character data if this is a CDATA node.
    struct Res_value typedData;
};
 */
public class ResXMLTreeCdataExt implements Struct {
  /** CDATA 原始值在字符串池中的索引 */
  @FieldOrder(n = 0) public ResStringPoolRef data;
  /** CDATA */
  @FieldOrder(n = 1) public ResValue typeData;

  @Override
  public String toString() {
    return Config.BEAUTIFUL ?
        "{" +
            "data=" + data +
            ", typeData=" + typeData +
            '}'
        :
        "ResXMLTreeCdataExt{" +
            "data=" + data +
            ", typeData=" + typeData +
            '}';
  }
}
