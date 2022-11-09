package com.framework.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;

@Builder
@Data
@Entity(name = "BCF_USER")
@Table(name = "BCF_USER")
@NoArgsConstructor
@AllArgsConstructor
public class BcfUser { // UserDetails는 Spring Sequrity가 고나리하는 객체이다.

    @javax.persistence.Id
    @javax.persistence.Column(name ="UID", length = 20, nullable = false)
    @Id
    @Column("UID")
    private String uid;

    @javax.persistence.Column(name = "USER_EMAIL", nullable = false, length = 100, unique = true)
    @Column("USER_EMAIL")
    private String userEmail;

    @javax.persistence.Column(name = "PASSWORD", nullable = false, length = 100, unique = true)
    @Column("PASSWORD")
    private String password;

    @javax.persistence.Column(name = "NAME", length = 15)
    @Column("NAME")
    private String name;

    @javax.persistence.Column(name = "PHONE", length = 20, nullable = false, unique = true)
    @Column("PHONE")
    private String phone;

    @javax.persistence.Column(name = "TYPE", length = 20, nullable = false)
    @Column("TYPE")
    private String type;

    @javax.persistence.Column(name = "ROLE", length = 20, nullable = false)
    @Column("ROLE")
    private String role;

    @javax.persistence.Column(name = "SUB_ROLE", nullable = false, columnDefinition = "varchar(20) default 'general'")
    @Column("SUB_ROLE")
    private String subRole;

    @javax.persistence.Column(name = "PASSWORD_FAIL_CNT", length = 22)
    @Column("PASSWORD_FAIL_CNT")
    @ColumnDefault("0")
    private int passwordFailCnt;

    @javax.persistence.Column(name = "LOCK_YN", length = 4, columnDefinition = "varchar(1) default 'N'")
    @Column("LOCK_YN")
    private String lockYn;

    @javax.persistence.Column(name = "CREATE_DT", length = 40, nullable = false)
    @Column("CREATE_DT")
    private String createDt;

    @javax.persistence.Column(name = "UPDATE_DT", length = 40, nullable = true)
    @Column("UPDATE_DT")
    private String updateDt;


//    @javax.persistence.Column(name = "enabled", length = 20, nullable = false)
//    @Column("enabled")
//    private boolean enabled;
//
//    @javax.persistence.Column(name = "authorities")
//    @Column("authorities")
//    private String authorities;


}
