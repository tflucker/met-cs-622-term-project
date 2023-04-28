package com.gims.foodItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.gims.constants.FoodTypes;

public class FoodItemsTest {

	public Protein p = new Protein(1l, "1lb Chicken Breast", 150, false, 3.00, 5.00, "Animal", false, false,
			false);

	public Vegetable v = new Vegetable(6l, "Lettuce", 25, false, 1.00, 3.00, true);

	@Test
	public void inheritedFieldsTest() {
		assertEquals("1lb Chicken Breast", p.getName());
		assertEquals(150, p.getCalories());
		assertEquals(false, p.isRelatedToFoodAllergy());
		assertEquals(3.00, p.getWholeSaleCost());
		assertEquals(5.00, p.getRetailPrice());
	}

	@Test
	public void instanceFieldsTest() {
		assertEquals("Animal", p.getProteinSource());
		assertEquals(false, p.isVegan());
		assertEquals(false, p.isCrueltyFree());
		assertEquals(false, p.isHormoneFree());
	}

	@Test
	public void getTypeTest1() {
		assertTrue(FoodTypes.PROTEIN.getName().equals(p.getType()));
	}

	@Test
	public void getClazzTest() {
		assertEquals(p.getClazz(), FoodTypes.PROTEIN.getClazz());
	}

	@Test
	public void inheritedFieldsTest2() {
		assertEquals("Lettuce", v.getName());
		assertEquals(25, v.getCalories());
		assertEquals(false, v.isRelatedToFoodAllergy());
		assertEquals(1.00, v.getWholeSaleCost());
		assertEquals(3.00, v.getRetailPrice());
	}

	@Test
	public void instanceFieldsTest2() {
		assertEquals(true, v.isLocallySourced());
	}

	@Test
	public void getTypeTest2() {
		assertTrue(FoodTypes.VEGETABLE.getName().equals(v.getType()));
	}
}
