package com.framework.demo.repository.user;

import com.framework.demo.domain.ServiceCategory;
import com.framework.demo.domain.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserServiceRepository extends JpaRepository<UserService, Long> {
    List<UserService> findByUid(String uid);

    boolean existsByUidAndServiceId(String uid, String serviceId);
}
