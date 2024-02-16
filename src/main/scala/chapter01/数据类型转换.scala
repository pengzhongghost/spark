package chapter01

object 数据类型转换 {

  def main(args: Array[String]): Unit = {
    //1)自动提升原则：有多种类型数据混合运算时，系统首先自动将所有数据转换为精度大的那种数据类型
    val a1: Byte = 10
    val b1: Long = 2353L
    val result1: Long = a1 + b1
    //强转
    val result2: Int = (a1 + b1).toInt
    //2)把精度大的数值类型赋值给精度小的数值类型时，就会报错，反之就会进行自动类型转换
    //只能强转
    //3)(byte,short)和char之间不会相互自动转换
    //4)byte,short,char他们三者可以计算，在计算时首先转换为int类型
    val a4: Byte = 12
    val b4: Short = 25
    val c4: Char = 'c'
    val result4: Int = a4 + b4 + c4
    println(result4)

    //强转
    val n: Int = "12.3".toDouble.toInt;

    //强转精度丢失的情况
    val n2: Int = 128
    val b: Byte = n2.toByte
    println(b)

  }

}
