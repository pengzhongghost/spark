package spark.rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object 创建rdd {

  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    //[*]表示当前本机的核数是多少，最少2个才能执行
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)
    //TODO 创建RDD
    //1.从内存中创建RDD,将内存中集合的数据作为处理的数据源
    val seq = Seq[Int](1, 2, 3, 4)
    val rdd: RDD[Int] = sc.parallelize(seq)
    //val rdd1: RDD[Int] = sc.makeRDD(seq)
    rdd.collect().foreach(println)
    //2.从外部文件创建RDD
    // 1)以行为单位读取数据
    val fileRDD: RDD[String] = sc.textFile("F:\\Java-learn\\hadoop\\资料\\11_input\\inputword\\hello.txt")
    fileRDD.collect().foreach(println)
    // 2)以文件为单位读取数据，读取的结果是一个元组
    val fileRDD1: RDD[(String, String)] = sc.wholeTextFiles("F:\\Java-learn\\hadoop\\资料\\11_input\\inputword\\hello.txt")
    fileRDD1.collect().foreach(println)

    //TODO 关闭环境
    sc.stop()
  }

}
