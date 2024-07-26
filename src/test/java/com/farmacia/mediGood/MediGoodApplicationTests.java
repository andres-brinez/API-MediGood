package com.farmacia.mediGood;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MediGoodApplicationTests {

	@Test
	public void testAddition() {
		// Realiza la suma de 1 + 1
		int sum = 1 + 1;

		// Verifica que el resultado es igual a 2
		assertEquals(2, sum, "La suma de 1 + 1 debe ser 2");
	}

}
