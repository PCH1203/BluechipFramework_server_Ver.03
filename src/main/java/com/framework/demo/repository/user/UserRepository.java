package com.framework.demo.repository.user;

import com.framework.demo.domain.BcfUser;
import com.framework.demo.model.user.dto.ModifyMyAccountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<BcfUser, String> {

    BcfUser findByUserEmail(String userEmail);
    BcfUser findByUid(String uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BCF_USER b SET b.name = :name WHERE b.uid= :uid")
    int modifyName(String uid, String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BCF_USER b SET b.passwordFailCnt = b.passwordFailCnt + 1 WHERE b.uid= :uid")
    void addPasswordFailCnt(String uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BCF_USER b SET b.passwordFailCnt = 0 WHERE b.uid= :uid")
    void resetPasswordFailCnt(String uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BCF_USER b SET b.lockYn = 'Y' WHERE b.uid= :uid")
    void updateLockYnByUid(String uid);





}
