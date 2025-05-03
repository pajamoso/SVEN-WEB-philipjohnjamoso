package com.pawtastic.petservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawtastic.petservices.dto.EncryptedPayloadDTO;
import com.pawtastic.petservices.dto.FormRequestDTO;
import com.pawtastic.petservices.entity.FormRequest;
import com.pawtastic.petservices.entity.Days;
import com.pawtastic.petservices.repository.FormRequestRepository;
import com.pawtastic.petservices.util.AESGCMUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/form")
@CrossOrigin(origins = "http://localhost:4200") //To set with specific allowed domains
public class FormController {

    private static final Logger logger = LoggerFactory.getLogger(FormController.class);  // Logger initialization

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FormRequestRepository formRequestRepository;

    @PostMapping("/submit")
    public ResponseEntity<?> submitEncryptedForm(@RequestBody EncryptedPayloadDTO payload) {
        try {
            // Decrypt the data
            String jsonDecrypted = AESGCMUtil.decrypt(payload);

            FormRequestDTO formRequestDTO = objectMapper.readValue(jsonDecrypted, FormRequestDTO.class);

            FormRequest formRequest = new FormRequest();
            formRequest.setFrequency(formRequestDTO.getFrequency());
            formRequest.setDate(formRequestDTO.getDate());
            formRequest.setTime(formRequestDTO.getTime());
            formRequest.setNotes(formRequestDTO.getNotes());

            Days days = new Days();
            days.setMonday(formRequestDTO.getDays().isMon());
            days.setTuesday(formRequestDTO.getDays().isTue());
            days.setWednesday(formRequestDTO.getDays().isWed());
            days.setThursday(formRequestDTO.getDays().isThu());
            days.setFriday(formRequestDTO.getDays().isFri());
            days.setSaturday(formRequestDTO.getDays().isSat());
            days.setSunday(formRequestDTO.getDays().isSun());

            logger.info("FormRequest Details to be saved: ");
            logger.info("Frequency: {}", formRequestDTO.getFrequency());
            logger.info("Date: {}", formRequestDTO.getDate());
            logger.info("Time: {}", formRequestDTO.getTime());
            logger.info("Notes: {}", formRequestDTO.getNotes());
            logger.info("Days: Monday-{}, Tuesday-{}, Wednesday-{}, Thursday-{}, Friday-{}, Saturday-{}, Sunday-{}",
                    formRequestDTO.getDays().isMon(),
                    formRequestDTO.getDays().isTue(),
                    formRequestDTO.getDays().isWed(),
                    formRequestDTO.getDays().isThu(),
                    formRequestDTO.getDays().isFri(),
                    formRequestDTO.getDays().isSat(),
                    formRequestDTO.getDays().isSun());

            // Save Days entity and associate it with FormRequest
            formRequest.setDays(days);

            // Save the formRequest to H2 database
            formRequestRepository.save(formRequest);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Success\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Failed to process encrypted form.");
        }
    }
}
