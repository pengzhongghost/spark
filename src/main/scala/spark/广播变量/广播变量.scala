package spark.广播变量

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object 广播变量 {

  def main(args: Array[String]): Unit = {
    val sparConf: SparkConf = new SparkConf().setMaster("local").setAppName("Acc")
    val sc = new SparkContext(sparConf)

    val rdd1: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))
    //        val rdd2 = sc.makeRDD(List(
    //            ("a", 4),("b", 5),("c", 6)
    //        ))

    val map: mutable.Map[String, Int] = mutable.Map(("a", 4),("b", 5),("c", 6))

    // 封装广播变量
    val bc: Broadcast[mutable.Map[String, Int]] = sc.broadcast(map)

    // join会导致数据量几何增长，并且会影响shuffle的性能，不推荐使用
    // 用map性能更加好
    //val joinRDD: RDD[(String, (Int, Int))] = rdd1.join(rdd2)
    //joinRDD.collect().foreach(println)
    // (a, 1),    (b, 2),    (c, 3)
    // (a, (1,4)),(b, (2,5)),(c, (3,6))
    rdd1.map {
      case (w, c) => {
        val l: Int = bc.value.getOrElse(w, 0)
        (w, (c, l))
      }
    }.collect().foreach(println)


    sc.stop()
  }

}
