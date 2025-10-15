package com.pandz.spring_kafka_producer.model.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaBaseMessage<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = -9090630791740092906L;
    private String requestId;
    private T data;
}
