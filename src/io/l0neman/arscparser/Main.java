package io.l0neman.arscparser;

import io.l0neman.arscparser.core.ArscParser;
import io.l0neman.arscparser.xml.AXmlPrinter;

import java.io.IOException;

public class Main {

  public static void main(String[] args) {

    // 解析 arsc 文件。
    parseArscFile();

    // 解析二进制 xml 文件。
    // parseBinaryXmlFile();
  }

  private static void parseArscFile() {
    try {
      ArscParser arscParser = new ArscParser();
      arscParser.parse("./file/com.muh2.icon/resources.arsc");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void parseBinaryXmlFile() {
    try {
      new AXmlPrinter().print("./file/com.muh2.icon/AndroidManifest.xml");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
