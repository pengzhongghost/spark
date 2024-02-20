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

    //5)glom:将同一个分区的数据直接转换为相同类型的内存数组进行处理，
    // 分区不变
    println("glom")
    val rdd4: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    //int => Array
    val glomRDD: RDD[Array[Int]] = rdd4.glom()
    glomRDD.collect().foreach(data => println(data.mkString(",")))
    //测试：将[1,2][3,4]转成取其中最大值再相加[2],[4]=>[6]
    val rdd5: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val glomRDD1: RDD[Array[Int]] = rdd5.glom()
    val maxRDD: RDD[Int] = glomRDD1.map(array => array.max)
    println(maxRDD.collect().sum)

    //6)groupBy 分组和分区没有必然的关系
    //将数据根据指定的规则进行分组, 分区默认不变，但是数据会被打乱重新组合，我们将这样
    //的操作称之为 shuffle
    //一个组的数据在一个分区中，但是并不是说一个分区中只有一个组
    println("groupBy")
    val rdd6: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    //(0,CompactBuffer(2, 4))
    //(1,CompactBuffer(1, 3))
    rdd6.groupBy(data => data % 2).collect().foreach(println)

    //7)filter
    println("filter")
    val rdd7: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    rdd7.filter(num => num % 2 != 0).collect().foreach(println)

    //8)sample算子需要传递三个参数
    // 1. 第一个参数表示，抽取数据后是否将数据返回 true（放回），false（丢弃）
    // 2. 第二个参数表示，
    //         如果抽取不放回的场合：数据源中每条数据被抽取的概率，基准值的概念
    //         如果抽取放回的场合：表示数据源中的每条数据被抽取的可能次数
    // 3. 第三个参数表示，抽取数据时随机算法的种子
    //                    如果不传递第三个参数，那么使用的是当前系统时间
    //sample针对分区中抽取数据可以用来判断shuffle造成的数据倾斜
    println("sample")
    val rdd8: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    println(rdd8.sample(
      withReplacement = false,
      //如果传的第三个参数是固定的，则每次都是抽取到1,4,8,9
      0.4,
      //如果第三个参数不传，则随机算法的种子是当前时间，每次取到的数就会不一样
      1
    ).collect().mkString(","))

    //9)distinct
    println("distinct")
    val rdd9 = sc.makeRDD(List(
      1,2,3,4,1,2
    ),1)
    val dataRDD1 = rdd9.distinct()
    val dataRDD2 = rdd9.distinct(2)

    //10)coalesce 缩减分区
    println("coalesce")
    val rdd10: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 4)
    val newRDD: RDD[Int] = rdd10.coalesce(2)
    newRDD.saveAsTextFile("output")
    //coalesce也可用扩大分区，但是如果不进行shuffle操作，是没有作用的
    val rdd11: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 2)
    val newRDD1: RDD[Int] = rdd11.coalesce(3, shuffle = true)
    newRDD1.saveAsTextFile("output1")

    //11)repartition底层就是调用的coalesce，shuffle为true
    val rdd12: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 2)
    val newRDD2: RDD[Int] = rdd12.repartition(3)
    newRDD2.saveAsTextFile("output2")

    //12)sortBy 排序
    //默认为升序排列。排序后新产生的 RDD 的分区数与原 RDD 的分区数一
    //致。中间存在 shuffle 的过程
    val rdd13: RDD[Int] = sc.makeRDD(List(
      1,2,3,4,1,2
    ),2)
    val dataRDD3: RDD[Int] = rdd13.sortBy(num=>num, false, 4)
    dataRDD3.collect().foreach(println)

    //TODO 关闭环境
    sc.stop()
  }

}
