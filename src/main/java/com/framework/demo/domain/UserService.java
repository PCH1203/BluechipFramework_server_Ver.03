package com.framework.demo.domain;

import com.framework.demo.domain.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;

@Builder
@Data
@Entity(name = "user_service")
@Table(name = "user_service")
@NoArgsConstructor
@AllArgsConstructor
public class UserService extends BaseEntity {

//    @javax.persistence.Id
//    @javax.persistence.Column(name ="id", length = 20, nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column("id")
//    private Long id;

    @javax.persistence.Column(name ="uid", length = 20, nullable = false)
    @Column("uid")
    @Schema(description = "사용자 PK")
    private String uid;

    @javax.persistence.Column(name = "service_id", nullable = false, length = 100)
    @Column("service_id")
    @Schema(description = "서비스 아이디")
    private String serviceId;

}
