package com.gims.service;

import java.util.List;
import java.util.Scanner;

import com.gims.foodItems.FoodItem;

/**
 * Service class which defines methods related to the FoodItem entity. Actual
 * implementation can be found in the FoodItemServiceImpl class.
 * 
 * @author Tim Flucker
 *
 */
public interface FoodItemService {

	public List<FoodItem> displayAll();

	public FoodItem findById(long id);

	public List<FoodItem> findByName(String search);

	public <T> FoodItem addNewFoodItem(T item, Scanner in);

	public FoodItem editFoodItem(long id, Scanner in);

	public void deleteItem(long id, Scanner in);

	public void getMetrics();
}
