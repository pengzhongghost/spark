package 模式匹配

object MatchCase {

  def main(args: Array[String]): Unit = {

    var a: Int = 10
    var b: Int = 20
    var operator: Char = '+'
    var result = operator match {
      case '+' => a + b
      case '-' => a - b
      case '*' => a * b
      case '/' => a / b
      case _ => "illegal"
    }
    println(result)

    //模式守卫，求一个整数的绝对值
    def abs(num: Int): Int = {
      num match {
        case i if i >= 0 => i
        case i if i < 0 => -i
      }
    }
    println(abs(67))
    println(abs(-6))

    //模式匹配可以匹配所有的字面量包括字符串，字符，数字，布尔值等等。
    def describe(x: Any) = x match {
      case 5 => "Int five"
      case "hello" => "String hello"
      case true => "Boolean true"
      case '+' => "Char +"
    }

    //匹配类型
    def describe2(x: Any) = x match {
      case i: Int => "Int"
      case s: String => "String hello"
      case m: List[_] => "List"
      case c: Array[Int] => "Array[Int]"
      case someThing => "something else " + someThing
    }

    //匹配数组
    for (arr <- Array(Array(0), Array(1, 0), Array(0, 1, 0),
      Array(1, 1, 0), Array(1, 1, 0, 1), Array("hello", 90))) { // 对一个数组集合进行遍历
      val result = arr match {
        case Array(0) => "0" //匹配 Array(0) 这个数组
        case Array(x, y) => x + "," + y //匹配有两个元素的数组，然后将将元素值赋给对应的 x,y
        case Array(0, _*) => "以 0 开头的数组" //匹配以 0 开头和数组
        case _ => "something else"
      }
      println("result = " + result)
    }

    //匹配列表
    //list 是一个存放 List 集合的数组
    //请思考，如果要匹配 List(88) 这样的只含有一个元素的列表,并原值返回.应该怎么写
    for (list <- Array(List(0), List(1, 0), List(0, 0, 0), List(1,
      0, 0), List(88))) {
      val result = list match {
        case List(0) => "0" //匹配 List(0)
        case List(x, y) => x + "," + y //匹配有两个元素的 List
        case List(0, _*) => "0 ..."
        case _ => "something else"
      }
      println(result)
    }

    //1-2-List(5, 6, 7)
    val list: List[Int] = List(1, 2, 5, 6, 7)
    list match {
      case first :: second :: rest => println(first + "-" +
        second + "-" + rest)
      case _ => println("something else")
    }

    //匹配元组
    //对一个元组集合进行遍历
    for (tuple <- Array((0, 1), (1, 0), (1, 1), (1, 0, 2))) {
      val result = tuple match {
        case (0, _) => "0 ..." //是第一个元素是 0 的元组
        case (y, 0) => "" + y + "0" // 匹配后一个元素是 0 的对偶元组
        case (a, b) => "" + a + " " + b
        case _ => "something else" //默认
      }
      println(result)
    }

    //匹配对象
    val user: User = User("zhangsan", 11)
    val result1 = user match {
      case User("zhangsan", 11) => "yes"
      case _ => "no"
    }
    println(result1)

    //匹配样例类
    /**
     * 说明
      ○1 样例类仍然是类，和普通类相比，只是其自动生成了伴生对象，并且伴生对象中
      自动提供了一些常用的方法，如 apply、unapply、toString、equals、hashCode 和 copy。
      ○2 样例类是为模式匹配而优化的类，因为其默认提供了 unapply 方法，因此，样例
      类可以直接使用模式匹配，而无需自己实现 unapply 方法。
      ○3 构造器中的每一个参数都成为 val，除非它被显式地声明为 var（不建议这样做）
     */
    val user2: User2 = User2("zhangsan", 11)
    val result2 = user2 match {
      case User2("zhangsan", 11) => "yes"
      case _ => "no"
    }
    println(result2)

  }

}

class User(val name: String, val age: Int)

object User{
  def apply(name: String, age: Int): User = new User(name, age)
  def unapply(user: User): Option[(String, Int)] = {
    if (user == null)
      None
    else
      Some(user.name, user.age)
  }
}
//样例类
case class User2(name: String, age: Int)