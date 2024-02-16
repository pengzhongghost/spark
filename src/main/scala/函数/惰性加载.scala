package 函数

object 惰性加载 {

  def main(args: Array[String]): Unit = {
    lazy val result: Int = sum(13, 47)
    println("1. 函数调用")
    println("2. result = " + result)
    println("4. result = " + result)
    /**
1. 函数调用
3. sum调用
2. result = 60
4. result = 60
     */
  }

  def sum(i: Int, j: Int): Int = {
    println("3. sum调用")
    i + j
  }

}
