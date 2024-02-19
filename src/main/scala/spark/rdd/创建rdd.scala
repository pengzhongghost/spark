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

    //TODO RDD的并行度
    // RDD的并行度 & 分区
    // 1)makeRDD方法可以传递第二个参数，这个参数表示分区的数量
    // 第二个参数可以不传递的，那么makeRDD方法会使用默认值 ： defaultParallelism（默认并行度）
    //     scheduler.conf.getInt("spark.default.parallelism", totalCores)
    //    spark在默认情况下，从配置对象中获取配置参数：spark.default.parallelism
    //    如果获取不到，那么使用totalCores属性，这个属性取值为当前运行环境的最大可用核数
    //val rdd1 = sc.makeRDD(List(1,2,3,4),2)
    val rdd1 = sc.makeRDD(List(1,2,3,4))
    // 2)textFile可以将文件作为数据处理的数据源，默认也可以设定分区。
    //     minPartitions : 最小分区数量
    //     math.min(defaultParallelism, 2)
    //val rdd = sc.textFile("datas/1.txt")
    // 如果不想使用默认的分区数量，可以通过第二个参数指定分区数
    // Spark读取文件，底层其实使用的就是Hadoop的读取方式
    // 分区数量的计算方式：
    //    totalSize = 7  比如有三个数据1,2,3是3个字节，但是还要加上回车符则为3+4=7个字节
    //    goalSize =  7 / 2 = 3（byte）  每个分区应存放3个字节
    //    7 / 3 = 2...1 (1.1) + 1 = 3(分区)   一共是3个分区

    val rdd2 = sc.textFile("datas/1.txt", 2)

    //TODO 关闭环境
    sc.stop()
  }

}
