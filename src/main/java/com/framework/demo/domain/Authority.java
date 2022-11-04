package com.framework.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@Entity(name = "authorities")
@Table(name = "authorities")
@NoArgsConstructor
@AllArgsConstructor
public class Authority { // UserDetails는 Spring Sequrity가 고나리하는 객체이다.

    @javax.persistence.Id
    @javax.persistence.Column(name ="uid", length = 20, nullable = false)
    @Id
    @Column("uid")
    private String uid;

    @javax.persistence.Column(name = "username", nullable = false, length = 100, unique = true)
    @Column("username")
    private String username;

    @javax.persistence.Column(name = "password", nullable = false, length = 100, unique = true)
    @Column("password")
    private String password;


    @javax.persistence.Column(name = "USER_NICKNAME", length = 15)
    @Column("USER_NICKNAME")
    private String userNickname;

    @javax.persistence.Column(name = "PHONE", length = 20, nullable = false)
    @Column("PHONE")
    private String phone;

    @javax.persistence.Column(name = "enabled", length = 20, nullable = false)
    @Column("enabled")
    private boolean enabled;

    @javax.persistence.Column(name = "authority")
    @Column("authority")
    private String authority;






}
