package com.gims.shopping;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.gims.constants.CommonConstants;

/**
 * Simulates a customer shopping in a store. Shopping occurs in a concurrent
 * fashion on a synchronized list of StoreInventory objects.
 * 
 * @author Tim Flucker
 *
 */
public class Customer implements Runnable {

	private int id;

	private int sizeOfList;

	private HashMap<Integer, Integer> shoppingList;

	private double totalShoppingCost;

	public List<StoreInventory> inventory;

	/**
	 * Run the Customer object with the Runnable interface to interact with the
	 * shared List<FoodItem> inventory. Modify an item's quantity as necessary and
	 * print messages to the console.
	 */
	@Override
	public void run() {
		// synchronizing the inventory so that modifications persist
		synchronized (inventory) {
			// loop through shopping list HashMap
			System.out.println(MessageFormat.format(CommonConstants.RUNNING_THREAD, this.id));
			this.shoppingList.forEach((id, quant) -> {
				StoreInventory itemInventory = this.inventory.get(id);
				// compare with map quantity
				int currentQuantity = itemInventory.getQuantity();
				// if (current - quant) > 0, then buy (set item quantity to difference),
				// else if current quantity is greater than 0, but less than the quantity, then
				// buy and print message "only buying x quantity"
				// else print message "Couldn't buy"
				if ((currentQuantity - quant) > 0) {
					System.out.println(MessageFormat.format(CommonConstants.THREAD_BUYING_SUCCESS, this.id, quant,
							itemInventory.getFoodItem().getName()));
					itemInventory.setQuantity(currentQuantity - quant);
				} else if (0 < currentQuantity && currentQuantity < quant) {
					itemInventory.setQuantity(0);
					System.out.println(MessageFormat.format(CommonConstants.INCOMPLETE_PURCHASE, this.id,
							currentQuantity, itemInventory.getFoodItem().getName()));
				} else {
					System.out.println(MessageFormat.format(CommonConstants.THREAD_BUYING_FAIL, this.id,
							itemInventory.getFoodItem().getName()));
				}
			});
		}
	}

	/**
	 * Instantiate new Customer object in preparation for the 'run' method.
	 * 
	 * @param id
	 * @param inventory
	 */
	public Customer(int id, List<StoreInventory> inventory) {
		this.id = id;
		this.inventory = inventory;
		this.sizeOfList = getRandomNumber(1, 10);

		// contains unique item ids (key) and a random quantity (value), represents a
		// shopping list
		HashMap<Integer, Integer> shoppingList = new HashMap<>();

		// create stream of random integers each corresponding to an item id in
		// inventory, and create shopping list entries with these ids and assign a
		// random quantity
		new Random().ints(1, inventory.size()).distinct().limit(this.sizeOfList).boxed().forEach(randomId -> {
			int randomQuantity = getRandomNumber(1, 10);
			shoppingList.put(randomId, randomQuantity);
		});

		this.shoppingList = shoppingList;
	}

	public Customer() {
	}

	/**
	 * Utility method to generate a random integer number between a provided min and
	 * max value.
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNumber(int min, int max) {
		return (int) Math.floor(Math.random() * (max - min + 1) + min);
	}

	public int getSizeOfList() {
		return sizeOfList;
	}

	public void setSizeOfList(int sizeOfList) {
		this.sizeOfList = sizeOfList;
	}

	public HashMap<Integer, Integer> getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(HashMap<Integer, Integer> shoppingList) {
		this.shoppingList = shoppingList;
	}

	public double getTotalShoppingCost() {
		return totalShoppingCost;
	}

	public void setTotalShoppingCost(double totalShoppingCost) {
		this.totalShoppingCost = totalShoppingCost;
	}

	public List<StoreInventory> getInventory() {
		return inventory;
	}

	public void setInventory(List<StoreInventory> inventory) {
		this.inventory = inventory;
	}

}
