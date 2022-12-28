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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
@Entity(name = "user_role")
@Table(name = "user_role")
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @javax.persistence.Id
    @javax.persistence.Column(name ="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column("id")
    private Integer id;

    @javax.persistence.Column(name = "role", nullable = false)
    @Column("role")
    private String role;

    @javax.persistence.Column(name = "create_dt")
    @Column("create_dt")
    private String createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

    @javax.persistence.Column(name = "update_dt")
    @Column("update_dt")
    private String updateDt;

}
