package io.l0neman.arscparser;

import io.l0neman.arscparser.core.ArscPackageWriter;
import io.l0neman.arscparser.core.ArscParser;
import io.l0neman.arscparser.xml.AXmlPrinter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
      arscParser.parse("./file/resources_test.arsc");

      ArscPackageWriter writer = new ArscPackageWriter();
      writer.setPackageName("ICON.PACK.PACKAGE.NAME");
      writer.write("./file/resources0.arsc", "./file/resources1.arsc");
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
