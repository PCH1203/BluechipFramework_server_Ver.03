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
@Entity(name = "service_category")
@Table(name = "service_category")
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCategory extends BaseEntity {

//    @javax.persistence.Id
//    @javax.persistence.Column(name ="service_id", nullable = false)
//    @Id
//    @Column("service_id")
//    @Schema(description = "서비스 아이디")
//    private String serviceId;

    @javax.persistence.Column(name = "service_id", nullable = false, unique = true)
    @Column("service_id")
    @Schema(description = "서비스 아이디")
    private String serviceId;

    @javax.persistence.Column(name = "service_name", nullable = false, unique = true)
    @Column("service_name")
    @Schema(description = "서비스 명")
    private String serviceName;

    @javax.persistence.Column(name = "service_url")
    @Column("service_url")
    private String serviceUrl;

    @javax.persistence.Column(name = "server_port")
    @Column("server_port")
    private String serverPort;

    @javax.persistence.Column(name = "api_docs_path")
    @Column("api_docs_path")
    private String apiDocsPath;

    @javax.persistence.Column(name = "version")
    @Column("version")
    private String version;

    @javax.persistence.Column(name = "is_open",  columnDefinition = "varchar(2) default 'N'")
    @Column("is_open")
    private String isOpen;

}
