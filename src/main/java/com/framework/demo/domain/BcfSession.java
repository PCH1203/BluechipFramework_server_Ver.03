package com.framework.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;

@Builder
@Data
@Entity(name = "BCF_SESSION")
@Table(name = "BCF_SESSION")
@NoArgsConstructor
@AllArgsConstructor
public class BcfSession {
    @javax.persistence.Id
    @javax.persistence.Column(name ="UID", length = 20, nullable = false)
    @Id
    @Column("UID")
    private String uid;

    @javax.persistence.Column(name = "access_ip", nullable = false, length = 100, unique = true)
    @Column("access_ip")
    private String accessIp;

    @javax.persistence.Column(name = "session_id", nullable = false, length = 100, unique = true)
    @Column("session_id")
    private String sessionId;

    @javax.persistence.Column(name = "status", length = 15)
    @Column("status")
    private String status;

    @javax.persistence.Column(name = "create_dt", length = 40, nullable = true)
    @Column("create_dt")
    private String createDt;

    @javax.persistence.Column(name = "update_dt", length = 40, nullable = true)
    @Column("update_dt")
    private String updateDt;


}
