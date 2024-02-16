package chapter01

object 运算符 {

  def main(args: Array[String]): Unit = {
    val s1: String = "hello"
    val s2: String = new String("hello")
    //scala中的==就是java的equals
    println(s1 == s2)
    println(s1.equals(s2))
    //eq比较的是内存引用地址，和java的==一样
    println(s1.eq(s2))

    //运算符的本质：scala中就是方法调用
    val n1: Int = 12
    val n2: Int = 37
    //n1+n2变成了方法调用
    println(n1.+(n2))
    val d = 1.34.*(25)
    println(d)

    println(7.5 toString)
    //转换为Int再转换为string
    println(7.5 toInt toString)
    println(7.5.toString)

  }

}
