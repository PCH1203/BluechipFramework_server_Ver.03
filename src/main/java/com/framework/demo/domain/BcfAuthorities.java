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
@Entity(name = "BCF_AUTHORITIES")
@Table(name = "BCF_AUTHORITIES")
@NoArgsConstructor
@AllArgsConstructor
public class BcfAuthorities {

    @javax.persistence.Id
    @javax.persistence.Column(name ="UID", length = 20, nullable = false)
    @Id
    @Column("UID")
    private String uid;

    @javax.persistence.Column(name = "REFRESH_TOKEN", nullable = true)
    @Column("REFRESH_TOKEN")
    private String refreshtoken;

    @javax.persistence.Column(name = "CREATE_DT", length = 40, nullable = false)
    @Column("CREATE_DT")
    private String createDt;

    @javax.persistence.Column(name = "UPDATE_DT", length = 40, nullable = true)
    @Column("UPDATE_DT")
    private String updateDt;




}
