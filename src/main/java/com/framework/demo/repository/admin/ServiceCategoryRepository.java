package com.framework.demo.repository.admin;

import com.framework.demo.domain.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, String> {
    boolean existsByServiceId(String serviceId);

}
