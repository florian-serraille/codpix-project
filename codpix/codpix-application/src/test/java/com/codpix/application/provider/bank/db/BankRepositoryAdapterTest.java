package com.codpix.application.provider.bank.db;

import com.codpix.domain.bank.Bank;
import com.codpix.domain.bank.BankRegistrationRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(BankRepositoryAdapter.class)
class BankRepositoryAdapterTest {
	
	private static Bank domainBank;
	
	@Autowired
	private BankRepositoryAdapter repository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@BeforeAll
	static void setUp(){
		domainBank = new Bank(new BankRegistrationRequest("001", "BB"));
		
	}
	
	@Test
	@DisplayName("Verify that bank is effectively persisted into database")
	void shouldEffectivelyPersistBank(){
	
		// Given
		repository.register(domainBank);
		
		// When
		final JpaBank jpaBank = entityManager.find(JpaBank.class, domainBank.getCode());
		
		//Then
		Assertions.assertThat(jpaBank).isNotNull();
	}
	
	@Test
	@DisplayName("Verify that exist method return true when bank is present into database")
	void existShouldReturnTrue(){
		
		// Given
		entityManager.persist(new JpaBank(domainBank));
		
		// When
		final boolean exist = repository.exist(domainBank.getInstitutionCode());
		
		//Then
		Assertions.assertThat(exist).isTrue();
	}
	@Test
	@DisplayName("Verify that exist method return false when no bank is present into database")
	void existShouldReturnFalse(){
		
		// When
		final boolean exist = repository.exist(domainBank.getInstitutionCode());
		
		//Then
		Assertions.assertThat(exist).isFalse();
	}
}