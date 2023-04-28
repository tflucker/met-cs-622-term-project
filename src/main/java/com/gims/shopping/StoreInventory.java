package com.gims.shopping;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.gims.foodItems.FoodItem;

/**
 * Entity that is a combination of the FoodItem and Store entities with
 * additional quantity information.
 * 
 * @author Tim Flucker
 *
 */
@Entity(name = "STORE_INVENTORY")
public class StoreInventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH })
	@JoinColumn(name = "S_ID", referencedColumnName = "ID", updatable = false)
	private Store store;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH })
	@JoinColumn(name = "F_ID", referencedColumnName = "ID", updatable = false)
	private FoodItem foodItem;

	@Column(name = "QUANTITY")
	private int quantity;

	@Column(name = "MAX_QUANTITY")
	private int maxQuantity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public FoodItem getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

}
