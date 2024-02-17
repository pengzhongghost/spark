package 面向对象

import scala.beans.BeanProperty

object 类和对象 {

  def main(args: Array[String]): Unit = {
    val student = new Student()
    println(student.age)
    student.setAge(18)
    println(student.getAge)
    println(student.sex)
  }

}

class Student {

  //私有属性访问不到
  private var name: String = "Alice"

  //getter setter方法注解
  @BeanProperty
  var age: Int = _

  //_就代表初值就是空值，Int代表是0，double代表的是0.0
  var sex: String = _



}