package 函数

object 控制抽象 {

  def main(args: Array[String]): Unit = {
    //1.传值参数
    def f0(a: Int): Unit = {
      println(a)
    }

    f0(23)

    //2.传名参数，传递的不再是具体的值，而是代码块
    def f1(): Int = {
      println("f1")
      12
    }

    def f2(a: => Int): Unit = {
      println(a)
      println(a)
    }

    f2(f1())



  }

}
