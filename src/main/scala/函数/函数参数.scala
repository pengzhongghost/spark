package 函数

object 函数参数 {

  def main(args: Array[String]): Unit = {
    //1)可变参数
    //这里的参数是一个集合类型
    def f1(str: String*): Unit = {
      println(str)
    }

    f1("alice", "bob")

    //2)如果参数列表中存在多个参数，那么可变参数一般放置在最后
    def f2(str1: String, str2: String*): Unit = {
      println("str1：" + str1)
      println("str2：" + str2)
    }

    f2("alice")
    f2("aaa", "bbb", "ccc")

    //3)参数默认值，一般将有默认值的参数放置在参数列表的后面
    def f3(name: String = "nihao"): Unit = {
      println("name：" + name)
    }

    f3()
    f3("liubei")

    //4)带名参数
    def f4(name: String = "nihao", age: Int): Unit = {
      println("name：" + name + "|age：" + age)
    }

    f4("haode", 18)
    f4(age = 20)


  }

}
