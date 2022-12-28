package com.framework.demo.repository.admin;

import com.framework.demo.domain.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, String> {
    boolean existsByServiceId(String serviceId);

    ServiceCategory findByServiceId(String serviceId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE service_category sc SET sc.is_open= :isOpen WHERE sc.service_id= :serviceId",nativeQuery = true)
    void modifyIsOpenByServiceId(String serviceId, String isOpen);
}
