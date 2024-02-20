package spark.算子.转换

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object 双value类型 {

  def main(args: Array[String]): Unit = {
    /**
     * 双value类型：就是两个RDD
     */
    //TODO 准备环境
    //[*]表示当前本机的核数是多少，最少2个才能执行
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)
    //TODO 执行业务操作
    val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val rdd2: RDD[Int] = sc.makeRDD(List(3, 4, 5, 6))
    //交集  [3,4]
    println(rdd1.intersection(rdd2).collect().mkString(","))
    //并集  [1,2,3,4,3,4,5,6]
    println(rdd1.union(rdd2).collect().mkString(","))
    //差集  [1,2]
    println(rdd1.subtract(rdd2).collect().mkString(","))
    //拉链  将两个 RDD 中的元素，以键值对的形式进行合并。
    // 其中，键值对中的 Key 为第 1 个 RDD
    //中的元素，Value 为第 2 个 RDD 中的相同位置的元素。
    //(1,3),(2,4),(3,5),(4,6)
    println(rdd1.zip(rdd2).collect().mkString(","))

    //TODO 关闭环境
    sc.stop()
  }

}
