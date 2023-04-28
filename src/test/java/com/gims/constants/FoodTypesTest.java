package com.gims.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.gims.foodItems.Dairy;

public class FoodTypesTest {

	@Test
	public void getClazzFromTypeTest_validType() {
		String type = FoodTypes.DAIRY.getName();
		Class<?> clazz = FoodTypes.getClazzFromType(type);

		assertEquals(clazz, Dairy.class);
	}

	@Test
	public void getClazzFromTypeTest_invalidType() {
		String type = "KJSDHFLKJSDHFL";
		Class<?> clazz = FoodTypes.getClazzFromType(type);

		assertNull(clazz);
	}
}
