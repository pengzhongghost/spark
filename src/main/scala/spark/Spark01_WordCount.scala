package spark

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_WordCount {

  def main(args: Array[String]): Unit = {
    //TODO 建立和spark框架的连接
    val conf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(conf)
    //TODO 执行业务操作
    //1.读取文件，获取一行一行的数据
    val lines = sc.textFile("F:\\Java-learn\\hadoop\\资料\\11_input\\inputword\\hello.txt")
    //"hello world => hello, world
    //第一种方式
//    val array = lines.flatMap(_.split(" "))
//      //根据单词分组
//      .groupBy(word => word)
//      //分组后的第二项变成统计值
//      .map {
//        case (word, list) =>
//          (word, list.size)
//      }.collect()
    //第二种方式
    val array = lines.flatMap(_.split(" "))
      .map(word => (word, 1))
      //spark框架提供的功能，可以将分组和聚合使用一个方法实现，相同key的数据，可以对value进行reduce聚合
      .reduceByKey(_ + _).collect()


    //2.打印结果
    println(array.mkString("Array(", ", ", ")"))
    //TODO 关闭连接
    sc.stop()
  }

}
