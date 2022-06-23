package com.self.cloudserver.kafka.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.cloudserver.dto.KafkaCommonReq;
import com.self.cloudserver.kafka.constants.TopicConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
    public void listen(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) throws JsonProcessingException {
        ack.acknowledge();

        KafkaCommonReq req = objectMapper.readValue(consumerRecord.value(), KafkaCommonReq.class);
        System.out.println(req.toString());
    }

}
