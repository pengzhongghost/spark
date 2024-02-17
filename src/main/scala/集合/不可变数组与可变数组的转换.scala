package 集合

import scala.collection.mutable.ArrayBuffer

object 不可变数组与可变数组的转换 {

  def main(args: Array[String]): Unit = {
    //（1）创建一个空的可变数组
    val arr2 = ArrayBuffer[Int]()
    //（2）追加值
    arr2.append(1, 2, 3)
    println(arr2) // 1,2,3
    //（3）ArrayBuffer ==> Array
    //（3.1）arr2.toArray 返回的结果是一个新的定长数组集合
    //（3.2）arr2 它没有变化
    val newArr = arr2.toArray
    println(newArr)
    //（4）Array ===> ArrayBuffer
    //（4.1）newArr.toBuffer 返回一个变长数组 newArr2
    //（4.2）newArr 没有任何变化，依然是定长数组
    val newArr2 = newArr.toBuffer
    newArr2.append(123)
    println(newArr2)
  }

}
