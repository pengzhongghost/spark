package 面向对象

object 自身类型 {

  def main(args: Array[String]): Unit = {
    val user = new RegisterUser("bob", "123")
    user.insert()
  }

}

class User(val name: String, val password: String)

trait UserDao {

  //相当于引入一个user对象
  _: User =>

  def insert(): Unit = {
    println(s"insert into db：" + this.name)
  }

}

class RegisterUser(name: String, password: String) extends User(name, password) with UserDao
