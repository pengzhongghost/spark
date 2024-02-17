package 集合

object 不可变数组 {

  def main(args: Array[String]): Unit = {
    //1.创建数组
    val arr01 = new Array[Int](10)
    val arr02 = Array.apply(12, 37, 42)
    val arr03 = Array(12, 37, 42)
    //2.访问数组
    println(arr02(0))
    //3.赋值
    arr02(0) = 1
    //（3）遍历数组
    //（3.1）查看数组
    println(arr02.mkString(","))
    //（3.2）普通遍历
    for (i <- arr02) {
      println(i)
    }
    //（3.3）简化遍历
    def printx(elem:Int): Unit = {
      println(elem)
    }
    arr02.foreach(printx)
    //（4）增加元素（由于创建的是不可变数组，增加元素，其实是产生新的数组）
    //添加新元素5，new了一个新数组，5作为新元素放在最后
    val ints: Array[Int] = arr02 :+ 5
    println(ints.mkString("Array(", ", ", ")"))
    //在数组前添加元素
    val ints1 = arr02.+:(5)
    //连续添加元素
    val ints2 = 3 +: 5 +: arr02
    println(ints1.mkString("Array(", ", ", ")"))

  }

}
