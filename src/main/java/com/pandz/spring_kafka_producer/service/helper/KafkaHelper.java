package com.pandz.spring_kafka_producer.service.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaHelper {
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
}
