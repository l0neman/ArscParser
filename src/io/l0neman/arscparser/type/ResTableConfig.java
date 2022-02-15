package io.l0neman.arscparser.type;

import io.l0neman.arscparser.util.objectio.FieldOrder;
import io.l0neman.arscparser.util.objectio.Struct;
import io.l0neman.arscparser.util.objectio.Union;

/*
struct ResTable_config
{
    // Number of bytes in this structure.
    uint32_t size;

    union {
        struct {
            // Mobile country code (from SIM).  0 means "any".
            uint16_t mcc;
            // Mobile network code (from SIM).  0 means "any".
            uint16_t mnc;
        };
        uint32_t imsi;
    };

    union {
        struct {
            // This field can take three different forms:
            // - \0\0 means "any".
            //
            // - Two 7 bit ascii values interpreted as ISO-639-1 language
            //   codes ('fr', 'en' etc. etc.). The high bit for both bytes is
            //   zero.
            //
            // - A single 16 bit little endian packed value representing an
            //   ISO-639-2 3 letter language code. This will be of the form:
            //
            //   {1, t, t, t, t, t, s, s, s, s, s, f, f, f, f, f}
            //
            //   bit[0, 4] = first letter of the language code
            //   bit[5, 9] = second letter of the language code
            //   bit[10, 14] = third letter of the language code.
            //   bit[15] = 1 always
            //
            // For backwards compatibility, languages that have unambiguous
            // two letter codes are represented in that format.
            //
            // The layout is always bigendian irrespective of the runtime
            // architecture.
            char language[2];

            // This field can take three different forms:
            // - \0\0 means "any".
            //
            // - Two 7 bit ascii values interpreted as 2 letter region
            //   codes ('US', 'GB' etc.). The high bit for both bytes is zero.
            //
            // - An UN M.49 3 digit region code. For simplicity, these are packed
            //   in the same manner as the language codes, though we should need
            //   only 10 bits to represent them, instead of the 15.
            //
            // The layout is always bigendian irrespective of the runtime
            // architecture.
            char country[2];
        };
        uint32_t locale;
    };

    enum {
        ORIENTATION_ANY  = ACONFIGURATION_ORIENTATION_ANY,
        ORIENTATION_PORT = ACONFIGURATION_ORIENTATION_PORT,
        ORIENTATION_LAND = ACONFIGURATION_ORIENTATION_LAND,
        ORIENTATION_SQUARE = ACONFIGURATION_ORIENTATION_SQUARE,
    };

    enum {
        TOUCHSCREEN_ANY  = ACONFIGURATION_TOUCHSCREEN_ANY,
        TOUCHSCREEN_NOTOUCH  = ACONFIGURATION_TOUCHSCREEN_NOTOUCH,
        TOUCHSCREEN_STYLUS  = ACONFIGURATION_TOUCHSCREEN_STYLUS,
        TOUCHSCREEN_FINGER  = ACONFIGURATION_TOUCHSCREEN_FINGER,
    };

    enum {
        DENSITY_DEFAULT = ACONFIGURATION_DENSITY_DEFAULT,
        DENSITY_LOW = ACONFIGURATION_DENSITY_LOW,
        DENSITY_MEDIUM = ACONFIGURATION_DENSITY_MEDIUM,
        DENSITY_TV = ACONFIGURATION_DENSITY_TV,
        DENSITY_HIGH = ACONFIGURATION_DENSITY_HIGH,
        DENSITY_XHIGH = ACONFIGURATION_DENSITY_XHIGH,
        DENSITY_XXHIGH = ACONFIGURATION_DENSITY_XXHIGH,
        DENSITY_XXXHIGH = ACONFIGURATION_DENSITY_XXXHIGH,
        DENSITY_ANY = ACONFIGURATION_DENSITY_ANY,
        DENSITY_NONE = ACONFIGURATION_DENSITY_NONE
    };

    union {
        struct {
            uint8_t orientation;
            uint8_t touchscreen;
            uint16_t density;
        };
        uint32_t screenType;
    };

    enum {
        KEYBOARD_ANY  = ACONFIGURATION_KEYBOARD_ANY,
        KEYBOARD_NOKEYS  = ACONFIGURATION_KEYBOARD_NOKEYS,
        KEYBOARD_QWERTY  = ACONFIGURATION_KEYBOARD_QWERTY,
        KEYBOARD_12KEY  = ACONFIGURATION_KEYBOARD_12KEY,
    };

    enum {
        NAVIGATION_ANY  = ACONFIGURATION_NAVIGATION_ANY,
        NAVIGATION_NONAV  = ACONFIGURATION_NAVIGATION_NONAV,
        NAVIGATION_DPAD  = ACONFIGURATION_NAVIGATION_DPAD,
        NAVIGATION_TRACKBALL  = ACONFIGURATION_NAVIGATION_TRACKBALL,
        NAVIGATION_WHEEL  = ACONFIGURATION_NAVIGATION_WHEEL,
    };

    enum {
        MASK_KEYSHIDDEN = 0x0003,
        KEYSHIDDEN_ANY = ACONFIGURATION_KEYSHIDDEN_ANY,
        KEYSHIDDEN_NO = ACONFIGURATION_KEYSHIDDEN_NO,
        KEYSHIDDEN_YES = ACONFIGURATION_KEYSHIDDEN_YES,
        KEYSHIDDEN_SOFT = ACONFIGURATION_KEYSHIDDEN_SOFT,
    };

    enum {
        MASK_NAVHIDDEN = 0x000c,
        SHIFT_NAVHIDDEN = 2,
        NAVHIDDEN_ANY = ACONFIGURATION_NAVHIDDEN_ANY << SHIFT_NAVHIDDEN,
        NAVHIDDEN_NO = ACONFIGURATION_NAVHIDDEN_NO << SHIFT_NAVHIDDEN,
        NAVHIDDEN_YES = ACONFIGURATION_NAVHIDDEN_YES << SHIFT_NAVHIDDEN,
    };

    union {
        struct {
            uint8_t keyboard;
            uint8_t navigation;
            uint8_t inputFlags;
            uint8_t inputPad0;
        };
        uint32_t input;
    };

    enum {
        SCREENWIDTH_ANY = 0
    };

    enum {
        SCREENHEIGHT_ANY = 0
    };

    union {
        struct {
            uint16_t screenWidth;
            uint16_t screenHeight;
        };
        uint32_t screenSize;
    };

    enum {
        SDKVERSION_ANY = 0
    };

  enum {
        MINORVERSION_ANY = 0
    };

    union {
        struct {
            uint16_t sdkVersion;
            // For now minorVersion must always be 0!!!  Its meaning
            // is currently undefined.
            uint16_t minorVersion;
        };
        uint32_t version;
    };

    enum {
        // screenLayout bits for screen size class.
        MASK_SCREENSIZE = 0x0f,
        SCREENSIZE_ANY = ACONFIGURATION_SCREENSIZE_ANY,
        SCREENSIZE_SMALL = ACONFIGURATION_SCREENSIZE_SMALL,
        SCREENSIZE_NORMAL = ACONFIGURATION_SCREENSIZE_NORMAL,
        SCREENSIZE_LARGE = ACONFIGURATION_SCREENSIZE_LARGE,
        SCREENSIZE_XLARGE = ACONFIGURATION_SCREENSIZE_XLARGE,

        // screenLayout bits for wide/long screen variation.
        MASK_SCREENLONG = 0x30,
        SHIFT_SCREENLONG = 4,
        SCREENLONG_ANY = ACONFIGURATION_SCREENLONG_ANY << SHIFT_SCREENLONG,
        SCREENLONG_NO = ACONFIGURATION_SCREENLONG_NO << SHIFT_SCREENLONG,
        SCREENLONG_YES = ACONFIGURATION_SCREENLONG_YES << SHIFT_SCREENLONG,

        // screenLayout bits for layout direction.
        MASK_LAYOUTDIR = 0xC0,
        SHIFT_LAYOUTDIR = 6,
        LAYOUTDIR_ANY = ACONFIGURATION_LAYOUTDIR_ANY << SHIFT_LAYOUTDIR,
        LAYOUTDIR_LTR = ACONFIGURATION_LAYOUTDIR_LTR << SHIFT_LAYOUTDIR,
        LAYOUTDIR_RTL = ACONFIGURATION_LAYOUTDIR_RTL << SHIFT_LAYOUTDIR,
    };

    enum {
        // uiMode bits for the mode type.
        MASK_UI_MODE_TYPE = 0x0f,
        UI_MODE_TYPE_ANY = ACONFIGURATION_UI_MODE_TYPE_ANY,
        UI_MODE_TYPE_NORMAL = ACONFIGURATION_UI_MODE_TYPE_NORMAL,
        UI_MODE_TYPE_DESK = ACONFIGURATION_UI_MODE_TYPE_DESK,
        UI_MODE_TYPE_CAR = ACONFIGURATION_UI_MODE_TYPE_CAR,
        UI_MODE_TYPE_TELEVISION = ACONFIGURATION_UI_MODE_TYPE_TELEVISION,
        UI_MODE_TYPE_APPLIANCE = ACONFIGURATION_UI_MODE_TYPE_APPLIANCE,
        UI_MODE_TYPE_WATCH = ACONFIGURATION_UI_MODE_TYPE_WATCH,

        // uiMode bits for the night switch.
        MASK_UI_MODE_NIGHT = 0x30,
        SHIFT_UI_MODE_NIGHT = 4,
        UI_MODE_NIGHT_ANY = ACONFIGURATION_UI_MODE_NIGHT_ANY << SHIFT_UI_MODE_NIGHT,
        UI_MODE_NIGHT_NO = ACONFIGURATION_UI_MODE_NIGHT_NO << SHIFT_UI_MODE_NIGHT,
        UI_MODE_NIGHT_YES = ACONFIGURATION_UI_MODE_NIGHT_YES << SHIFT_UI_MODE_NIGHT,
    };

    union {
        struct {
            uint8_t screenLayout;
            uint8_t uiMode;
            uint16_t smallestScreenWidthDp;
        };
        uint32_t screenConfig;
    };

    union {
        struct {
            uint16_t screenWidthDp;
            uint16_t screenHeightDp;
        };
        uint32_t screenSizeDp;
    };

    // The ISO-15924 short name for the script corresponding to this
    // configuration. (eg. Hant, Latn, etc.). Interpreted in conjunction with
    // the locale field.
    char localeScript[4];

    // A single BCP-47 variant subtag. Will vary in length between 5 and 8
    // chars. Interpreted in conjunction with the locale field.
    char localeVariant[8];

    enum {
        // screenLayout2 bits for round/notround.
        MASK_SCREENROUND = 0x03,
        SCREENROUND_ANY = ACONFIGURATION_SCREENROUND_ANY,
        SCREENROUND_NO = ACONFIGURATION_SCREENROUND_NO,
        SCREENROUND_YES = ACONFIGURATION_SCREENROUND_YES,
    };

    // An extension of screenConfig.
    union {
        struct {
            uint8_t screenLayout2;      // Contains round/notround qualifier.
            uint8_t screenConfigPad1;   // Reserved padding.
            uint16_t screenConfigPad2;  // Reserved padding.
        };
        uint32_t screenConfig2;
    };
}
 */
public class ResTableConfig implements Struct {

  /** ResTableConfig 配置大小 */
  @FieldOrder(n = 0) public int size;

  public static class MobileConfig implements Union {
    public static class Type implements Struct {
      public short mcc;
      public short mnc;

      @Override
      public String toString() {
        return Config.BEAUTIFUL ?
            "Struct{" +
                "mcc=" + mcc +
                ", mnc=" + mnc +
                '}'
            :
            "Struct{" +
                "mcc=" + mcc +
                ", mnc=" + mnc +
                '}';
      }
    }

    public Type data;
    public int imsi;

    @Override
    public String toString() {
      return Config.BEAUTIFUL ?
          "{" +
              "data=" + data +
              ", imsi=" + imsi +
              '}'
          :
          "MobileConfig{" +
              "data=" + data +
              ", imsi=" + imsi +
              '}';
    }
  }

  public static class LocaleConfig implements Union {

    public static class Type implements Struct {
      public char[] language = new char[2];
      public char[] country = new char[2];

      @Override
      public String toString() {
        return Config.BEAUTIFUL ?
            "{" +
                "language=" + new String(language) +
                ", country=" + new String(country) +
                '}'
            :
            "Struct{" +
                "language=" + new String(language) +
                ", country=" + new String(country) +
                '}';
      }
    }
    public Type data;
    public int locale;

    @Override
    public String toString() {
      return Config.BEAUTIFUL ?
          "{" +
              "data=" + data +
              ", locale=" + locale +
              '}'
          :
          "LocaleConfig{" +
              "data=" + data +
              ", locale=" + locale +
              '}';
    }
  }

  public static final int ORIENTATION_ANY = 0x0000;
  public static final int ORIENTATION_PORT = 0x0001;
  public static final int ORIENTATION_LAND = 0x0002;
  public static final int ORIENTATION_SQUARE = 0x0003;

  public static final int TOUCHSCREEN_ANY = 0x0000;
  public static final int TOUCHSCREEN_NOTOUCH = 0x0001;
  public static final int TOUCHSCREEN_STYLUS = 0x0002;
  public static final int TOUCHSCREEN_FINGER = 0x0003;

  public static final int DENSITY_DEFAULT = 0;
  public static final int DENSITY_LOW = 120;
  public static final int DENSITY_MEDIUM = 160;
  public static final int DENSITY_TY = 213;
  public static final int DENSITY_HIGH = 240;
  public static final int DENSITY_XHIGH = 320;
  public static final int DENSITY_XXHIGH = 480;
  public static final int DENSITY_XXXHIGH = 640;
  public static final int DENSITY_ANY = 0xfffe;
  public static final int DENSITY_NONE = 0xfff;

  public static class ScreenTypeConfig implements Union {

    public static class Type implements Struct {
      public byte orientation;
      public byte touchscreen;
      public short density;

      @Override
      public String toString() {
        return Config.BEAUTIFUL ?
            "{" +
                "orientation=" + orientation +
                ", touchscreen=" + touchscreen +
                ", density=" + density +
                '}'
            :
            "Struct{" +
                "orientation=" + orientation +
                ", touchscreen=" + touchscreen +
                ", density=" + density +
                '}';
      }
    }
    public Type data;
    public int screenType;

    @Override
    public String toString() {
      return Config.BEAUTIFUL ?
          "{" +
              "data=" + data +
              ", screenType=" + screenType +
              '}'
          :
          "ScreenTypeConfig{" +
              "data=" + data +
              ", screenType=" + screenType +
              '}';
    }
  }

  public static final int KEYBOARD_ANY = 0x0000;
  public static final int KEYBOARD_NOKEYS = 0x0001;
  public static final int KEYBOARD_QWERTY = 0x0002;
  public static final int KEYBOARD_12KEY = 0x0003;

  public static final int NAVIGATION_ANY = 0x0000;
  public static final int NAVIGATION_NONAV = 0x0001;
  public static final int NAVIGATION_DPAD = 0x0002;
  public static final int NAVIGATION_TRACKBALL = 0x0003;
  public static final int NAVIGATION_WHEEL = 0x0004;

  public static final int MASK_KEYSHIDDEN = 0x0003;
  public static final int KEYSHIDDEN_ANY = 0x0000;
  public static final int KEYSHIDDEN_NO = 0x0001;
  public static final int KEYSHIDDEN_YES = 0x0002;
  public static final int KEYSHIDDEN_SOFT = 0x0003;

  public static final int MASK_NAVHIDDEN = 0x000c;
  public static final int SHIFT_NAVHIDDEN = 2;
  public static final int NAVHIDDEN_ANY = 0;
  public static final int NAVHIDDEN_NO = 0x0001 << SHIFT_NAVHIDDEN;
  public static final int NAVHIDDEN_YES = 0x0002 << SHIFT_NAVHIDDEN;

  public static class InputConfig implements Union {
    public static class Type implements Struct {
      public byte keyboard;
      public byte navigation;
      public byte inputFlags;
      public byte inputPad0;

      @Override
      public String toString() {
        return Config.BEAUTIFUL ?
            "{" +
                "keyboard=" + keyboard +
                ", navigation=" + navigation +
                ", inputFlags=" + inputFlags +
                ", inputPad0=" + inputPad0 +
                '}'
            :
            "Struct{" +
                "keyboard=" + keyboard +
                ", navigation=" + navigation +
                ", inputFlags=" + inputFlags +
                ", inputPad0=" + inputPad0 +
                '}';
      }
    }

    public Type data;
    public int input;

    @Override
    public String toString() {
      return Config.BEAUTIFUL ?
          "{" +
              "data=" + data +
              ", input=" + input +
              '}'
          :
          "InputConfig{" +
              "data=" + data +
              ", input=" + input +
              '}';
    }
  }

  public static final int SCREENWIDTH_ANY = 0;
  public static final int SCREENHEIGHT_ANY = 0;

  public static class ScreenSizeConfig implements Union {
    public static class Type implements Struct {
      public short screenWidth;
      public short screenHeight;

      @Override
      public String toString() {
        return Config.BEAUTIFUL ?
            "{" +
                "screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                '}'
            :
            "Struct{" +
                "screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                '}';
      }
    }

    public Type data;
    public int screenSize;

    @Override
    public String toString() {
      return Config.BEAUTIFUL ?
          "{" +
              "data=" + data +
              ", screenSize=" + screenSize +
              '}'
          :
          "ScreenSizeConfig{" +
              "data=" + data +
              ", screenSize=" + screenSize +
              '}';
    }
  }

  public static final int SDKVERSION_ANY = 0;
  public static final int MINORVERSION_ANY = 0;

  public static class VersionConfig implements Union {
    public static class Type implements Struct {
      public short sdkVersion;
      public short minorVersion;

      @Override
      public String toString() {
        return Config.BEAUTIFUL ?
            "{" +
                "sdkVersion=" + sdkVersion +
                ", minorVersion=" + minorVersion +
                '}'
            :
            "Struct{" +
                "sdkVersion=" + sdkVersion +
                ", minorVersion=" + minorVersion +
                '}';
      }
    }

    public Type data;
    public int screenSize;

    @Override
    public String toString() {
      return Config.BEAUTIFUL ?
          "{" +
              "data=" + data +
              ", screenSize=" + screenSize +
              '}'
          :
          "VersionConfig{" +
              "data=" + data +
              ", screenSize=" + screenSize +
              '}';
    }
  }

  public static final int MASK_SCREENSIZE = 0x0f;
  public static final int SCREENSIZE_ANY = 0x00;
  public static final int SCREENSIZE_SMALL = 0x01;
  public static final int SCREENSIZE_NORMAL = 0x02;
  public static final int SCREENSIZE_LARGE = 0x03;
  public static final int SCREENSIZE_XLARGE = 0x04;

  public static final int MASK_SCREENLONG = 0x30;
  public static final int SHIFT_SCREENLONG = 4;
  public static final int SCREENLONG_ANY = 0x00;
  public static final int SCREENLONG_NO = 0x01 << SHIFT_SCREENLONG;
  public static final int SCREENLONG_YES = 0x02 << SHIFT_SCREENLONG;

  public static final int MASK_LAYOUTDIR = 0xC0;
  public static final int SHIFT_LAYOUTDIR = 6;
  public static final int LAYOUTDIR_ANY = 0x00;
  public static final int LAYOUTDIR_LTR = 0x01 << SHIFT_LAYOUTDIR;
  public static final int LAYOUTDIR_RTL = 0x02 << SHIFT_LAYOUTDIR;

  public static final int MASK_UI_MODE_TYPE = 0x0f;
  public static final int UI_MODE_TYPE_ANY = 0x00;
  public static final int UI_MODE_TYPE_NORMAL = 0x01;
  public static final int UI_MODE_TYPE_DESK = 0x02;
  public static final int UI_MODE_TYPE_CAR = 0x03;
  public static final int UI_MODE_TYPE_TELEVISION = 0x04;
  public static final int UI_MODE_TYPE_APPLIANCE = 0x05;
  public static final int UI_MODE_TYPE_WATCH = 0x06;

  public static final int MASK_UI_MODE_NIGHT = 0x30;
  public static final int SHIFT_UI_MODE_NIGHT = 4;
  public static final int UI_MODE_NIGHT_ANY = 0x00;
  public static final int UI_MODE_NIGHT_NO = 0x1;
  public static final int UI_MODE_NIGHT_YES = 0x2;

  public static class ScreenConfig implements Union {

    public static class Type implements Struct {
      public byte screenLayout;
      public byte uiMode;
      public byte screenConfigPad1;
      public byte screenConfigPad2;

      @Override
      public String toString() {
        return Config.BEAUTIFUL ?
            "{" +
                "screenLayout=" + screenLayout +
                ", uiMode=" + uiMode +
                ", screenConfigPad1=" + screenConfigPad1 +
                ", screenConfigPad2=" + screenConfigPad2 +
                '}'
            :
            "Struct{" +
                "screenLayout=" + screenLayout +
                ", uiMode=" + uiMode +
                ", screenConfigPad1=" + screenConfigPad1 +
                ", screenConfigPad2=" + screenConfigPad2 +
                '}';
      }
    }

    public Type data;
    public int screenConfig;

    @Override
    public String toString() {
      return Config.BEAUTIFUL ?
          "{" +
              "data=" + data +
              ", screenConfig=" + screenConfig +
              '}'
          :
          "ScreenConfig{" +
              "data=" + data +
              ", screenConfig=" + screenConfig +
              '}';
    }
  }

  public static class ScreenSizeDpConfig implements Union {

    public static class Type implements Struct {
      public short screenWidth;
      public short screenHeight;

      @Override
      public String toString() {
        return Config.BEAUTIFUL ?
            "{" +
                "screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                '}'
            :
            "Struct{" +
                "screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                '}';
      }
    }

    public Type data;
    public int screenSizeDp;

    @Override
    public String toString() {
      return Config.BEAUTIFUL ?
          "{" +
              "data=" + data +
              ", screenSizeDp=" + screenSizeDp +
              '}'
          :
          "ScreenSizeDpConfig{" +
              "data=" + data +
              ", screenSizeDp=" + screenSizeDp +
              '}';
    }
  }

  public MobileConfig mobileConfig;
  public LocaleConfig localeConfig;
  public ScreenTypeConfig screenTypeConfig;
  public InputConfig inputConfig;
  public ScreenSizeConfig screenSizeConfig;
  public VersionConfig versionConfig;
  public ScreenConfig screenConfig;
  public ScreenSizeDpConfig screenSizeDpConfig;

  public char[] localeScript = new char[4];
  public char[] localeVariant = new char[8];

  public ScreenConfig2 screenConfig2;

  public static class ScreenConfig2 implements Union {

    public static class Type implements Struct {
      public byte screenLayout2;
      public byte screenConfigPad1;
      public short screenConfigPad2;

      @Override
      public String toString() {
        return Config.BEAUTIFUL ?
            "{" +
                "screenLayout2=" + screenLayout2 +
                ", screenConfigPad1=" + screenConfigPad1 +
                ", screenConfigPad2=" + screenConfigPad2 +
                '}'
            :
            "Struct{" +
                "screenLayout2=" + screenLayout2 +
                ", screenConfigPad1=" + screenConfigPad1 +
                ", screenConfigPad2=" + screenConfigPad2 +
                '}';
      }
    }

    public Type data;
    public int screenConfig2;

    @Override
    public String toString() {
      return Config.BEAUTIFUL ?
          "{" +
              "data=" + data +
              ", screenConfig2=" + screenConfig2 +
              '}'
          :
          "ScreenConfig2{" +
              "data=" + data +
              ", screenConfig2=" + screenConfig2 +
              '}';
    }
  }

  @Override
  public String toString() {
    return Config.BEAUTIFUL ?
        "{" +
            "size=" + size +
            ", localeScript=" + new String(localeScript) +
            ", localeVariant=" + new String(localeVariant) +
            ", mobile=" + mobileConfig +
            ", locale=" + localeConfig +
            ", screenType=" + screenTypeConfig +
            ", input=" + inputConfig +
            ", screenSize=" + screenSizeConfig +
            ", version=" + versionConfig +
            ", screenConfig=" + screenConfig +
            ", screenSizeDp=" + screenSizeDpConfig +
            ", screenConfig2=" + screenConfig2 +
            '}'
        :
        "ResTableConfig{" +
            "size=" + size +
            ", localeScript=" + new String(localeScript) +
            ", localeVariant=" + new String(localeVariant) +
            ", mobileConfig=" + mobileConfig +
            ", localeConfig=" + localeConfig +
            ", screenTypeConfig=" + screenTypeConfig +
            ", inputConfig=" + inputConfig +
            ", screenSizeConfig=" + screenSizeConfig +
            ", versionConfig=" + versionConfig +
            ", screenConfig=" + screenConfig +
            ", screenSizeDpConfig=" + screenSizeDpConfig +
            ", screenConfig2=" + screenConfig2 +
            '}';
  }
}
