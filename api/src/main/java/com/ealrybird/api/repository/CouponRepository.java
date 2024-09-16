package com.ealrybird.api.repository;

import com.ealrybird.api.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    int countByUserId(Long userId);

}
