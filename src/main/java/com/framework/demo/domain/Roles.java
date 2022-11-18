package com.framework.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Builder
@Data
@Entity(name = "role_category")
@Table(name = "role_category")
@NoArgsConstructor
@AllArgsConstructor
public class Roles {

    @javax.persistence.Id
    @javax.persistence.Column(name ="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column("id")
    private Integer id;

    @javax.persistence.Column(name = "role", nullable = false)
    @Column("role")
    private String role;

    @javax.persistence.Column(name = "sub_role", nullable = false)
    @Column("sub_role")
    private String subRole;

}
