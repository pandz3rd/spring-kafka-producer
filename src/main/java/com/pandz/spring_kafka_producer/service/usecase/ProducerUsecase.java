package com.pandz.spring_kafka_producer.service.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandz.spring_kafka_producer.exception.ProducerException;
import com.pandz.spring_kafka_producer.model.internal.ApiBaseResponse;
import com.pandz.spring_kafka_producer.model.internal.CustomerInfo;
import com.pandz.spring_kafka_producer.model.internal.KafkaBaseMessage;
import com.pandz.spring_kafka_producer.service.surrounding.KafkaPublisher;
import com.pandz.spring_kafka_producer.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

import static com.pandz.spring_kafka_producer.constant.GeneralConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerUsecase {
    private final KafkaPublisher publisher;

    public ResponseEntity<ApiBaseResponse<String>> publishMessage(String message) {
        log.info("Start publish message: " + message);
        String result = "Success";
        try {
            publisher.sendMessage(message);
        } catch (Exception e) {
            log.error("Error when get list asteroid: " + e.getMessage());
            throw new ProducerException(ERROR_MESSAGE_SYSTEM_ERROR, RC_CODE_SYSTEM_ERROR, ERROR_DESCRIPTION_SYSTEM_ERROR);
        }
        return ResponseUtil.buildHttpResponse(ResponseUtil.buildResponse(RC_CODE_SUCCESS, ERROR_MESSAGE_SUCCESS, result));
    }


    public ResponseEntity<ApiBaseResponse<String>> publishBulkMessage(String message) {
        log.info("Start publish bulk message");
        String result = "Success";
        try {
            IntStream.range(0, 1000).forEach(i -> publisher.sendMessage(message + " " + i));
        } catch (Exception e) {
            log.error("Error when get list asteroid: " + e.getMessage());
            throw new ProducerException(ERROR_MESSAGE_SYSTEM_ERROR, RC_CODE_SYSTEM_ERROR, ERROR_DESCRIPTION_SYSTEM_ERROR);
        }
        return ResponseUtil.buildHttpResponse(ResponseUtil.buildResponse(RC_CODE_SUCCESS, ERROR_MESSAGE_SUCCESS, result));
    }

    public ResponseEntity<ApiBaseResponse<CustomerInfo>> publishMessageCustomer(CustomerInfo customer) {
        log.info("Start publish message customer");
        try {
            var customerMessage = new KafkaBaseMessage<>();
            customerMessage.setData(customer);
            String customerString = (new ObjectMapper()).writeValueAsString(customerMessage);
            publisher.sendMessageToTopic(TOPIC_CUSTOMER, customerString);
        } catch (Exception e) {
            log.error("Error when get list asteroid: " + e.getMessage());
            throw new ProducerException(ERROR_MESSAGE_SYSTEM_ERROR, RC_CODE_SYSTEM_ERROR, ERROR_DESCRIPTION_SYSTEM_ERROR);
        }
        return ResponseUtil.buildHttpResponse(ResponseUtil.buildResponse(RC_CODE_SUCCESS, ERROR_MESSAGE_SUCCESS, customer));
    }
}
