package com.framework.demo.domain;

import com.framework.demo.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;

@Builder
@Data
@Entity(name = "login")
@Table(name = "login")
@NoArgsConstructor
@AllArgsConstructor
public class Login extends BaseEntity {

//    @javax.persistence.Id
//    @javax.persistence.Column(name ="uid", length = 20, nullable = false)
//    @Id
//    @Column("uid")
//    private String uid;

    @javax.persistence.Column(name = "uid")
    @Column("uid")
    private String uid;

    @javax.persistence.Column(name = "service_id")
    @Column("service_id")
    private String serviceId;

    @javax.persistence.Column(name = "status", length = 2,  columnDefinition = "varchar(2) default 'Y'")
    @Column("status")
    private String status;



}
