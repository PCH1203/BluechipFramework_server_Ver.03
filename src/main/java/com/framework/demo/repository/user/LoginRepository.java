package com.framework.demo.repository.user;

import com.framework.demo.domain.BcfLogin;
import com.framework.demo.domain.BcfUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LoginRepository extends JpaRepository<BcfLogin, String> {

    BcfLogin findByUid(String uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BCF_LOGIN l SET l.isLogin = :isLogin, l.updateDt= :updateDt WHERE l.uid = :uid")
    void modifyIsLogin(String isLogin,String updateDt, String uid);






}
