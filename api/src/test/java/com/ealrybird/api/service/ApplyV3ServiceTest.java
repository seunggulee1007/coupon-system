package com.ealrybird.api.service;

import com.ealrybird.api.repository.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplyV3ServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ApplyV3ServiceTest.class);
    @Autowired
    private ApplyV3Service applyV3Service;

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
                    log.error("호출!!!!");
                    applyV3Service.apply(userId);
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