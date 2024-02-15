package chapter01

/**
 * scala中正常类，类似Java中的普通class
 */
class Student(name: String, var age: Int) {

  def printInfo(): Unit = {
    //Student.school伴生对象类似解决了java中的static属性
    println(name + " " + age + " " + Student.school)
  }

}

/**
 * 引入伴生对象,main方法只能放在object类中
 * object：关键字，声明一个单例对象(伴生对象)
 * 实际上就是java的final class
 * 这里实际上生成的是Student$类
 */
object Student {

  val school: String = "DongTai"

  def main(args: Array[String]): Unit = {
    val student = new Student("alice", 20)
    student.printInfo()
  }

}
