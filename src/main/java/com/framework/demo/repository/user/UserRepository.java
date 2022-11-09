package com.framework.demo.repository.user;

import com.framework.demo.domain.BcfUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<BcfUser, String> {

    BcfUser findByUserEmail(String userEmail);

}
