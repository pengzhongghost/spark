package 函数

import scala.annotation.tailrec

object 递归 {

  def main(args: Array[String]): Unit = {
    println(dfs(3))
    println(tailDfs(3))
  }

  //递归实现计算阶乘
  def dfs(n: Int): Int = {
    if (0 == n) return 1
    n * dfs(n - 1)
  }

  //尾递归
  def tailDfs(n: Int): Int = {
    //加上这个注解如果不是尾递归直接报错
    @tailrec
    def loop(n: Int, curRes: Int): Int = {
      if (0 == n) return curRes
      loop(n - 1, curRes * n)
    }
    loop(n, 1)
  }

}
