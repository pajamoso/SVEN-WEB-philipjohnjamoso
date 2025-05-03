package com.pawtastic.petservices.repository;

import com.pawtastic.petservices.entity.FormRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRequestRepository extends JpaRepository<FormRequest, Long> {
}
