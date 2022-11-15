package com.framework.demo.repository.auth;

import com.framework.demo.domain.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AuthRepository extends JpaRepository<Authorities, String> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE authorities a SET a.refresh_token= :refreshToken, a.update_dt= :loginDt WHERE a.uid = :uid",nativeQuery = true)
    void modifyRefreshToken(String uid, String refreshToken, String loginDt);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE authorities a SET a.refresh_token= :refreshToken, a.update_dt= :loginDt WHERE a.uid = :uid",nativeQuery = true)
    void updateRefreshToken(String uid, String refreshToken, String loginDt);







}
