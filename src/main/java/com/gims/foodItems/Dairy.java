package com.gims.foodItems;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.gims.constants.FoodTypes;

/**
 * Model class that inherits from FoodItem.
 * 
 * @author Tim Flucker
 *
 */
@Entity
@DiscriminatorValue("DAIRY")
public class Dairy extends FoodItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CUSTOM_FIELD1")
	private String dairySource;
	@Column(name = "CUSTOM_FIELD2")
	private boolean containsLactose;

	public Dairy(long id, String name, double calories, boolean isRelatedToFoodAllergy, double wholeSaleCost,
			double retailPrice, String dairySource, boolean containsLactose) {
		super(id, name, calories, isRelatedToFoodAllergy, wholeSaleCost, retailPrice);
		this.dairySource = dairySource;
		this.containsLactose = containsLactose;
	}

	public Dairy() {

	}

	public String getDairySource() {
		return dairySource;
	}

	public void setDairySource(String dairySource) {
		this.dairySource = dairySource;
	}

	public boolean isContainsLactose() {
		return containsLactose;
	}

	public void setContainsLactose(boolean containsLactose) {
		this.containsLactose = containsLactose;
	}

	@Override
	public String getType() {
		return FoodTypes.DAIRY.getName();
	}

	@Override
	public Class<?> getClazz() {
		return FoodTypes.DAIRY.getClazz();
	}

}
