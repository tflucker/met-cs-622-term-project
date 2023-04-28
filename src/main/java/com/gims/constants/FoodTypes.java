package com.gims.constants;

import com.gims.foodItems.Dairy;
import com.gims.foodItems.Fruit;
import com.gims.foodItems.Grain;
import com.gims.foodItems.Protein;
import com.gims.foodItems.Vegetable;

/**
 * Defines the various food types which are allowable to this application.
 * Values in this enum are used to verify imported data to ensure that the
 * proper type and number of arguments are provided in the import file.
 * 
 * @author Tim Flucker
 *
 */
public enum FoodTypes {

	PROTEIN("PROTEIN", 12, Protein.class), VEGETABLE("VEGETABLE", 9, Vegetable.class), FRUIT("FRUIT", 9, Fruit.class),
	GRAIN("GRAIN", 10, Grain.class), DAIRY("DAIRY", 10, Dairy.class);

	private String name;
	private int numberOfArguments;

	private Class<?> clazz;

	public static Class<?> getClazzFromType(String type){
		Class<?> returnClazz = null;
		for(FoodTypes foodType : FoodTypes.values()) {
			if(foodType.getName().equals(type)) {
				return foodType.getClazz();
			}
		}
		return returnClazz;
	}
	
	private FoodTypes(String name, int numberOfArguments, Class<?> clazz) {
		this.name = name;
		this.numberOfArguments = numberOfArguments;
		this.clazz = clazz;
	}

	public int getNumberOfArguments() {
		return numberOfArguments;
	}

	public void setNumberOfArguments(int numberOfArguments) {
		this.numberOfArguments = numberOfArguments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

}
