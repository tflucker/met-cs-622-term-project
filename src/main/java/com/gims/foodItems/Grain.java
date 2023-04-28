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
@DiscriminatorValue("GRAIN")
public class Grain extends FoodItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CUSTOM_FIELD1")
	private boolean isGlutenFree;

	@Column(name = "CUSTOM_FIELD2")
	private boolean isMultigrain;

	public boolean isGlutenFree() {
		return isGlutenFree;
	}

	public void setGlutenFree(boolean isGlutenFree) {
		this.isGlutenFree = isGlutenFree;
	}

	public boolean isMultigrain() {
		return isMultigrain;
	}

	public void setMultigrain(boolean isMultigrain) {
		this.isMultigrain = isMultigrain;
	}

	public Grain(long id, String name, double calories, boolean isRelatedToFoodAllergy, double wholeSaleCost,
			double retailPrice, boolean isGlutenFree, boolean isMultigrain) {
		super(id, name, calories, isRelatedToFoodAllergy, wholeSaleCost, retailPrice);
		this.isGlutenFree = isGlutenFree;
		this.isMultigrain = isMultigrain;
	}

	public Grain() {
	}

	@Override
	public String getType() {
		return FoodTypes.GRAIN.getName();
	}

	@Override
	public Class<?> getClazz() {
		return FoodTypes.GRAIN.getClazz();
	}

}
