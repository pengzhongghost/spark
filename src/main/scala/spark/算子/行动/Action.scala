package spark.算子.行动

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Action {

  def main(args: Array[String]): Unit = {
    //TODO 准备环境
    //[*]表示当前本机的核数是多少，最少2个才能执行
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)
    //TODO 执行业务操作
    val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    // TODO - 行动算子
    // 所谓的行动算子，其实就是触发作业(Job)执行的方法
    // 底层代码调用的是环境对象的runJob方法
    // 底层代码中会创建ActiveJob，并提交执行。
    //1)collect：在驱动程序中，以数组 Array 的形式返回数据集的所有元素
    println("collect")
    rdd1.collect().foreach(println)

    //2)reduce：聚集 RDD 中的所有元素，先聚合分区内数据，再聚合分区间数据
    println("reduce")
    val rdd2: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val res1: Int = rdd2.reduce(_ + _)
    println(res1)

    //3)count：返回 RDD 中元素的个数
    val rdd3: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    println(rdd3.count())


    //4)first：返回 RDD 中的第一个元素
    //5)take：返回一个由 RDD 的前 n 个元素组成的数组
    //6)takeOrdered：返回该 RDD 排序后的前 n 个元素组成的数组
    //7)aggregate：分区的数据通过初始值和分区内的数据进行聚合，然后再和初始值进行分区间的数据聚合
    // aggregateByKey : 初始值只会参与分区内计算
    // aggregate : 初始值会参与分区内计算,并且和参与分区间计算
    println("aggregate")
    val rdd4: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 8)
    // 将该 RDD 所有元素相加得到结果
    //val res2: Int = rdd4.aggregate(0)(_ + _, _ + _)
    //8个分区，加起来80，再加上1+2+3+4=10，再加上最后一个10(初始值会参与分区内计算,并且和参与分区间计算)
    val res3: Int = rdd4.aggregate(10)(_ + _, _ + _)
    println(res3)

    //8)fold：aggregate分区间和分区内规则一致的情况
    val rdd5: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    val foldResult: Int = rdd5.fold(0)(_+_)

    //9)countByKey：统计每种 key 的个数
    val rdd6: RDD[(Int, String)] = sc.makeRDD(List((1, "a"), (1, "a"), (1, "a"), (2,
      "b"), (3, "c"), (3, "c")))
    // 统计每种 key 的个数
    val result: collection.Map[Int, Long] = rdd6.countByKey()

    //10)foreach：分布式遍历 RDD 中的每一个元素，调用指定函数

    ///11)save：将数据保存到不同格式的文件中
    // 保存成 Text 文件
    rdd6.saveAsTextFile("output")
    // 序列化成对象保存到文件
    rdd6.saveAsObjectFile("output1")
    // 保存成 Sequencefile 文件
    //rdd6.map((_,1)).saveAsSequenceFile("output2")

    //TODO 关闭环境
    sc.stop()

  }

}
