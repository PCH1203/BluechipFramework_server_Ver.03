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
@Entity(name = "BCF_FILE")
@Table(name = "BCF_FILE")
@NoArgsConstructor
@AllArgsConstructor // 자동 빌더 생성
public class BcfFile {

    @javax.persistence.Id
    @javax.persistence.Column(name ="ID", length = 20, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column("ID")
    private Long id;

    @javax.persistence.Column(name = "UPLOADER", nullable = false, length = 100)
    @Column("UPLOADER")
    private String uploader;

    @javax.persistence.Column(name = "ORIGIN_NAME", nullable = false, length = 100)
    @Column("ORIGIN_NAME")
    private String originName;

    @javax.persistence.Column(name = "SAVED_NAME", nullable = false, length = 100)
    @Column("SAVED_NAME")
    private String savedName;

    @javax.persistence.Column(name = "SAVED_PATH")
    @Column("SAVED_PATH")
    private String savedPath;

    @javax.persistence.Column(name = "CREATE_DT")
    @Column("CREATE_DT")
    private String createDt;

    @javax.persistence.Column(name = "UPDATE_DT")
    @Column("UPDATE_DT")
    private String updateDt;

//    @Builder
//    public BcfFile(BcfUser uid, String originName, String savedName, String savedPath, String createDt) {
//        this.uploader = uid;
//        this.originName = originName;
//        this.savedName = savedName;
//        this.savedPath = savedPath;
//        this.createDt = createDt;
//    }

}
