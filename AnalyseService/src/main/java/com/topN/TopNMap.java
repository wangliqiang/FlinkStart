package com.topN;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/14 9:37
 */
public class TopNMap implements FlatMapFunction<String, Tuple2<String,Integer>> {
    @Override
    public void flatMap(String value, Collector<Tuple2<String, Integer>> collector) throws Exception {
        //（书1,1） (书2，1) （书3,1）
        collector.collect(new Tuple2<String, Integer>(value,1));
    }
}
