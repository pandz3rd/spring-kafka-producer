package com.pandz.spring_kafka_producer.controller;

import com.pandz.spring_kafka_producer.model.internal.ApiBaseResponse;
import com.pandz.spring_kafka_producer.model.internal.CustomerInfo;
import com.pandz.spring_kafka_producer.service.usecase.ProducerUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publish")
@RequiredArgsConstructor
public class KafkaProducerController {
    private final ProducerUsecase producerUsecase;

    @GetMapping("message")
    public ResponseEntity<ApiBaseResponse<String>> publishMessage(@RequestParam String message) {
        return producerUsecase.publishMessage(message);
    }

    @GetMapping("message/bulk")
    public ResponseEntity<ApiBaseResponse<String>> publishBulkMessage(@RequestParam String message) {
        return producerUsecase.publishBulkMessage(message);
    }

    @PostMapping("message")
    public ResponseEntity<ApiBaseResponse<CustomerInfo>> publishCustomerMessage(@RequestBody CustomerInfo customer) {
        return producerUsecase.publishMessageCustomer(customer);
    }
}
