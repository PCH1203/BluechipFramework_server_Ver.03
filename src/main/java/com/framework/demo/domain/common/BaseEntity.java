package com.framework.demo.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@MappedSuperclass // BaseEntity를 상속한 엔티티들은 아래 필드들을 컬럼으로 인식한다.
//@EntityListeners(AuditingEntityListener.class) // 자동으로 값을 매핑 시킨다.
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity extends UuidEntity {

    @javax.persistence.Column(name = "create_dt")
    @Column("create_dt")
    private String createDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

    @javax.persistence.Column(name = "update_dt")
    @Column("update_dt")
    private String updateDt;


}
