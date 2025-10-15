package com.pandz.spring_kafka_producer.util;

import com.pandz.spring_kafka_producer.model.internal.ApiBaseResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.pandz.spring_kafka_producer.constant.GeneralConstant.*;

public class ResponseUtil {
    private ResponseUtil() {}

    private static final Map<String, HttpStatus> ERROR_CODE_HTTP_STATUSES = new HashMap<>();
    static {
        ERROR_CODE_HTTP_STATUSES.put(RC_CODE_SUCCESS, HttpStatus.OK);
        ERROR_CODE_HTTP_STATUSES.put(RC_CODE_DECLINE, HttpStatus.BAD_REQUEST);
        ERROR_CODE_HTTP_STATUSES.put(RC_CODE_SYSTEM_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T extends Serializable> ApiBaseResponse<T> buildResponse(String code, String message, T data) {
        ApiBaseResponse<T> result = new ApiBaseResponse<>();
        result.setResponseCode(code);
        result.setResponseMessage(message);
        result.setTraceId(MDC.get(TRACE_ID));
        result.setData(data);

        return result;
    }

    public static <T extends Serializable> ApiBaseResponse<T> buildResponse(String message, String code) {
        ApiBaseResponse<T> result = new ApiBaseResponse<>();
        result.setResponseMessage(message);
        result.setResponseCode(code);
        result.setTraceId(MDC.get(TRACE_ID));
        return result;
    }

    public static HttpStatus errorCodeToHttpStatus(String errorCode) {
        return Optional.ofNullable(ERROR_CODE_HTTP_STATUSES.get(errorCode)).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T extends Serializable> ResponseEntity<ApiBaseResponse<T>> buildHttpResponse(ApiBaseResponse<T> result) {
        return new ResponseEntity<>(
            result, errorCodeToHttpStatus(result.getResponseCode())
        );
    }
}
