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
@Entity(name = "BCF_LOGIN")
@Table(name = "BCF_LOGIN")
@NoArgsConstructor
@AllArgsConstructor
public class BcfLogin {

    @javax.persistence.Id
    @javax.persistence.Column(name ="UID", length = 20, nullable = false)
    @Id
    @Column("UID")
    private String uid;

    @javax.persistence.Column(name = "IS_LOGIN", length = 2,  columnDefinition = "varchar(2) default 'Y'")
    @Column("IS_LOGIN")
    private String isLogin;

    @javax.persistence.Column(name = "CREATE_DT", length = 40, nullable = true)
    @Column("CREATE_DT")
    private String createDt;

    @javax.persistence.Column(name = "UPDATE_DT", length = 40, nullable = true)
    @Column("UPDATE_DT")
    private String updateDt;



}
