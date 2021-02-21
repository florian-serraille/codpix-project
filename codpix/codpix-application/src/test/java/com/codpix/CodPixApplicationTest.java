package com.codpix;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

@SpringBootTest
class CodPixApplicationTest {
	
	@Test
	void contextLoads() {
		
		var thrown = catchThrowable(() -> CodPixApplication.main(new String[] {}));
		
		assertThat(thrown).isNull();
	}
	
}