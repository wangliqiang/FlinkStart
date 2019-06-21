package com.topN;

import com.alibaba.fastjson.JSON;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.sql.Timestamp;
import java.util.*;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/14 9:44
 */
public class TopNAllFunction extends ProcessAllWindowFunction<Tuple2<String, Integer>, String, TimeWindow> {

    private int topSize;

    public TopNAllFunction(int topSize) {

        this.topSize = topSize;
    }

    public void process(ProcessAllWindowFunction<Tuple2<String, Integer>, String, TimeWindow>.Context arg0,
                        Iterable<Tuple2<String, Integer>> input,
                        Collector<String> out) throws Exception {

        TreeMap<Integer, Tuple2<String, Integer>> treemap = new TreeMap<>(
                new Comparator<Integer>() {

                    @Override
                    public int compare(Integer y, Integer x) {
                        return (x < y) ? -1 : 1;
                    }

                }); //treemap按照key降序排列，相同count值不覆盖

        for (Tuple2<String, Integer> element : input) {
            treemap.put(element.f1, element);
            if (treemap.size() > topSize) { //只保留前面TopN个元素
                treemap.pollLastEntry();
            }
        }

        List list = new ArrayList();
        for (Map.Entry<Integer, Tuple2<String, Integer>> entry : treemap
                .entrySet()) {
            list.add(entry.getValue());
        }
//        System.out.println("=================\n热销图书列表:\n" + new Timestamp(System.currentTimeMillis()) + treemap.toString() + "\n===============\n");
        out.collect(JSON.toJSONString(list));
    }

}
