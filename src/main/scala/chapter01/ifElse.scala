package chapter01

object ifElse {

  def main(args: Array[String]): Unit = {
    val age: Int = 5
    val result: String = if (age <= 6) {
      "童年"
    } else if (age < 18) {
      "青少年"
    } else {
      "其他"
    }
    println(result);
  }

}
