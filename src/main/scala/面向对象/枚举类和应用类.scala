package 面向对象

object 枚举类和应用类 {

  def main(args: Array[String]): Unit = {
    println(WorkDay.MONDAY)
    println(WorkDay.MONDAY.id)
  }

}

//枚举类
object WorkDay extends Enumeration {

  val MONDAY = Value(1, "星期一")
  val TUESDAY = Value(2, "星期二")

}

//应用类
//继承App，包装了main方法，就不需要显式定义main方法了，可以直接执行。
object TestApp extends App {

  println("hello,world!")

  type MyString = String
  val a:MyString = "abc"
  println(a)

}