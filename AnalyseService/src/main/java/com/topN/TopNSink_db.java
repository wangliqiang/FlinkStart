package com.topN;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/20 10:16
 */
public class TopNSink_db extends RichSinkFunction<String> {

    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://172.17.20.8:3306/topn", "root", "123456");
        connection.setAutoCommit(false);
    }

    @Override
    public void invoke(String value, Context context) throws Exception {
        preparedStatement = connection.prepareStatement("insert into topn(info,date) values (?,?)");
        preparedStatement.setString(1, value);
        preparedStatement.setString(2, new Timestamp(System.currentTimeMillis()) + "");
        preparedStatement.execute();
        connection.commit();
    }

    @Override
    public void close() throws Exception {
        super.close();
        preparedStatement.close();
        connection.close();
    }

}
