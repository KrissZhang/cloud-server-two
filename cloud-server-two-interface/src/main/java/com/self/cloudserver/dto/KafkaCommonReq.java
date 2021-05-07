package com.self.cloudserver.dto;

public class KafkaCommonReq {

    private String key;

    public KafkaCommonReq() {
        super();
    }

    public KafkaCommonReq(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "KafkaCommonReq{" +
                "key='" + key + '\'' +
                '}';
    }

}
