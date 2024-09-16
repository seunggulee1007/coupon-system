package com.ealrybird.consumer.consumer;

import com.ealrybird.consumer.domain.Coupon;
import com.ealrybird.consumer.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCreatedConsumer {

    private final CouponRepository couponRepository;

    @KafkaListener(topics="coupon_create", groupId = "group_1")
    public void listen(String userId) {
        couponRepository.save(new Coupon(userId));
        System.out.println("Coupon created for user: " + userId);
    }

}
