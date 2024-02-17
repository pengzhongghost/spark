package 集合

object 多维数组 {

  def main(args: Array[String]): Unit = {
    //（1）创建了一个二维数组, 有三个元素，每个元素是，含有 4 个元素一维数组()
    val arr = Array.ofDim[Int](3, 4)
    arr(1)(2) = 88
    //（2）遍历二维数组
    for (i <- arr) { //i 就是一维数组
      for (j <- i) {
        print(j + " ")
      }
      println()
    }
  }

}
