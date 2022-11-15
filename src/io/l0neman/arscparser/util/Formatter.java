package io.l0neman.arscparser.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Formatter {
  public static String toHex(byte[] b) {
    StringBuilder stringBuilder = new StringBuilder("0x");
    for (byte b1 : b) {
      int v = b1 & 0xFF;
      String h = Integer.toHexString(v);
      if (h.length() < 2) {
        stringBuilder.append(0);
      }
      stringBuilder.append(h);
    }
    return stringBuilder.toString();
  }

  public static byte[] fromInt(int a, boolean bigEndian) {
    return bigEndian ?
        new byte[]{
            (byte) ((a >> 24) & 0xFF),
            (byte) ((a >> 16) & 0xFF),
            (byte) ((a >> 8) & 0xFF),
            (byte) ((a) & 0xFF)
        } :
        new byte[]{
            (byte) ((a) & 0xFF),
            (byte) ((a >> 8) & 0xFF),
            (byte) ((a >> 16) & 0xFF),
            (byte) ((a >> 24) & 0xFF)
        };
  }

  public static String toUtf16String(byte[] bytes) {
    return new String(bytes, 0, bytes.length, StandardCharsets.UTF_16LE);
  }
}
