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
@DiscriminatorValue("VEGETABLE")
public class Vegetable extends FoodItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CUSTOM_FIELD1")
	public boolean isLocallySourced;

	public Vegetable(long id, String name, double calories, boolean isRelatedToFoodAllergy, double wholeSaleCost,
			double retailPrice, boolean isLocallySourced) {
		super(id, name, calories, isRelatedToFoodAllergy, wholeSaleCost, retailPrice);
		this.isLocallySourced = isLocallySourced;
	}

	public Vegetable() {
	}

	public boolean isLocallySourced() {
		return isLocallySourced;
	}

	public void setLocallySourced(boolean isLocallySourced) {
		this.isLocallySourced = isLocallySourced;
	}

	@Override
	public String getType() {
		return FoodTypes.VEGETABLE.getName();
	}

	@Override
	public Class<?> getClazz() {
		return FoodTypes.VEGETABLE.getClazz();
	}
}
