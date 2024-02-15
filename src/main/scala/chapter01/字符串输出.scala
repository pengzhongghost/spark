package chapter01

object 字符串输出 {

  def main(args: Array[String]): Unit = {
    //1)字符串，通过+号连接
    val name: String = "alice"
    val age: Int = 18
    println(name + "：" + age)
    //2)*用于将一个字符串复制多次并拼接
    println(name * 3)
    //3)printf用法：字符串，通过%传值
    printf("姓名：%s|年龄：%d", name, age)
    //4)字符串模板(插值字符串)，通过$获取变量值
    //s""代表模板字符串
    println(s"${age}岁的${name}")

    val num:Double = 2.3456
    println(f"The num is ${num}%2.2f") //%2.2保留两位小数 格式化模板字符串
    println(raw"The num is ${num}%2.2f")//不会解析%2.2
    //5)三引号表示字符串，保持多行字符串的原格式输出
    val sql = s"""
       |select *
       |  from student
       |where
       |  name = ${name}
       |and
       |  age > ${age}
       |""".stripMargin;//stripMargin表示可以忽略边界，不要输出|和前面的空格
    println(sql)

  }

}
