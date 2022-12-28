package com.framework.demo.repository.util;

import com.framework.demo.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    boolean existsByIdAndUploader(Long fileId, String uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE FILE F SET F.status = :status, F.update_dt= :updateDt WHERE F.id = :id", nativeQuery = true)
    void modifyStatus(Long id, String status, String updateDt);


}
