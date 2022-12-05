package com.framework.demo.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;

@Builder
@Data
@Entity(name = "service")
@Table(name = "service")
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    @javax.persistence.Id
    @javax.persistence.Column(name ="service_id", nullable = false)
    @Id
    @Column("service_id")
    @Schema(description = "서비스 아이디")
    private String serviceId;

    @javax.persistence.Column(name = "service_name", nullable = false, unique = true)
    @Column("service_name")
    @Schema(description = "서비스 명")
    private String serviceName;

    @javax.persistence.Column(name = "version")
    @Column("version")
    private String version;

    @javax.persistence.Column(name = "is_open")
    @Column("is_open")
    private String isOpen;

}
