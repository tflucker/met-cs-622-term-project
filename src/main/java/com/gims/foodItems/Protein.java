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
@DiscriminatorValue("PROTEIN")
public class Protein extends FoodItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CUSTOM_FIELD1")
	private String proteinSource;

	@Column(name = "CUSTOM_FIELD2")
	private boolean isVegan;

	@Column(name = "CUSTOM_FIELD3")
	private boolean isCrueltyFree;

	@Column(name = "CUSTOM_FIELD4")
	private boolean isHormoneFree;

	public Protein(long id, String name, double calories, boolean isRelatedToFoodAllergy, double wholeSaleCost,
			double retailPrice, String proteinSource, boolean isVegan, boolean isCrueltyFree, boolean isHormoneFree) {
		super(id, name, calories, isRelatedToFoodAllergy, wholeSaleCost, retailPrice);
		this.proteinSource = proteinSource;
		this.isVegan = isVegan;
		this.isCrueltyFree = isCrueltyFree;
		this.isHormoneFree = isHormoneFree;
	}

	public Protein() {

	}

	public String getProteinSource() {
		return proteinSource;
	}

	public void setProteinSource(String proteinSource) {
		this.proteinSource = proteinSource;
	}

	public boolean isVegan() {
		return isVegan;
	}

	public void setVegan(boolean isVegan) {
		this.isVegan = isVegan;
	}

	public boolean isCrueltyFree() {
		return isCrueltyFree;
	}

	public void setCrueltyFree(boolean isCrueltyFree) {
		this.isCrueltyFree = isCrueltyFree;
	}

	public boolean isHormoneFree() {
		return isHormoneFree;
	}

	public void setHormoneFree(boolean isHormoneFree) {
		this.isHormoneFree = isHormoneFree;
	}

	@Override
	public String toString() {
		return super.toString() + "[proteinSource=" + proteinSource + ", isVegan=" + isVegan + ", isCrueltyFree="
				+ isCrueltyFree + ", isHormoneFree=" + isHormoneFree + "]";
	}

	@Override
	public String getType() {
		return FoodTypes.PROTEIN.getName();
	}

	@Override
	public Class<?> getClazz() {
		return FoodTypes.PROTEIN.getClazz();
	}

}
