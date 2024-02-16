package 函数

object 函数至简原则 {

  def main(args: Array[String]): Unit = {
    def f0(name: String): String = {
      return name
    }

    println(f0("nihao"))

    //1)return可以省略
    def f1(name: String): String = {
      name
    }

    //2)如果函数体只有一行代码，可以省略花括号
    def f2(name: String): String = name

    //3)返回值类型如果能够推断出来，可以省略
    def f3(name: String) = name

    //4)如果有return，则不能省略返回值类型，必须指定
    def f4(name: String): String = return name

    //5)如果函数明确声明unit，那么即使函数体中使用return关键字也不起作用
    def f5(name: String): Unit = return name

    //6)scala如果期望的是无返回值类型，可以省略等号
    def f6(name: String) {
      println(name)
    }

    //7)如果函数无参，但是声明了参数列表，那么调用时，小括号，可加可不加
    def f7(): Unit = {
      println("无参")
    }

    f7
    f7()

    //8)如果函数没有参数列表，那么小括号可以省略，调用时小括号必须省略
    def f8: Unit = {
      println("无参无括号")
    }

    f8

    //9)如果不关心名称，只关系逻辑处理，那么函数名(def)可以省略
    def f9(name: String): Unit = {
      println(name)
    }
    //匿名函数，lambda表达式
    (name: String) => {
      println(name)
    }

  }

}
