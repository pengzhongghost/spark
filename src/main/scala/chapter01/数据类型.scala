package chapter01

object 数据类型 {

  def main(args: Array[String]): Unit = {
    //空值
    def m1(): Unit = {
      println("m1被调用执行")
    }

    val a: Unit = m1();
    println(a)

    //空引用Null
    //val n: Int = null;只有引用类型才能赋值为null
    var student = new Student("alice", 20);
    student = null

    //Nothing
    def m2(n: Int): NullPointerException = {
      throw new NullPointerException
    }

    def m3(n: Int): Int = {
      if (0 == n) {
        return n
      }
      throw new NullPointerException
    }

    val c = m3(0)
    println(c)

    val b = m2(0)
    println(b)

  }

}
