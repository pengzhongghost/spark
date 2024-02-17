package 集合

import scala.collection.mutable.ArrayBuffer

object 可变数组 {

  def main(args: Array[String]): Unit = {
    //（1）创建并初始赋值可变数组
    val arr01 = ArrayBuffer[Any](1, 2, 3)
    //（2）遍历数组
    for (i <- arr01) {
      println(i)
    }
    println(arr01.length) // 3
    println("arr01.hash=" + arr01.hashCode())
    //（3）增加元素
    //（3.1）追加数据
    arr01.+=(4)
    //（3.2）向数组最后追加数据
    arr01.append(5,6)
    //（3.3）向指定的位置插入数据
    arr01.insert(0,7,8)
    println("arr01.hash=" + arr01.hashCode())
    //（4）修改元素
    arr01(1) = 9 //修改第 2 个元素的值
    println("--------------------------")
    for (i <- arr01) {
      println(i)
    }
    println(arr01.length) // 5
  }

}
