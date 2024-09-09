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
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("한번만 응모")
    void  once_apply () {
        // given
        applyService.apply(1L);

        // when
        long count = couponRepository.count();

        // then
        assertThat(count).isEqualTo(1);

    }

    @Test
    @DisplayName("여러번 요청 - 동시성 실패")
    void multi_apply_fail () throws InterruptedException {
        // given
        int treadCount = 1000;

        ExecutorService service = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(treadCount);
        // when

        for (int i = 0; i < treadCount; i++) {
             long userId = i;
            service.submit(() -> {
                try{
                    applyService.apply(userId);
                }finally {
                    latch.countDown();
                }
            });

        }

        latch.await();

        // then
        long count = couponRepository.count();
        assertThat(count).isNotEqualTo(treadCount);
    }
}