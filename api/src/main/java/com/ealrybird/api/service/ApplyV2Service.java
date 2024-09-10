package com.ealrybird.api.service;

import com.ealrybird.api.domain.Coupon;
import com.ealrybird.api.repository.CouponCountRepository;
import com.ealrybird.api.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyV2Service {

    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;

    public void apply(Long userId) {
        long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        couponRepository.save(new Coupon(userId));
    }
}
