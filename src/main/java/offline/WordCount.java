package offline;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author pengzhong
 * @since 2024/2/5
 */
public class WordCount {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("wordcount");
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);
        JavaRDD<String> javaRDD = javaSparkContext.textFile("/Users/pengzhong/Downloads/工作簿01.csv");

        //todo 1.flatMap
        JavaRDD<String> flatMapRDD = javaRDD.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String line) throws Exception {
                String[] split = line.split(",");
                return Arrays.asList(split).iterator();
            }
        });

        //todo 2.map
        JavaPairRDD<String, Integer> mapToPairRDD = flatMapRDD.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });

        /**
         * todo 3.reduceByKey
         * key相同的做累加
         * 预聚合，和mr的combiner类似
         * 结果：
         *  (20,1)
         * (name,1)
         * (18,2)
         * (刘备,2)
         * (吕布,1)
         * (张飞,2)
         * (2,1)
         * (3,1)
         * (age,1)
         * (id,1)
         * (17,2)
         * (1,3)
         */
//        mapToPairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
//            @Override
//            public Integer call(Integer v1, Integer v2) throws Exception {
//                return v1 + v2;
//            }
//        }).foreach(new VoidFunction<Tuple2<String, Integer>>() {
//            @Override
//            public void call(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
//                System.out.println(stringIntegerTuple2);
//            }
//        });

        mapToPairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        })
                //相当于排序
                .mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                return stringIntegerTuple2.swap();
            }
        }).sortByKey(false).foreach(new VoidFunction<Tuple2<Integer, String>>() {
            @Override
            public void call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
                System.out.println(integerStringTuple2);
            }
        });

        //行动算子 count foreach take first collect
        System.out.println(javaRDD.count());
        System.out.println(javaRDD.take(3));
        System.out.println(javaRDD.first());
        List<String> collect = javaRDD.collect();
        System.out.println(collect);

        javaSparkContext.stop();
    }

}
