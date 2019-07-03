package com.socket;

import java.util.StringJoiner;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/25 16:36
 */
public class MsgBean {
    private String from;
    private String to;
    private String content;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MsgBean.class.getSimpleName() + "[", "]")
                .add("from='" + from + "'")
                .add("to='" + to + "'")
                .add("content='" + content + "'")
                .toString();
    }
}
