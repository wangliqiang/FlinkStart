package com.kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import java.util.Properties;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/14 9:29
 */
public class KafkaProducer {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        DataStreamSource<String> dataStreamSource = env.addSource(new MyNoParalleSource());
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "flink01:9092");
        props.setProperty("zookeeper.connect", "flink01:2821");

        FlinkKafkaProducer<String> producer = new FlinkKafkaProducer<String>("topn",new SimpleStringSchema(),props);

        dataStreamSource.addSink(producer);

        env.execute("KafkaProducer Task!");
    }
}
