package 函数

object 闭包和函数柯里化 {

  def main(args: Array[String]): Unit = {
    //闭包:如果一个函数，访问到了它的外部(局部)变量的值，那么这个函数和他所处的环境，称为闭包
    def addA(a: Int): Int => Int = {
      def addB(b: Int): Int = {
        a + b
      }

      addB
    }

    println(addA(2)(8))
    val addA2 = addA(4)
    println(addA2(5))

    //lambda表达式简写
    def addA3(a: Int): Int => Int = {
      (b: Int) => a + b
    }

    def addA4(a: Int): Int => Int = {
      b => a + b
    }

    def addA5(a: Int): Int => Int = a + _

    //函数柯里化:把一个参数列表的多个参数，变成多个参数列表
    def addCurring(a: Int)(b: Int): Int = {
      a + b
    }
    println(addCurring(1)(2))


  }

}
