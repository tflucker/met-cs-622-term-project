package com.gims.foodItems;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Abstract class which is the parent class for all other FoodItems in this
 * application.
 * 
 * @author Tim Flucker
 *
 */
@Entity(name = "FOOD_ITEM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FOOD_TYPE", discriminatorType = DiscriminatorType.STRING)
@JsonIgnoreProperties(value = { "clazz" })
public abstract class FoodItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CALORIES")
	private double calories;

	@Column(name = "IS_RELATED_TO_FOOD_ALLERGY")
	private boolean isRelatedToFoodAllergy;

	@Column(name = "WHOLESALE_COST")
	private double wholeSaleCost;

	@Column(name = "RETAIL_PRICE")
	private double retailPrice;
	
	public FoodItem() {

	}

	public FoodItem(long id, double retailPrice) {
		this.id = id;
		this.retailPrice = retailPrice;
	}
	
	public FoodItem(long id, String name, double calories, boolean isRelatedToFoodAllergy, double wholeSaleCost,
			double retailPrice) {
		super();
		this.id = id;
		this.name = name;
		this.calories = calories;
		this.isRelatedToFoodAllergy = isRelatedToFoodAllergy;
		this.wholeSaleCost = wholeSaleCost;
		this.retailPrice = retailPrice;
	}

	public abstract String getType();

	public abstract Class<?> getClazz();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public boolean isRelatedToFoodAllergy() {
		return isRelatedToFoodAllergy;
	}

	public void setRelatedToFoodAllergy(boolean isRelatedToFoodAllergy) {
		this.isRelatedToFoodAllergy = isRelatedToFoodAllergy;
	}

	public double getWholeSaleCost() {
		return wholeSaleCost;
	}

	public void setWholeSaleCost(double wholeSaleCost) {
		this.wholeSaleCost = wholeSaleCost;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	@Override
	public String toString() {
		return "FoodItem [id=" + id + ", name=" + name + ", calories=" + calories + ", isRelatedToFoodAllergy="
				+ isRelatedToFoodAllergy + ", wholeSaleCost=" + wholeSaleCost + ", retailPrice=" + retailPrice + "]";
	}

}
