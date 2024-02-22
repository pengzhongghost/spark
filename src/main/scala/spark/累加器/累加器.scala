package spark.累加器

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

object 累加器 {

  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    //[*]表示当前本机的核数是多少，最少2个才能执行
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)
    //TODO 创建RDD
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    //reduce:分区内计算，分区间计算
    //    val res: Int = rdd.reduce(_ + _)
    //    println(res)
    // 获取系统累加器
    // Spark默认就提供了简单数据聚合的累加器
    // 少加的情况：累加器在转换算子中，如果没有触发行动算子，则不会执行
    // 多加的情况：rdd.collect()执行了两遍则会出现多加的情况
    // 一般情况下放置在行动算子中进行操作
    val sumAcc: LongAccumulator = sc.longAccumulator("sum")
    rdd.foreach(num => {
      sumAcc.add(num)
    })
    println(sumAcc.value)
    //TODO 关闭环境
    sc.stop()
  }

}
