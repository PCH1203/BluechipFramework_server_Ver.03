package com.framework.demo.repository.util;

import com.framework.demo.domain.Authorities;
import com.framework.demo.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    @Query(value = "SELECT o.expire_dt FROM otp o WHERE o.creator= :uid ORDER BY o.id DESC LIMIT 1",nativeQuery = true)
    String findByCreator(String uid);








}
