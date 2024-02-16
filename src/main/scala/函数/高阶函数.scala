package 函数

object 高阶函数 {

  def main(args: Array[String]): Unit = {
    def f(a: String): Unit = {
      println(a)
    }

    //1.函数作为值进行传递
    //不会调用函数f，只是函数f的本体
    val f1 = f _
    //String参数，返回值Unit
    val f2: String => Unit = f
    f1("你好")
    f2("你好")

    //2.函数作为参数进行传递
    def dualEval(op: (Int, Int) => Int, a: Int, b: Int): Int = {
      op(a, b)
    }

    def add(a: Int, b: Int): Int = {
      a + b
    }

    val res = dualEval(add, 1, 2)
    println(res)
    dualEval((a, b) => a + b, 1, 2)
    dualEval(_ + _, 1, 2)

    //3.函数作为函数的返回值返回
    def f5(): Int => Unit = {
      def f6(a: Int): Unit = {
        println("f6调用" + a)
      }

      f6
    }
    println(f5())
    //调用
    //1)
    f5().apply(6)
    //2)
    val f6 = f5()
    f6(1)
    //3)
    f5()(25)
  }

}
