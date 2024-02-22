package spark.文件读取和保存

import org.apache.spark.{SparkConf, SparkContext}

object 文件读取和保存 {

  def main(args: Array[String]): Unit = {
    val sparConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparConf)

    val rdd = sc.makeRDD(
      List(
        ("a", 1),
        ("b", 2),
        ("c", 3)
      )
    )

    //读取
//    val rdd = sc.textFile("output1")
//    println(rdd.collect().mkString(","))
//
//    val rdd1 = sc.objectFile[(String, Int)]("output2")
//    println(rdd1.collect().mkString(","))
//
//    val rdd2 = sc.sequenceFile[String, Int]("output3")
//    println(rdd2.collect().mkString(","))

    //保存
    rdd.saveAsTextFile("output1")
    rdd.saveAsObjectFile("output2")
    rdd.saveAsSequenceFile("output3")

    sc.stop()
  }

}
