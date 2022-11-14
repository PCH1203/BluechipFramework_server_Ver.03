package com.framework.demo.repository.auth;

import com.framework.demo.domain.BcfAuthorities;
import com.framework.demo.domain.BcfLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AuthRepository extends JpaRepository<BcfAuthorities, String> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BCF_AUTHORITIES a SET a.refreshToken= :refreshToken, a.updateDt= :loginDt WHERE a.uid = :uid")
    void modifyRefreshToken(String uid, String refreshToken, String loginDt);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BCF_AUTHORITIES a SET a.refreshToken= :refreshToken, a.updateDt= :loginDt WHERE a.uid = :uid")
    void updateRefreshToken(String uid, String refreshToken, String loginDt);







}
