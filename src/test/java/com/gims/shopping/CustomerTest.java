package com.gims.shopping;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CustomerTest {

	
	@Test
	public void getRandomNumberTest() {
		int min = 1;
		int max = 10;
		int randomNum = Customer.getRandomNumber(min, max);
		
		assertTrue(randomNum >= min);
		assertTrue(randomNum <= max);
	}
}
