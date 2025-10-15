package com.pandz.spring_kafka_producer.model.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfo implements Serializable {
    private static final long serialVersionUID = 130450963346535667L;
    private String name;
    private String email;
}
