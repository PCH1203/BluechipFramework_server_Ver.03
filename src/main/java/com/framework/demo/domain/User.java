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
@Entity(name = "user")
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @javax.persistence.Id
    @javax.persistence.Column(name ="uid", length = 20, nullable = false)
    @Id
    @Column("uid")
    @Schema(description = "사용자 PK", example = "uJjDEPSdX3wyeX0P4lAU")
    private String uid;

    @javax.persistence.Column(name = "user_email", nullable = false, length = 100, unique = true)
    @Column("user_email")
    @Schema(description = "사용자 이메일(ID)", example = "chanho1203@naver.com")
    private String userEmail;

    @javax.persistence.Column(name = "password", nullable = false, length = 100, unique = true)
    @Column("password")
    private String password;

    @javax.persistence.Column(name = "name", length = 15)
    @Schema(description = "사용자 이름", example = "박찬호")
    @Column("name")
    private String name;

    @javax.persistence.Column(name = "phone", length = 20, nullable = false, unique = true)
    @Schema(description = "사용자 연락처", example = "01027902203")
    @Column("phone")
    private String phone;

    @javax.persistence.Column(name = "type", length = 20, nullable = false)
    @Schema(description = "회원 타입 (web, app)", example = "web")
    @Column("type")
    private String type;

    @javax.persistence.Column(name = "role", length = 20, nullable = false)
    @Schema(description = "role(admin, user)", example = "user")
    @Column("role")
    private String role;

    @javax.persistence.Column(name = "sub_role", nullable = false, columnDefinition = "varchar(20) default 'general'")
    @Schema(description = "subRole(general, master, manager)", example = "general")
    @Column("sub_role")
    private String subRole;

    @javax.persistence.Column(name = "password_fail_cnt", length = 22)
    @Column("password_fail_cnt")
    @ColumnDefault("0")
    private int passwordFailCnt;

    @javax.persistence.Column(name = "lock_yn", length = 4, columnDefinition = "varchar(1) default 'N'")
    @Schema(description = "lock_yn", example = "N")
    @Column("lock_yn")
    private String lockYn;

    @javax.persistence.Column(name = "create_dt", length = 40, nullable = false)
    @Column("create_dt")
    private String createDt;

    @javax.persistence.Column(name = "update_dt", length = 40, nullable = true)
    @Column("update_dt")
    private String updateDt;

}
