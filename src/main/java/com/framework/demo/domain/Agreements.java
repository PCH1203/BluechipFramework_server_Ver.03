package com.framework.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
@Entity(name = "agreemetns")
@Table(name = "agreemetns")
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Agreements {

    @JsonIgnore
    @javax.persistence.Id
    @javax.persistence.Column(name ="id", length = 20, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column("id")
    private Long id;

    @javax.persistence.Column(name = "title")
    @Column("title")
    private String title;

    @javax.persistence.Column(name = "contents", columnDefinition = "LONGTEXT")
    @Column("contents")
    private String contents;

    @JsonIgnore
    @javax.persistence.Column(name = "create_dt")
    @Column("create_dt")
    private String createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

}
