package com.framework.demo.domain;

import com.framework.demo.domain.common.RuntimeEntity;
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
@Entity(name = "authorities")
@Table(name = "authorities")
@NoArgsConstructor
@AllArgsConstructor
public class Authorities extends RuntimeEntity {

    @javax.persistence.Id
    @javax.persistence.Column(name ="uid", length = 20, nullable = false)
    @Id
    @Column("uid")
    private String uid;

    @javax.persistence.Column(name = "refresh_token")
    @Column("refresh_token")
    private String refreshToken;

//    @javax.persistence.Column(name = "create_dt", length = 40, nullable = false)
//    @Column("create_dt")
//    private String createDt;
//
//    @javax.persistence.Column(name = "update_dt", length = 40, nullable = true)
//    @Column("update_dt")
//    private String updateDt;




}
