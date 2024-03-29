package chapter01

/**
 * object：关键字，声明一个单例对象(伴生对象)
 * 实际上就是java的final class
 */
object HelloWorld {

  /**
   * main 从外部可以直接调用执行的方法
   * def 方法名称(参数名称：参数类型[泛型])：返回值类型 ={方法体}
   */
  def main(args: Array[String]): Unit = {
    //打印hello world
    println("hello world")
    //scala也可以直接调用java
    System.out.println("hello scala from java")

  }

}
