package com.framework.demo.repository.agreements;

import com.framework.demo.domain.Agreements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementsRepository extends JpaRepository<Agreements, Integer> {



}
