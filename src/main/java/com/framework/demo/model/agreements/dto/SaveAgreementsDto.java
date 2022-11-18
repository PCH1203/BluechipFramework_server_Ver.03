package com.framework.demo.model.agreements.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveAgreementsDto {

    private String contents;

    private String title;

}
