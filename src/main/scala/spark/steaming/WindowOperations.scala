package spark.steaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WindowOperations {

  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    val lines: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)
    val wordToOne: DStream[(String, Int)] = lines.map((_, 1))


    /**
     * Window Operations 可以设置窗口的大小和滑动窗口的间隔来动态的获取当前 Steaming 的允许
     * 状态。所有基于窗口的操作都需要两个参数，分别为窗口时长以及滑动步长。
     * ➢ 窗口时长：计算内容的时间范围；
     * ➢ 滑动步长：隔多久触发一次计算。
     * 注意：这两者都必须为采集周期大小的整数倍。
     * WordCount 第三版：3 秒一个批次，窗口 12 秒，滑步 6 秒。
     */
    // 窗口的范围应该是采集周期的整数倍
    // 窗口可以滑动的，但是默认情况下，一个采集周期进行滑动
    // 这样的话，可能会出现重复数据的计算，为了避免这种情况，可以改变滑动的滑动（步长）
    //➢ 第一个参数窗口时长：计算内容的时间范围；
    // ➢ 第二个参数滑动步长：隔多久触发一次计算
    val windowDS: DStream[(String, Int)] = wordToOne.window(Seconds(6), Seconds(6))

    val wordToCount: DStream[(String, Int)] = windowDS.reduceByKey(_ + _)

    wordToCount.print()

    // reduceByKeyAndWindow : 当窗口范围比较大，但是滑动幅度比较小，那么可以采用增加数据和删除数据的方式
    // 无需重复计算，提升性能。
    val windowDS1: DStream[(String, Int)] =
    wordToOne.reduceByKeyAndWindow(
      //新增的窗口的数据加进来
      (x:Int, y:Int) => { x + y},
      //过去的窗口的数据减掉
      (x:Int, y:Int) => {x - y},
      Seconds(9), Seconds(3))

    windowDS1.print()


    ssc.start()
    ssc.awaitTermination()
  }

}
