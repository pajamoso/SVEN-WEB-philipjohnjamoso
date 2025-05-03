package com.pawtastic.petservices.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormRequestDTO {
    private String frequency;
    private String date;
    private String time;
    private DaysDTO days;
    private String notes;
}
