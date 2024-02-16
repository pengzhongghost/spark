package chapter01

import scala.util.control.Breaks
import scala.util.control.Breaks._

object 循环中断 {

  def main(args: Array[String]): Unit = {
    //1.抛出异常跳出循环
    try {
      for (i <- 0 until 5) {
        if (3 == i)
          throw new RuntimeException
      }
    } catch {
      case e: Exception => //什么都不做，只是跳出循环
    }
    //2.使用scala中的Breaks类的break方法，实现异常的抛出和捕捉，其实里面就是try catch
    Breaks.breakable(
      for (i <- 0 until 5) {
        if (3 == i)
          Breaks.break()
        println(i)
      }
    )
    //import scala.util.control.Breaks._
    //类似于java的.*引入
    breakable(
      for (i <- 0 until 5) {
        if (3 == i)
          break()
        println(i)
      }
    )

  }

}
