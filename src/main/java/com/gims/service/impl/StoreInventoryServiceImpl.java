package com.gims.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.gims.constants.CommonConstants;
import com.gims.dao.FoodItemRepository;
import com.gims.dao.StoreInventoryRepository;
import com.gims.dao.StoreRepository;
import com.gims.foodItems.FoodItem;
import com.gims.service.ConfigService;
import com.gims.service.StoreInventoryService;
import com.gims.shopping.Customer;
import com.gims.shopping.Store;
import com.gims.shopping.StoreInventory;

/**
 * Implementation of StoreInventoryService methods. Manipulates data and
 * transforms it based on business logic.
 * 
 * @author Tim Flucker
 *
 */
@Service
public class StoreInventoryServiceImpl implements StoreInventoryService {

	@Autowired
	private StoreInventoryRepository storeInventoryRepository;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private FoodItemRepository foodItemRepository;

	@Autowired
	private ConfigService configService;

	/**
	 * Returns all StoreInventory objects from the database
	 */
	@Override
	public List<StoreInventory> findAll() {
		return StreamSupport.stream(storeInventoryRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	/**
	 * returns all StoreInventory objects based on the provided storeId
	 */
	@Override
	public List<StoreInventory> retrieveStoreInventory(long storeId) {
		List<StoreInventory> inventory = storeInventoryRepository.findAllAtLocation(storeId);
		return inventory;
	}

	/**
	 * Iterate through every item in the inventory parameter, compare the quantity
	 * of each item against its maxQuantity. If different set the quantity to the
	 * maxQuantity and compute how the cost (quantity_difference * wholesale_cost).
	 * Print message if needed restock and how much it cost.
	 * 
	 * @param inventory
	 */
	@Override
	public List<StoreInventory> restockInventory(long storeId) {
		List<StoreInventory> inventory = retrieveStoreInventory(storeId);
		System.out.println(CommonConstants.RESTOCK_TEXT);
		// contains each restock cost per item, used to determine total cost
		List<Double> restockTotal = new ArrayList<>();

		// find all items that have mismatching quantity and max quantity
		// modify each item to reset quantity
		// print message showing itemized restock cost
		inventory.stream().filter(itemInventory -> itemInventory.getQuantity() != itemInventory.getMaxQuantity())
				.forEach(itemInventory -> {
					int difference = itemInventory.getMaxQuantity() - itemInventory.getQuantity();
					double restockCost = difference * itemInventory.getFoodItem().getWholeSaleCost();
					itemInventory.setQuantity(itemInventory.getMaxQuantity());
					restockTotal.add(restockCost);
					System.out.println(
							MessageFormat.format(CommonConstants.RESTOCK_ITEM, itemInventory.getFoodItem().getName(),
									difference, itemInventory.getFoodItem().getWholeSaleCost(), restockCost));
				});
		storeInventoryRepository.saveAll(inventory);
		// print total restock cost at the end
		if (restockTotal.size() > 0) {
			System.out.println(MessageFormat.format(CommonConstants.RESTOCK_TOTAL,
					restockTotal.stream().mapToDouble(cost -> cost.doubleValue()).sum()));
		}

		return inventory;
	}

	/**
	 * Create a random number of Customer objects and then execute them concurrently
	 * using an ExecutorService thread pool. These Customer objects will manipulate
	 * the quantity of items in the inventory and messages will be printed based on
	 * whether the Customer was able to buy the item, or if there wasn't enough
	 * stock left. Triggers restock if total number of out of stock items is greater
	 * than 1/3 the size of the inventory.
	 * 
	 * @param inventory
	 */
	@Override
	public void customerShopping(long storeId) {
		List<StoreInventory> inventory = retrieveStoreInventory(storeId);

		// call configService to get 'shopping.threads' config, and then get the generic
		// value which will be converted to an int
		int threads = configService.getConfigValue(configService.findStoreConfig("shopping.threads", "test", storeId));

		// determine a random number of customers (min=10,max=50)
		int numberOfCustomers = getRandomNumber(10, 50);
		System.out.println(MessageFormat.format(CommonConstants.NUMBER_OF_CUSTOMERS, numberOfCustomers));
		ExecutorService ex = Executors.newFixedThreadPool(threads);

		// create list of customer objects for ease of access for the thread pool
		// executor
		List<Customer> customers = new ArrayList<>();

		// Create a synchronized instance of the inventory list
		List<StoreInventory> syncInventory = Collections.synchronizedList(inventory);

		// create customer objects and add them to the list
		IntStream.rangeClosed(1, numberOfCustomers).forEach(num -> {
			customers.add(new Customer(num, syncInventory));
		});

		// execute each customer created above in a thread using the thread pool
		customers.forEach(ex::execute);

		// save the synchronized STORE_INVENTORY to persist customer shopping changes
		storeInventoryRepository.saveAll(syncInventory);

		// shutdown executor to prevent any new processes
		ex.shutdown();

		while (!ex.isTerminated()) {
			// do nothing until all threads have finished
		}

		System.out.println(CommonConstants.ALL_THREADS_DONE);

		// prints messages to tell user which items are out of stock
		int outOfStockTotal = printShoppingResults(inventory);

		// If that number is greater than 1/3 of the total inventory, then automatically
		// trigger a restock.
		if ((Double.valueOf(outOfStockTotal) / Double.valueOf(inventory.size())) >= Double.valueOf(1.00 / 3.00)) {
			System.out.println(CommonConstants.TRIGGERING_RESTOCK);
			restockInventory(storeId);
		}
	}

	/**
	 * Checks the FoodItem and Store sub-object in the StoreInventory argument to
	 * see if they exist in the database. If they do not, the data will be created.
	 */
	public void checkForData(StoreInventory inventory) {

		FoodItem item = inventory.getFoodItem();
		Store store = inventory.getStore();

		// check to see if the food item referenced here exists or not
		// if it does not exist then create it
		if (!foodItemRepository.existsById(item.getId())) {
			foodItemRepository.save(item);
			System.out.println("New Food Item detected, saving ...");
		}

		// check to see if the store referenced here exists or not
		// if it does not exist then create it
		if (!storeInventoryRepository.existsById(store.getId())) {
			storeRepository.save(store);
			System.out.println("New Store detected, saving ...");
		}

		// save store
		storeInventoryRepository.save(inventory);
		System.out.println("Saving inventory record ...");

	}

	/**
	 * Print messages showing which items are out of stock, and how many are out of
	 * stock. Returns total number of out of stock items.
	 * 
	 * @param inventory
	 */
	private static int printShoppingResults(List<StoreInventory> inventory) {
		// print back to user which items are now out of stock
		List<StoreInventory> outOfStockItems = inventory.stream().filter(t -> t.getQuantity() == 0)
				.collect(Collectors.toList());
		outOfStockItems.forEach(t -> {
			System.out.println(MessageFormat.format(CommonConstants.ITEM_OUT_OF_STOCK, t.getFoodItem().getName()));
		});
		System.out.println(MessageFormat.format(CommonConstants.TOTAL_OUT_OF_STOCK, outOfStockItems.size()));

		return outOfStockItems.size();
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

	public static <T> void printObject(T food) {
		ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
		try {
			String output = writer.writeValueAsString(food);
			System.out.println(output);
		} catch (JsonProcessingException e) {
			System.err.println(CommonConstants.UNABLE_TO_PRINT);
		}
	}

}
