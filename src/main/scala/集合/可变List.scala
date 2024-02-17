package 集合

import scala.collection.mutable.ListBuffer

object 可变List {

  def main(args: Array[String]): Unit = {
    //（1）创建一个可变集合
    val buffer = ListBuffer(1,2,3,4)
    //（2）向集合中添加数据
    buffer.+=(5)
    buffer.append(6)
    buffer.insert(1,2)
    //（3）打印集合数据
    buffer.foreach(println)
    //（4）修改数据
    buffer(1) = 6
    buffer.update(1,7)
    //（5）删除数据
    buffer.-(5)
    buffer.-=(5)
    buffer.remove(5)
  }

}
