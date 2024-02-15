package chapter01

import java.io.{File, PrintWriter}
import scala.io.Source

object 文件输入输出 {

  def main(args: Array[String]): Unit = {
    //1.从文件中读取数据
    Source.fromFile("F:\\Java-learn\\hadoop\\资料\\11_input\\inputword\\hello.txt").foreach(print)
    //2.将数据写入文件
    val writer = new PrintWriter(new File("E:\\output1\\test.txt"));
    writer.write("hello scala from java writer")
    writer.close()
  }

}
