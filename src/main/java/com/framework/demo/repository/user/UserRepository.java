package com.framework.demo.repository.user;

import com.framework.demo.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserEmail(String userEmail);
    User findByUid(String uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user SET name = :name WHERE uid= :uid", nativeQuery = true)
    void modifyName(String uid, String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user u SET u.password_fail_cnt = u.passwordFailCnt + 1 WHERE u.uid= :uid",nativeQuery = true)
    void addPasswordFailCnt(String uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user u SET u.password_fail_cnt = 0 WHERE u.uid= :uid", nativeQuery = true)
    void resetPasswordFailCnt(String uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user u SET u.lock_yn = 'Y' WHERE u.uid= :uid",nativeQuery = true)
    User modifyLockYnByUid(String uid);

}
