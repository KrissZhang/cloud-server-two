package com.self.cloudserver.kafka.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.cloudserver.dto.KafkaCommonReq;
import com.self.cloudserver.kafka.constants.TopicConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class RecordKafkaReceiver {

    @Autowired
    private ObjectMapper objectMapper;

    private static Logger logger = LoggerFactory.getLogger(RecordKafkaReceiver.class);

    @KafkaListener(topics = {TopicConstant.TEST_RECORD})
    public void listen(String rel, Acknowledgment ack) throws JsonProcessingException {
        KafkaCommonReq req = objectMapper.readValue(rel, KafkaCommonReq.class);
        System.out.println(req.toString());
        ack.acknowledge();
    }

}
