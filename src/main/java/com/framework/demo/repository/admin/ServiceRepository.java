package com.framework.demo.repository.admin;

import com.framework.demo.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {
    boolean existsByServiceId(String serviceId);




}
