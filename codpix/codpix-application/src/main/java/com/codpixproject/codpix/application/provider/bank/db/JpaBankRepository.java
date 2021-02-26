package com.codpixproject.codpix.application.provider.bank.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface JpaBankRepository extends JpaRepository<JpaBank, UUID> {
	
	boolean existsByInstitutionCode(String institutionCode);
}
