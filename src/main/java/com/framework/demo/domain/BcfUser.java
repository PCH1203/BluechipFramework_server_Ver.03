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

    @javax.persistence.Column(name = "USER_NICKNAME", length = 15)
    @Column("USER_NICKNAME")
    private String userNickname;

    @javax.persistence.Column(name = "PHONE", length = 20, nullable = false)
    @Column("PHONE")
    private String phone;

    @javax.persistence.Column(name = "ROLE", length = 20, nullable = false)
    @Column("ROLE")
    private String role;

    @javax.persistence.Column(name = "REFRESH_TOKEN", nullable = true)
    @Column("REFRESH_TOKEN")
    private String refreshtoken;

    @javax.persistence.Column(name = "CREATE_DT", length = 40, nullable = true)
    @Column("CREATE_DT")
    private String createDt;

//    @javax.persistence.Column(name = "enabled", length = 20, nullable = false)
//    @Column("enabled")
//    private boolean enabled;
//
//    @javax.persistence.Column(name = "authorities")
//    @Column("authorities")
//    private String authorities;


}
