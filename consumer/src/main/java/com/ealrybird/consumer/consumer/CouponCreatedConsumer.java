package com.ealrybird.consumer.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CouponCreatedConsumer {

    @KafkaListener(topics="coupon_create", groupId = "group_1")
    public void listen(String userId) {
        System.out.println("Coupon created for user: " + userId);
    }

}
