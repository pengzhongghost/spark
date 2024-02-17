package 面向对象

object 类型检查和转换 {

  def main(args: Array[String]): Unit = {
    val student1 = new Student17("alice", 18)
    student1.sayHi()
    student1.study()
    val person1 = new Person17("bob", 23)
    person1.sayHi()
    //类型检测
    println(student1.isInstanceOf[Student17])
    println(student1.isInstanceOf[Person17])
    println(person1.isInstanceOf[Student17])
    println(person1.isInstanceOf[Person17])
    //类型转换
    val person2 = student1.asInstanceOf[Person17]
  }

}

class Person17(val name: String, val age: Int) {

  def sayHi(): Unit = {
    println("hi from person " + name)
  }

}

class Student17(name: String, age: Int) extends Person17(name, age) {

  override def sayHi(): Unit = {
    println("hi from student " + name)
  }

  def study(): Unit = {
    println("student " + name + " study")
  }

}
