package com.pandz.spring_kafka_producer.exception;

import com.pandz.spring_kafka_producer.model.internal.ApiBaseResponse;
import com.pandz.spring_kafka_producer.util.ResponseUtil;

import java.io.Serializable;

public class ProducerException extends RuntimeException {
    private final String messageCode;
    private final String errorCode;
    private final String errorMessage;

    public ProducerException(String message, String code, String errorMessage) {
        super(message);
        this.messageCode = message;
        this.errorCode = code;
        this.errorMessage = errorMessage;
    }

    public ApiBaseResponse<Serializable> apiResponseBase() {
        return ResponseUtil.buildResponse(this.messageCode, this.errorCode);
    }
}
