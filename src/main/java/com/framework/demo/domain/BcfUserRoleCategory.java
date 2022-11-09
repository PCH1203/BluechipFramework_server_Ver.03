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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Builder
@Data
@Entity(name = "BCF_USER_ROLE_CATEGORY")
@Table(name = "BCF_USER_ROLE_CATEGORY")
@NoArgsConstructor
@AllArgsConstructor
public class BcfUserRoleCategory {

    @javax.persistence.Id
    @javax.persistence.Column(name ="ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column("ID")
    private Long id;

    @javax.persistence.Column(name = "ROLE", nullable = false)
    @Column("ROLE")
    private String role;

    @javax.persistence.Column(name = "SUB_ROLE", nullable = false)
    @Column("SUB_ROLE")
    private String subRole;


}
