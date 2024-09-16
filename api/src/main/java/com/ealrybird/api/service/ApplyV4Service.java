package com.ealrybird.api.service;

import com.ealrybird.api.producer.CouponCreateProducer;
import com.ealrybird.api.repository.AppliedUserRepository;
import com.ealrybird.api.repository.CouponCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyV4Service {

    private final CouponCountRepository couponCountRepository;

    private final CouponCreateProducer couponCreateProducer;

    private final AppliedUserRepository appliedUserRepository;


    public void apply(Long userId) {

        Long saved = appliedUserRepository.save(userId);

        if(saved != 1){
            return;
        }

        long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        couponCreateProducer.create(Long.toString(userId));
    }
}
