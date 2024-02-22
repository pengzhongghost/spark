package spark.持久化

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

object 持久化 {

  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    //[*]表示当前本机的核数是多少，最少2个才能执行
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)
    //TODO 执行业务操作
    val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

    val mapRDD: RDD[(Int, Int)] = rdd1.map(word => {
      println("@@@@@@@@@")
      (word, 1)
    })
    //1)数据放在内存中可以持久使用
    mapRDD.cache()
    mapRDD.collect()
    mapRDD.collect()
    //2)存储到磁盘
    mapRDD.persist(StorageLevel.DISK_ONLY)

    //3)checkpoint 需要落盘，需要指定检查点保存路径
    // 检查点路径保存的文件，当作业执行完毕后，不会被删除
    // 一般保存路径都是在分布式存储系统：HDFS
    mapRDD.checkpoint()



    //TODO 关闭环境
    sc.stop()
  }

}
