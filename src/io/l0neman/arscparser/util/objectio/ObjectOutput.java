package io.l0neman.arscparser.util.objectio;

import java.io.Closeable;
import java.io.IOException;

/**
 * 对象输入，提供了将数据结构按字节依次写入文件的方法，与 ObjectInput 配合使用。
 * <p>
 * 接口借鉴 C 语言中的 write 函数用法。
 * <p>
 * todo 待实现。
 */
public class ObjectOutput implements Closeable {
  @Override
  public void close() throws IOException {

  }
}
