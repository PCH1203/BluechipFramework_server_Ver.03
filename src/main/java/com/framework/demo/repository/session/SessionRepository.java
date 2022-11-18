package com.framework.demo.repository.session;

import com.framework.demo.domain.Session;
import com.framework.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {



}
