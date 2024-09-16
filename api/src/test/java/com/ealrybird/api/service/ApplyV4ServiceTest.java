package com.ealrybird.api.service;

import com.ealrybird.api.repository.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplyV4ServiceTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ApplyV4Service applyV4Service;

    @Test
    @DisplayName("한명의 유저가 하나의 쿠폰만 발급- 성공")
    void multi_apply_one_user_one_coupon () throws InterruptedException {
        // given
        int treadCount = 1000;
        long userId = 1L;
        try(ExecutorService service = Executors.newFixedThreadPool(32)) {
            CountDownLatch latch = new CountDownLatch(treadCount);
            // when

            for (int i = 0; i < treadCount; i++) {
                service.submit(() -> {
                    try{
                        applyV4Service.apply(userId);
                    }finally {
                        latch.countDown();
                    }
                });

            }

            latch.await();
        }

        // then
        long count = couponRepository.countByUserId(userId);
        assertThat(count).isEqualTo(1);
    }

}