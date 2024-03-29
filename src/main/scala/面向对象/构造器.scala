package 面向对象

object 构造器 {

  def main(args: Array[String]): Unit = {
    val student1 = new Student1()
    val student2 = new Student1("bob", 18)
    val student3 = new Student2("alice", 20)
    println(student3.name)
  }

}

class Student1() {

  var name: String = _
  var age: Int = _

  println("1. 主构造方法被调用")

  //声明辅助构造方法
  def this(name: String) {
    this() //直接调用主构造器
    println("2. 辅助构造方法一被调用")
    this.name = name
  }

  def this(name: String, age: Int) {
    this(name)
    println("3. 辅助构造方法二被调用")
    this.age = age
  }

}

//上面的定义等价于
class Student2(var name: String, var age: Int)

//主构造器参数无修饰var，val，则只是形参
class Student3(name: String, age: Int) {

}
