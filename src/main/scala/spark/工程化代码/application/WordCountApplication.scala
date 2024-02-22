package spark.工程化代码.application

import spark.工程化代码.common.TApplication
import spark.工程化代码.controller.WordCountController

object WordCountApplication extends App with TApplication{

    // 启动应用程序
    start(){
        val controller = new WordCountController()
        controller.dispatch()
    }

}
