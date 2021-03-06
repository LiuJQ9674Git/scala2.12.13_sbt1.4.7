import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
object Test {

  def main(args: Array[String]): Unit ={
    print("test")
    // 创建流处理环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    // 接收socket文本流
    val textDstream: DataStream[String] =
      env.socketTextStream("localhost", 9000)
    // flatMap和Map需要引用隐式转换
    import org.apache.flink.api.scala._
    // 处理：分组并且sum聚合
    val dStream:DataStream[(String,Int)] = textDstream
      .flatMap(_.split(","))
      .filter(_.nonEmpty)
      .map((_,1))
      .keyBy(0)
      .sum(1)
    // 打印输出
    dStream.print()
    // 启动
    env.execute()
  }
}
