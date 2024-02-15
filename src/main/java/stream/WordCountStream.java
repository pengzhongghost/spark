package stream;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author pengzhong
 * @since 2024/2/4
 */
public class WordCountStream {

    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf();
        //local[2],后面跟个2开两个线程
        conf.setMaster("local[2]").setAppName("sparkStream");
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);
        javaSparkContext.sc().setLogLevel("WARN");
        //通过环境以及batchInterval创建流环境，5秒执行一次
        JavaStreamingContext streamingContext = new JavaStreamingContext(javaSparkContext, Durations.seconds(5));
        //接收websocket发送过来的消息
        JavaReceiverInputDStream<String> dStream = streamingContext.socketTextStream("127.0.0.1", 9999);
        //转换算子要有output算子才会执行
        dStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        }).mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        }).print();

        /**
         * todo 1:foreachRDD
         * 操作对象是dStream中的RDD对象，所以可以继续做rdd操作
         */
        dStream.foreachRDD(new VoidFunction<JavaRDD<String>>() {
            @Override
            public void call(JavaRDD<String> stringJavaRDD) throws Exception {
                //循环处理
                stringJavaRDD.foreach(new VoidFunction<String>() {
                    @Override
                    public void call(String s) throws Exception {
                        System.out.println(s);
                    }
                });
                //过滤 不会触发
                stringJavaRDD.filter(new Function<String, Boolean>() {
                    @Override
                    public Boolean call(String s) throws Exception {
                        System.out.println("filter：" + s);
                        return true;
                    }
                })
                        //但是如果加个foreach此时filter就会执行
                        .foreach(new VoidFunction<String>() {
                    @Override
                    public void call(String s) throws Exception {
                        System.out.println("foreach：" + s);
                    }
                });
            }
        });

        /**
         * todo 2.transform
         * 也是操作的RDD对象
         */
        JavaPairDStream<String, String> ds1 = dStream.mapToPair(new PairFunction<String, String, String>() {
            @Override
            public Tuple2<String, String> call(String s) throws Exception {
                System.out.println("mapToPair：" + s);
                return new Tuple2<>(s.split(" ")[1], s);
            }
        });
        JavaDStream<String> ds2 = ds1.transform(new Function<JavaPairRDD<String, String>, JavaRDD<String>>() {
            @Override
            public JavaRDD<String> call(JavaPairRDD<String, String> rdd1) throws Exception {
                System.out.println("这边是driver在干活");
                return rdd1.map(new Function<Tuple2<String, String>, String>() {
                    @Override
                    public String call(Tuple2<String, String> v1) throws Exception {
                        System.out.println("transform.map" + v1);
                        return v1._2;
                    }
                });
            }
        });



        ds2.print();

        //开启流
        streamingContext.start();
        //等待流结束
        streamingContext.awaitTermination();
        //关闭流环境
        streamingContext.stop();

    }

}
