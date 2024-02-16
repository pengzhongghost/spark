package 函数

object Lambda {

  def main(args: Array[String]): Unit = {

    val fun = (name: String) => {
      println(name)
    }
    fun("function")

    //lambda表达式一般用在函数的参数上面
    def f(func: String => Unit): Unit = {
      func("函数入参")
    }

    f(fun)
    f((name: String) => {
      println(name)
    })

    //匿名函数的简化原则
    //1)参数的类型可以省略，会根据形参进行自动的推导
    f((name) => {
      println(name)
    })
    //2)如果参数只有一个参数，则小括号可以省略；没有参数或参数大于1个的不能省略括号
    f(name => {
      println(name)
    })
    //3)匿名函数如果只有一行，则大括号可以省略
    f(name => println(name))
    //4)如果参数只出现一次，则参数省略且后面参数可以用_代替
    f(println(_))
    //5)如果可以推断出，当前传入的println是一个函数体，而不是调用语句，可以直接省略下划线
    f(println)

  }

}
