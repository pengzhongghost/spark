package spark.算子.转换

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object value类型 {

  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    //[*]表示当前本机的核数是多少，最少2个才能执行
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)
    //TODO 执行业务操作
    //1)map转换算子将1，2，3，4转换为2，4，6，8
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val array: Array[Int] = rdd.map(_ * 2).collect()
    println(array.mkString("Array(", ", ", ")"))

    //2)分区并行执行
    // 1. rdd的计算一个分区内的数据是一个一个执行逻辑
    //    只有前面一个数据全部的逻辑执行完毕后，才会执行下一个数据。
    //    分区内数据的执行是有序的。
    // 2. 不同分区数据计算是无序的。
    val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val mapRDD: RDD[Int] = rdd1.map(
      num => {
        println(">>>>>>>> " + num)
        num
      }
    )
    val mapRDD1: RDD[Int] = mapRDD.map(
      num => {
        println("######" + num)
        num
      }
    )
    mapRDD1.collect()

    //3)mapPartitions
    //将待处理的数据以分区为单位发送到计算节点进行处理，这里的处理是指可以进行任意的处
    //理，哪怕是过滤数据。一次性拿到一个分区的数据，批处理
    println("mapPartitions")
    val rdd2: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    rdd2.mapPartitions(iterator => {
      println(">>>>>>>>")
      iterator.map(_ * 2)
    }).collect().foreach(println)

    //4)mapPartitionsWithIndex
    //只拿到某个分区的数据
    println("mapPartitionsWithIndex")
    val rdd3: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    rdd3.mapPartitionsWithIndex((index, iterator) => {
      if (1 == index) {
        iterator
      } else {
        //空集合的迭代器
        Nil.iterator
      }
    }).collect().foreach(println)


    //TODO 关闭环境
    sc.stop()
  }

}
