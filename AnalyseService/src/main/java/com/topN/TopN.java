package com.topN;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @Description 实时热销排行
 * @Author wangliqiang
 * @Date 2019/6/14 9:01
 */
public class TopN {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);// 以ProcessingTime作为时间语义

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "flink01:9092");
        props.setProperty("zookeeper.connect", "flink01:2821");

        FlinkKafkaConsumer<String> input = new FlinkKafkaConsumer<String>("topn", new SimpleStringSchema(), props);
        input.setStartFromEarliest();

        DataStream<String> dataStream = env.addSource(input);

        DataStream<Tuple2<String, Integer>> flatMap = dataStream.flatMap(new TopNMap());

        DataStream<Tuple2<String, Integer>> sum = flatMap.keyBy(0)
                .window(SlidingProcessingTimeWindows.of(Time.seconds(600), Time.seconds(5)))//key之后的元素进入一个总时间长度为600s,每5s向后滑动一次的滑动窗口
                .sum(1);// 将相同的key的元素第二个count值相加


        sum.windowAll(TumblingProcessingTimeWindows.of(Time.seconds(5)))//所有key元素进入一个5s长的窗口（选5秒是因为上游窗口每5s计算一轮数据，topN窗口一次计算只统计一个窗口时间内的变化）
                .process(new TopNAllFunction(5))
                .writeToSocket("127.0.0.1", 8000, new SerializationSchema<String>() {
                    @Override
                    public byte[] serialize(String element) {
                        return element.getBytes();
                    }
                });
//                .addSink(new TopNSink());

        env.execute("TopN Task!");
    }
}
