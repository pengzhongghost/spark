package 函数

object Test {

  def main(args: Array[String]): Unit = {
    //随处都可以定义函数，而类中的函数称为方法
    //1)函数定义
    def f(arg: String): Unit = {
      println("in:" + arg)
    }
    //2)函数调用
    f("hello")
    Test.f("hello");

  }

  //方法
  def f(arg: String): Unit = {
    println("out:" + arg)
  }

}
