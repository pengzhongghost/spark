package chapter01

import scala.collection.immutable

object for循环 {

  def main(args: Array[String]): Unit = {
    //todo 1.范围遍历
    //从1到10
    for (i <- 1 to 10) {
      println(i)
    }
    //to也是方法调用，调用了to方法
    for (i <- 1.to(10)) {
      println(i)
    }
    //不包含10
    for (i <- 1 until 10) {
      println(i)
    }
    for (i <- Range(1, 10)) {
      println(i)
    }
    for (i <- new Range(1, 10, 1)) {
      println(i)
    }
    //todo 2.集合遍历
    for (i <- Array(12, 34, 53)) {
      println(i)
    }
    for (i <- List(12, 34, 53)) {
      println(i)
    }
    for (i <- Set(12, 34, 53)) {
      println(i)
    }
    //todo 3.循环守卫，加判断式
    for (i <- 1 to 3 if i != 2) {
      println(i)
    }
    for (i <- 1 to 3) {
      if (i != 2)
        println(i)
    }
    //todo 4.循环步长 2是步长
    for (i <- 1 to(5, 2)) {
      println(i)
    }
    for (i <- 1 to 5 by 2) {
      println(i)
    }
    for (i <- new Range(1, 5, 2)) {
      println(i)
    }
    //倒序输出
    for (i <- 5 to 1 by -2) {
      println(i)
    }
    for (i <- 1 to 5 reverse) {
      println(i)
    }
    //double类型
    for (i <- 1.0 to 10.0 by 0.5) {
      println(i)
    }
    //todo 5.嵌套循环
    for (i <- 1 to 4) {
      for (j <- 1 to 5) {
        println("i = " + i + ", j = " + j)
      }
    }
    for (i <- 1 to 4; j <- 1 to 5) {
      println("i = " + i + ", j = " + j)
    }
    println("引入变量")
    //todo 6.引入变量
    for (i <- 1 to 3; j = 4 - i) {
      println("i = " + i + ", j = " + j)
    }
    //todo 7.循环返回值
    println("循环返回值")
    //默认是Unit
    val unit: Unit = for (i <- 1 to 10) {
      println(i)
    }
    println(unit)
    //返回其他类型
    //scala中集合类型
    val ints: immutable.IndexedSeq[Int] = for (i <- 1 to 10) yield i
    println(ints)

  }

}
