package chapter01

object 变量 {

  def main(args: Array[String]): Unit = {
    //声明一个变量通用语法
    var a: Int = 10
    //1)声明变量时，类型可以省略
    var a1 = 10
    //2)类型确定后，就不能修改
    //3)变量声明时，必须要有初始值

    //对象更改和变量变化
    var alice = new Student("alice", 20)
    alice = new Student("Alice", 20)
    alice = null
    val bob = new Student("bob", 23)
    //类传入的属性需要加var才能修改此变量
    bob.age = 24
    bob.printInfo()
  }

}
