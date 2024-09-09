package com.ealrybird.api.service;

import com.ealrybird.api.repository.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}