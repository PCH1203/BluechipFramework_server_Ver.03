package com.framework.demo.domain;

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
@Entity(name = "file")
@Table(name = "file")
@NoArgsConstructor
@AllArgsConstructor // 자동 빌더 생성
public class File {

    @javax.persistence.Id
    @javax.persistence.Column(name ="id", length = 20, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column("id")
    private Long id;

    @javax.persistence.Column(name = "uploader", nullable = false, length = 100)
    @Column("uploader")
    private String uploader;

    @javax.persistence.Column(name = "origin_name", nullable = false, length = 100)
    @Column("origin_name")
    private String originName;

    @javax.persistence.Column(name = "saved_name", nullable = false, length = 100)
    @Column("saved_name")
    private String savedName;

    @javax.persistence.Column(name = "saved_path")
    @Column("saved_path")
    private String savedPath;

    @javax.persistence.Column(name = "create_dt")
    @Column("create_dt")
    private String createDt;

    @javax.persistence.Column(name = "update_dt")
    @Column("update_dt")
    private String updateDt;

}
