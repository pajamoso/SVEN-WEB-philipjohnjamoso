package com.pawtastic.petservices.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EncryptedPayloadDTO {
    private List<Integer> salt;
    private String data;
}
