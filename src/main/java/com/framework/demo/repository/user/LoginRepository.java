package com.framework.demo.repository.user;

import com.framework.demo.domain.Login;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {

    Login findByUid(String uid);
    Login findByUidAndServiceId(String uid, String serviceId);

    // 12.23 service_id 추가
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE login l SET l.is_login = :isLogin, l.update_dt= :updateDt WHERE l.uid = :uid", nativeQuery = true)
//    void modifyIsLogin(String isLogin, String updateDt, String uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE login l SET l.status = :status, l.update_dt= :updateDt WHERE l.uid = :uid", nativeQuery = true)
    void modifyIsLogin(String status, String updateDt, String uid);






}
