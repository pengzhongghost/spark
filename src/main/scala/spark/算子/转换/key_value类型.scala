package spark.算子.转换

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object key_value类型 {

  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    //[*]表示当前本机的核数是多少，最少2个才能执行
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)
    //TODO 执行业务操作
    val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    val mapRDD: RDD[(Int, Int)] = rdd1.map((_, 1))
    //1)partitionBy
    //RDD => PairRDDFunctions
    //将数据按照指定 Partitioner 重新进行分区。Spark 默认的分区器是 HashPartitioner
    mapRDD.partitionBy(new HashPartitioner(2))
    //.saveAsTextFile("output")

    //2)reduceByKey
    val rdd2: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("a", 2)))
    rdd2.reduceByKey(_ + _).collect().foreach(println)

    //3)groupByKey
    //从 shuffle 的角度：reduceByKey 和 groupByKey 都存在 shuffle 的操作，但是 reduceByKey
    //可以在 shuffle 前对分区内相同 key 的数据进行预聚合（combine）功能，这样会减少落盘的
    //数据量，而 groupByKey 只是进行分组，不存在数据量减少的问题，reduceByKey 性能比较
    //高。
    //从功能的角度：reduceByKey 其实包含分组和聚合的功能。GroupByKey 只能分组，不能聚
    //合，所以在分组聚合的场合下，推荐使用 reduceByKey，如果仅仅是分组而不需要聚合。那
    //么还是只能使用 groupByKey
    println("groupByKey")
    val rdd3: RDD[(String, Int)] =
      sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3)))
    //rdd3.groupByKey().collect().foreach(println)
    //rdd3.groupByKey(2).collect().foreach(println)
    //    rdd3.groupByKey(new HashPartitioner(2)).collect().foreach(println)

    //4)aggregateByKey:将数据根据不同的规则进行分区内计算和分区间计算
    //存在函数柯里化，有两个参数列表
    // 第一个参数列表,需要传递一个参数，表示为初始值
    //       主要用于当碰见第一个key的时候，和value进行分区内计算
    // 第二个参数列表需要传递2个参数
    //      第一个参数表示分区内计算规则
    //      第二个参数表示分区间计算规则
    println("aggregateByKey")
    val rdd4: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3), ("a", 4)
    ), 2)
    //(a,[1,2]),(a,[3,4]) => (a,2),(a,4) => (a,6)
    rdd4.aggregateByKey(0)(
      (x, y) => math.max(x, y),
      (x, y) => x + y
    ).collect.foreach(println)

    //5)foldByKey:如果聚合计算时，分区内和分区间计算规则相同
    println("foldByKey")
    val rdd5: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)
    ), 2)
    rdd5.foldByKey(0)(_ + _).collect.foreach(println)

    //6)combineByKey：当计算时，发现数据结构不满足要求时，可以让第一个数据转换结构。分区
    println("combineByKey")
    val rdd6: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)
    ), 2)

    // 方法需要三个参数
    // 第一个参数表示：将相同key的第一个数据进行结构的转换，实现操作
    // 第二个参数表示：分区内的计算规则
    // 第三个参数表示：分区间的计算规则
    val newRDD: RDD[(String, (Int, Int))] = rdd6.combineByKey(
      v => (v, 1),
      (t: (Int, Int), v) => {
        (t._1 + v, t._2 + 1)
      },
      (t1: (Int, Int), t2: (Int, Int)) => {
        (t1._1 + t2._1, t1._2 + t2._2)
      }
    )

    //7)join：在类型为(K,V)和(K,W)的 RDD 上调用，返回一个相同 key 对应的所有元素连接在一起的
    //(K,(V,W))的 RDD
    println("join")
    val rdd7: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("c", 3)
    ))

    val rdd8: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 5), ("c", 6), ("a", 4), ("b", 7)
    ))

    // join : 两个不同数据源的数据，相同的key的value会连接在一起，形成元组
    //        如果两个数据源中key没有匹配上，那么数据不会出现在结果中
    //        如果两个数据源中key有多个相同的，会依次匹配，可能会出现笛卡尔乘积，数据量会几何性增长，会导致性能降低。
    val joinRDD: RDD[(String, (Int, Int))] = rdd7.join(rdd8)
    joinRDD.collect().foreach(println)

    //8)leftOuterJoin&rightJoinRDD
    println("leftOuterJoin&rightJoinRDD")
    val rdd9: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("b", 2) //, ("c", 3)
    ))

    val rdd10: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 4), ("b", 5), ("c", 6)
    ))
    val leftJoinRDD: RDD[(String, (Int, Option[Int]))] = rdd9.leftOuterJoin(rdd10)
    // val rightJoinRDD: RDD[(String, (Option[Int], Int))] = rdd9.rightOuterJoin(rdd10)

    leftJoinRDD.collect().foreach(println)
    //rightJoinRDD.collect().foreach(println)

    //9)cogroup：connect + group ：相同的key分组之后连接在一起
    println("cogroup")
    val rdd11: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("b", 2)//, ("c", 3)
    ))
    val rdd12: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 4), ("b", 5),("c", 6),("c", 7)
    ))
    val cgRDD: RDD[(String, (Iterable[Int], Iterable[Int]))] = rdd11.cogroup(rdd12)
    cgRDD.collect().foreach(println)

    //TODO 关闭环境
    sc.stop()
  }

}
