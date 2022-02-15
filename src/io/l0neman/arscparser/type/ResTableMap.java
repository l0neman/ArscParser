package io.l0neman.arscparser.type;

import io.l0neman.arscparser.util.objectio.FieldOrder;
import io.l0neman.arscparser.util.objectio.Struct;

/*
struct ResTable_map
    {
    // The resource identifier defining this mapping's name.  For attribute
    // resources, 'name' can be one of the following special resource types
    // to supply meta-data about the attribute; for all other resource types
    // it must be an attribute resource.
    ResTable_ref name;

// Special values for 'name' when defining attribute resources.
enum {
    // This entry holds the attribute's type code.
    ATTR_TYPE = Res_MAKEINTERNAL(0),

    // For integral attributes, this is the minimum value it can hold.
    ATTR_MIN = Res_MAKEINTERNAL(1),

    // For integral attributes, this is the maximum value it can hold.
    ATTR_MAX = Res_MAKEINTERNAL(2),

    // Localization of this resource is can be encouraged or required with
    // an aapt flag if this is set
    ATTR_L10N = Res_MAKEINTERNAL(3),

    // for plural support, see android.content.res.PluralRules#attrForQuantity(int)
    ATTR_OTHER = Res_MAKEINTERNAL(4),
    ATTR_ZERO = Res_MAKEINTERNAL(5),
    ATTR_ONE = Res_MAKEINTERNAL(6),
    ATTR_TWO = Res_MAKEINTERNAL(7),
    ATTR_FEW = Res_MAKEINTERNAL(8),
    ATTR_MANY = Res_MAKEINTERNAL(9)

    };

// Bit mask of allowed types, for use with ATTR_TYPE.
enum {
    // No type has been defined for this attribute, use generic
    // type handling.  The low 16 bits are for types that can be
    // handled generically; the upper 16 require additional information
    // in the bag so can not be handled generically for TYPE_ANY.
    TYPE_ANY = 0x0000FFFF,

    // Attribute holds a references to another resource.
    TYPE_REFERENCE = 1<<0,

    // Attribute holds a generic string.
    TYPE_STRING = 1<<1,

    // Attribute holds an integer value.  ATTR_MIN and ATTR_MIN can
    // optionally specify a constrained range of possible integer values.
    TYPE_INTEGER = 1<<2,

    // Attribute holds a boolean integer.
    TYPE_BOOLEAN = 1<<3,

    // Attribute holds a color value.
    TYPE_COLOR = 1<<4,

    // Attribute holds a floating point value.
    TYPE_FLOAT = 1<<5,

    // Attribute holds a dimension value, such as "20px".
    TYPE_DIMENSION = 1<<6,

    // Attribute holds a fraction value, such as "20%".
    TYPE_FRACTION = 1<<7,

    // Attribute holds an enumeration.  The enumeration values are
    // supplied as additional entries in the map.
    TYPE_ENUM = 1<<16,

    // Attribute holds a bitmaks of flags.  The flag bit values are
    // supplied as additional entries in the map.
    TYPE_FLAGS = 1<<17
    };

// Enum of localization modes, for use with ATTR_L10N.
enum {
    L10N_NOT_REQUIRED = 0,
    L10N_SUGGESTED    = 1
    };

    // This mapping's value.
    Res_value value;
    };
 */
public class ResTableMap implements Struct {
  /** 引用资源地址 */
  @FieldOrder(n = 0) public ResTableRef name;
  /** 资源值 */
  @FieldOrder(n = 1) public ResValue value;

  @Override
  public String toString() {
    return Config.BEAUTIFUL ?
        "{" +
            "name=" + name +
            ", value=" + value +
            '}'
        :
        "ResTableMap{" +
            "name=" + name +
            ", value=" + value +
            '}';
  }
}
