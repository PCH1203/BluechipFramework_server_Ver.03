package com.framework.demo.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass // BaseEntity를 상속한 엔티티들은 아래 필드들을 컬럼으로 인식한다.
//@EntityListeners(AuditingEntityListener.class) // 자동으로 값을 매핑 시킨다.
@NoArgsConstructor
@AllArgsConstructor
public abstract class UuidEntity extends RootEntity {

    @Id
    @Column("uuid")
    @javax.persistence.Column(name = "uuid", nullable = false)
    private String uuid = UUID.randomUUID().toString();

}
