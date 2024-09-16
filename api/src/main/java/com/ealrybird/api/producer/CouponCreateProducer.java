package com.ealrybird.api.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j @Component
@RequiredArgsConstructor
public class CouponCreateProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void create(String userId) {
        log.info("Coupon created for user: {}", userId);
        kafkaTemplate.send("coupon_create",  userId);
    }

}
