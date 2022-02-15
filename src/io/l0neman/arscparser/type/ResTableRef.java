package io.l0neman.arscparser.type;
/*
struct ResTable_ref
{
    uint32_t ident;
};
 */

import io.l0neman.arscparser.util.Formatter;
import io.l0neman.arscparser.util.objectio.FieldOrder;
import io.l0neman.arscparser.util.objectio.Struct;

/**
 * 资源的引用（ResID）
 */
public class ResTableRef implements Struct {

  @FieldOrder(n = 0) public int ident;

  @Override
  public String toString() {
    return Config.BEAUTIFUL ?
        "{" +
            "ident=" + Formatter.toHex(Formatter.fromInt(ident, true)) +
            '}'
        :
        "ResTableRef{" +
            "ident=" + ident +
            '}';
  }
}
