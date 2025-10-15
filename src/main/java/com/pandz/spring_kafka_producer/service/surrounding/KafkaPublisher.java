package com.pandz.spring_kafka_producer.service.surrounding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandz.spring_kafka_producer.model.internal.CustomerInfo;
import com.pandz.spring_kafka_producer.model.internal.KafkaBaseMessage;
import com.pandz.spring_kafka_producer.service.helper.KafkaHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.pandz.spring_kafka_producer.constant.GeneralConstant.TOPIC_GENERAL;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaHelper kafkaHelper;

    public void sendMessage(String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC_GENERAL, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                kafkaHelper.onSuccess(result, message);
            } else {
                kafkaHelper.onFailure(ex, message);
            }
        });
    }

    public void sendMessageToTopic(String topic, String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                kafkaHelper.onSuccess(result, message);
            } else {
                kafkaHelper.onFailure(ex, message);
            }
        });
    }
}
