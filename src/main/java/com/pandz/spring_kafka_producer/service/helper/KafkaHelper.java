package com.pandz.spring_kafka_producer.service.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaHelper {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public  <T> void onSuccess(final SendResult<String, String> result, final T t) {
        log.info("Sent Message={} to topic-partition={}-{} with offset={}",
            t.toString(),
            result.getRecordMetadata().topic(),
            result.getRecordMetadata().partition(),
            result.getRecordMetadata().offset());
    }

    public  <T> void onFailure(final Throwable ex, final T t) {
        log.error("Unable to send message={} due to {}", t.toString(), ex.getMessage());
    }

    public void handleException(Throwable ex, String payload, final String targetTopic) {
        log.error("Unable to send message={} due to {}", payload, ex.getMessage());
        if (ex instanceof UnsupportedVersionException
                || ex instanceof RecordTooLargeException
                || ex instanceof CorruptRecordException) {
            log.error("Non-Retriable exception occurred. Sending message to dead-letter queue: [{}] Error: {}", payload, ex.getMessage());
            sendToDeadLetterTopic(payload, targetTopic);
        } else if (ex instanceof SerializationException) {
            log.error("Serialization error! message=[{}]", payload);
        } else if (ex instanceof RetriableException) {
            log.warn("Retriable exception occurred. Kafka will retry automatically: {}", ex.getMessage());
        }
    }

    private void sendToDeadLetterTopic(String payload, final String topic) {
        String deadLetterTopic = topic + ".DLT";
        log.info("Sending failed message to Dead Letter Topic: {}", deadLetterTopic);
        kafkaTemplate.send(deadLetterTopic, payload);
    }
}
