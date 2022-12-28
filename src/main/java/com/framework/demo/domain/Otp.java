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
@Entity(name = "otp")
@Table(name = "otp")
@NoArgsConstructor
@AllArgsConstructor
public class Otp extends BaseEntity {

//    @javax.persistence.Id
//    @javax.persistence.Column(name ="id", length = 20, nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column("id")
//    private Long id;

    @javax.persistence.Column(name = "type")
    @Column("type")
    private String type;

    @javax.persistence.Column(name = "otp_password", nullable = false, length = 100)
    @Column("otp_password")
    private String otpPassword;

    @javax.persistence.Column(name = "creator", nullable = true, length = 100)
    @Column("creator")
    private String creator;

    @javax.persistence.Column(name = "send_to")
    @Column("send_to")
    private String sendTo;

    @javax.persistence.Column(name = "expire_dt")
    @Column("expire_dt")
    private String expireDt;

}
