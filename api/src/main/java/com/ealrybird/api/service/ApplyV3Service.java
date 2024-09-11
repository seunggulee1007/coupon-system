package com.ealrybird.api.service;

import com.ealrybird.api.domain.Coupon;
import com.ealrybird.api.producer.CouponCreateProducer;
import com.ealrybird.api.repository.CouponCountRepository;
import com.ealrybird.api.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyV3Service {

    private final CouponCountRepository couponCountRepository;

    private final CouponCreateProducer couponCreateProducer;

    public void apply(Long userId) {
        long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        couponCreateProducer.create(userId);
    }
}
