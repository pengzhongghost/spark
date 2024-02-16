package 函数

import scala.annotation.tailrec

object 自定义while循环 {

  def main(args: Array[String]): Unit = {
    var n = 10

    def myWhile(condition: => Boolean): (=> Unit) => Unit = {
      def doLoop(op: => Unit): Unit = {
        if (condition) {
          op
          myWhile(condition)(op)
        }
      }

      doLoop _
    }

    myWhile(n >= 1) {
      println(n)
      n -= 1
    }

    n = 10

    //化简版 匿名函数实现
    def myWhile2(condition: => Boolean): (=> Unit) => Unit = {
      op => {
        if (condition) {
          op
          myWhile2(condition)(op)
        }
      }
    }

    myWhile2(n >= 1) {
      println(n)
      n -= 1
    }

    n = 10

    //柯里化简版 匿名函数实现
    @tailrec
    def myWhile3(condition: => Boolean)(op: => Unit): Unit = {
      if (condition) {
        op
        myWhile3(condition)(op)
      }
    }

    myWhile3(n >= 1) {
      println(n)
      n -= 1
    }

  }

}
