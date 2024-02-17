package 面向对象

object 单例设计模式 {

  def main(args: Array[String]): Unit = {
    val student = Student4.getInstance()
  }

}

//构造器私有化
class Student4 private(val name: String, val age: Int) {

}

//饿汉式
//object Student4 {
//
//  private val student: Student4 = new Student4("bob", 18);
//
//  def getInstance(): Student4 = student
//
//}

//懒汉式
object Student4 {

  private var student: Student4 = _

  def getInstance(): Student4 = {
    if (null == student) {
      student = new Student4("bob", 18)
    }
    student
  }

}
