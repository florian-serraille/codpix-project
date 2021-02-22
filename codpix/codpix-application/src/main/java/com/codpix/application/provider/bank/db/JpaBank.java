package com.codpix.application.provider.bank.db;

import com.codpix.domain.bank.Bank;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Setter
@NoArgsConstructor
@Table(name = "BANK")
public class JpaBank {
	
	@Id
	private UUID code;
	private String institutionCode;
	private String name;
	
	public JpaBank(final Bank bank) {
		
		final JpaBank jpaBank = JpaBankMapper.JPA_BANK_MAPPER.map(bank);
		
		this.code = jpaBank.code;
		this.institutionCode = jpaBank.institutionCode;
		this.name = jpaBank.name;
	}
	
	@Mapper
	interface JpaBankMapper {
		
		final JpaBankMapper JPA_BANK_MAPPER = Mappers.getMapper(JpaBankMapper.class);
		
		JpaBank map(Bank bank);
	}
}
