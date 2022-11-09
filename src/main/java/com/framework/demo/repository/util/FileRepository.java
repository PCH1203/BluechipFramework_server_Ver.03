package com.framework.demo.repository.util;

import com.framework.demo.domain.BcfFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<BcfFile, Long> {

    boolean existsByIdAndUploader(Long fileId, String uid);

}
