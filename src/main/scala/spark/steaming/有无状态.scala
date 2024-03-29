package spark.steaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object 有无状态 {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")
    val ssc = new StreamingContext(sparkConf, Seconds(3))
    ssc.checkpoint("cp")

    // 无状态数据操作，只对当前的采集周期内的数据进行处理
    // 在某些场合下，需要保留数据统计结果（状态），实现数据的汇总
    // 使用有状态操作时，需要设定检查点路径
    val datas: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)

    val wordToOne: DStream[(String, Int)] = datas.map((_,1))

    //val wordToCount = wordToOne.reduceByKey(_+_)

    // updateStateByKey：根据key对数据的状态进行更新
    // 传递的参数中含有两个值
    // 第一个值表示相同的key的value数据
    // 第二个值表示缓存区相同key的value数据
    val state: DStream[(String, Int)] = wordToOne.updateStateByKey(
      ( seq:Seq[Int], buff:Option[Int] ) => {
        val newCount: Int = buff.getOrElse(0) + seq.sum
        Option(newCount)
      }
    )

    //之前的累加在一起更新到检查点中
    state.print()

    ssc.start()
    ssc.awaitTermination()
  }

}
