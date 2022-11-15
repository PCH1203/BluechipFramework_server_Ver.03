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
@Entity(name = "login")
@Table(name = "login")
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @javax.persistence.Id
    @javax.persistence.Column(name ="uid", length = 20, nullable = false)
    @Id
    @Column("uid")
    private String uid;

    @javax.persistence.Column(name = "is_login", length = 2,  columnDefinition = "varchar(2) default 'Y'")
    @Column("is_login")
    private String isLogin;

    @javax.persistence.Column(name = "create_dt", length = 40, nullable = true)
    @Column("create_dt")
    private String createDt;

    @javax.persistence.Column(name = "update_dt", length = 40, nullable = true)
    @Column("update_dt")
    private String updateDt;

}
