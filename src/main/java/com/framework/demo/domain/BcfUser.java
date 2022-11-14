package com.framework.demo.domain;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "사용자 고유키", example = "uJjDEPSdX3wyeX0P4lAU")
    private String uid;

    @javax.persistence.Column(name = "USER_EMAIL", nullable = false, length = 100, unique = true)
    @Column("USER_EMAIL")
    @Schema(description = "사용자 이메일(ID)", example = "chanho1203@naver.com")
    private String userEmail;

    @javax.persistence.Column(name = "PASSWORD", nullable = false, length = 100, unique = true)
    @Column("PASSWORD")
    private String password;

    @javax.persistence.Column(name = "NAME", length = 15)
    @Schema(description = "사용자 이름", example = "박찬호")
    @Column("NAME")
    private String name;

    @javax.persistence.Column(name = "PHONE", length = 20, nullable = false, unique = true)
    @Schema(description = "사용자 연락처", example = "01027902203")
    @Column("PHONE")
    private String phone;

    @javax.persistence.Column(name = "TYPE", length = 20, nullable = false)
    @Schema(description = "회원 타입 (web, app)", example = "web")
    @Column("TYPE")
    private String type;

    @javax.persistence.Column(name = "ROLE", length = 20, nullable = false)
    @Schema(description = "role(admin, user)", example = "user")
    @Column("ROLE")
    private String role;

    @javax.persistence.Column(name = "SUB_ROLE", nullable = false, columnDefinition = "varchar(20) default 'general'")
    @Schema(description = "subRole(general, master, manager)", example = "general")
    @Column("SUB_ROLE")
    private String subRole;

    @javax.persistence.Column(name = "PASSWORD_FAIL_CNT", length = 22)
    @Column("PASSWORD_FAIL_CNT")
    @ColumnDefault("0")
    private int passwordFailCnt;

    @javax.persistence.Column(name = "LOCK_YN", length = 4, columnDefinition = "varchar(1) default 'N'")
    @Schema(description = "lockYn", example = "N")
    @Column("LOCK_YN")
    private String lockYn;

    @javax.persistence.Column(name = "CREATE_DT", length = 40, nullable = false)
    @Column("CREATE_DT")
    private String createDt;

    @javax.persistence.Column(name = "UPDATE_DT", length = 40, nullable = true)
    @Column("UPDATE_DT")
    private String updateDt;




}
