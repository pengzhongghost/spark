package 集合

import scala.collection.mutable

object 集合计算高级函数 {

  def main(args: Array[String]): Unit = {
    val list: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val nestedList: List[List[Int]] = List(List(1, 2, 3), List(4,
      5, 6), List(7, 8, 9))
    val wordList: List[String] = List("hello world", "hello java", "hello scala")
    //（1）过滤
    println(list.filter(x => x % 2 == 0))
    //（2）转化/映射
    println(list.map(x => x + 1))
    //（3）扁平化
    println(nestedList.flatten)
    //（4）扁平化+映射 注：flatMap 相当于先进行 map 操作，在进行 flatten操作
    println(wordList.flatMap(x => x.split(" ")))
    //（5）分组
    println(list.groupBy(x => x % 2))

    /**
     * Reduce 简化（归约） ：通过指定的逻辑将集合中的
     * 数据进行聚合，从而减少数据，最终获取结果。
     */
    val list1 = List(1, 2, 3, 4)
    // 将数据两两结合，实现运算规则
    //1-2-3-4
    val i: Int = list1.reduce((x, y) => x - y)
    println("i = " + i)
    // 从源码的角度，reduce 底层调用的其实就是 reduceLeft
    //val i1 = list.reduceLeft((x,y) => x-y)
    //(1-(2-(3-4))) = -2
    val i2 = list1.reduceRight((x, y) => x - y)
    println(i2)

    /**
     * Fold 折叠：化简的一种特殊情况
     */
    val list2 = List(1, 2, 3, 4)
    // fold 方法使用了函数柯里化，存在两个参数列表
    // 第一个参数列表为 ： 零值（初始值）
    // 第二个参数列表为： 简化规则
    // fold 底层其实为 foldLeft
    println(list2.fold(10)((x, y) => x - y)) // 10+1+2+3+4
    println(list2.foldLeft(10)((x, y) => x - y)) // 10-1-2-3-4
    println(list2.foldRight(10)(_ - _)) // (1-(2-(3-(4-10))))

    /**
     * 两个集合合并
     */
    // 两个 Map 的数据合并
    val map1 = mutable.Map("a" -> 1, "b" -> 2, "c" -> 3)
    val map2 = mutable.Map("a" -> 4, "b" -> 5, "d" -> 6)
    val map3: mutable.Map[String, Int] = map2.foldLeft(map1) {
      (map, kv) => {
        val k = kv._1
        val v = kv._2
        map(k) = map.getOrElse(k, 0) + v
        map
      }
    }
    println(map3)

  }

}
