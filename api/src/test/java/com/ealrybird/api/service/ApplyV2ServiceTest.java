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
class ApplyV2ServiceTest {

    @Autowired
    private ApplyV2Service applyV2Service;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("동시성 테스트 - 성공")
    void multi_apply () throws InterruptedException {
        // given
        int treadCount = 1000;

        ExecutorService service = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(treadCount);
        // when

        for (int i = 0; i < treadCount; i++) {
            long userId = i;
            service.submit(() -> {
                try{
                    applyV2Service.apply(userId);
                }finally {
                    latch.countDown();
                }
            });

        }

        latch.await();

        // then
        long count = couponRepository.count();
        assertThat(count).isEqualTo(100);
    }
}