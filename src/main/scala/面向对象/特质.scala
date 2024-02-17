package 面向对象

object 特质 {

  def main(args: Array[String]): Unit = {
    val student = new Student5
    student.study()
    student.dating()
    student.play()

    //动态混入特质
    val student1 = new Student5 with Talent {
      override def singing(): Unit = println("唱歌")

      override def dancing(): Unit = println("跳舞")

      override def init(): Unit = println("talent init")

    }
    student1.singing()
    student1.dancing()

    //父类和特质里面全部有init方法时，子类调用super.init()则会调用最后一个的init方法
    student1.init()

  }

}

trait Young {

  //声明抽象和非抽象属性
  var age: Int

  val name: String = "young"

  //声明抽象和非抽象方法
  def play(): Unit = {
    println("young people is playing")
  }

  def dating(): Unit

  def init(): Unit = {
    println("young init")
  }

}

trait Talent {
  def singing(): Unit

  def dancing(): Unit

  def init(): Unit

}

//可以实现多个特质
class Student5 extends Person with Young {

  //父类和特质里面都有name这个属性，所以一定要重写
  override val name: String = "你好"

  override def dating(): Unit = println(s"student $name is dating")

  override var age: Int = _

  def study(): Unit = {
    println(s"student $name is studying")
  }

  override def init(): Unit = super.init()

}

class Person {
  val name: String = "person"

  def init(): Unit = {
    println("person init")
  }

}
