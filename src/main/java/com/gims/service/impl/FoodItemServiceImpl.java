package com.gims.service.impl;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gims.constants.Commands;
import com.gims.constants.CommonConstants;
import com.gims.dao.FoodItemRepository;
import com.gims.dao.StoreInventoryRepository;
import com.gims.dao.StoreRepository;
import com.gims.foodItems.FoodItem;
import com.gims.service.FoodItemService;

/**
 * Implementation of FoodItemService methods. Manipulates data and transforms it
 * based on business logic.
 * 
 * @author Tim Flucker
 *
 */
@Service
public class FoodItemServiceImpl implements FoodItemService {

	@Autowired
	private FoodItemRepository foodItemRepository;

	@Autowired
	private StoreInventoryRepository storeInventoryRepository;

	@Autowired
	private StoreRepository storeRepository;

	/**
	 * returns all FoodItem objects from the database.
	 */
	@Override
	public List<FoodItem> displayAll() {
		List<FoodItem> inventory = StreamSupport.stream(foodItemRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return inventory;
	}

	/**
	 * returns the FoodItem record with the provided id value.
	 */
	@Override
	public FoodItem findById(long id) {
		FoodItem result = foodItemRepository.findById(id).orElse(null);
		if (result == null) {
			System.out.println(MessageFormat.format(CommonConstants.NO_ITEM_FOUND_ID, id));
		}
		return result;
	}

	/**
	 * returns all FoodItem records whose NAME value contains the provided search
	 * string
	 */
	@Override
	public List<FoodItem> findByName(String search) {
		List<FoodItem> searchResults = foodItemRepository.searchByName(search);
		return searchResults;
	}

	/**
	 * Prompt user for field input, then adds it to the database.
	 */
	@Override
	public <T> FoodItem addNewFoodItem(T item, Scanner in) {
		FoodItem result = null;
		// get all fields in new generic object by combining the fields of the
		// superclass (FoodItem) with the fields of the provided 'newItem' generic class
		List<Field> fields = Stream.concat(Arrays.stream(FoodItem.class.getDeclaredFields()),
				Arrays.stream(item.getClass().getDeclaredFields())).collect(Collectors.toList());

		// call 'setFoodItemFields' method which will prompt user for input for each
		// field in the provided 'item' parameter
		boolean validAddition = setFoodItemFields(item, fields, in, Commands.ADD.name());

		// add newItem to inventory, if valid addition
		if (validAddition) {
			result = foodItemRepository.save((FoodItem) item);
			System.out.println(MessageFormat.format(CommonConstants.ADD_SUCCESS_MSG, result.getId()));
		}
		return result;
	}

	/**
	 * Prompts user for new values for the specified FoodItem object.
	 */
	@Override
	public FoodItem editFoodItem(long id, Scanner in) {
		FoodItem item = foodItemRepository.findById(id).orElse(null);

		if (item != null) {
			// get all fields from parent class 'FoodItem', since all objects have these
			// fields then add fields specific to the type of foodItem (Protein, Dairy,
			// Grain, etc.)
			List<Field> fields = Stream.concat(Arrays.stream(FoodItem.class.getDeclaredFields()),
					Arrays.stream(item.getClass().getDeclaredFields())).collect(Collectors.toList());

			// call 'setFoodItemFields' method which will prompt user for input for each
			// field in the provided 'item' parameter
			setFoodItemFields(item, fields, in, Commands.EDIT.name());
			System.out.println(MessageFormat.format(CommonConstants.EDIT_SUCCESS_MSG, id));
		}

		return item;
	}

	/**
	 * Using the provided list of fields, set new values for each field with user
	 * input. If modificationType is ADD, then manually set Id, otherwise do not
	 * touch that field.
	 * 
	 * @param <T>
	 * @param item
	 * @param fields
	 * @param in
	 * @param modificationType
	 * @return
	 */
	private <T> boolean setFoodItemFields(T item, List<Field> fields, Scanner in, String modificationType) {
		String fieldName = "";
		in.useDelimiter(System.lineSeparator());
		boolean isValid = true;
		for (Field field : fields) {
			field.setAccessible(true);
			fieldName = field.getName();
			Class<?> fieldType = field.getType();
			try {
				if (fieldName.equals("id") || fieldName.equals("serialVersionUID")) {
					// do nothing
				} else {
					// check if modificationType is EDIT and print the current value
					if (modificationType.equals(Commands.EDIT.name())) {
						System.out.println(MessageFormat.format(CommonConstants.CURRENT_VALUES, fieldName.toUpperCase(),
								field.get(item)));
						System.out.println(
								"If you do not wish to modify this field, please press the space bar and then enter.");
					}
					// print instruction for user, so they know which field they are setting and
					// what type of data they should provide
					System.out.println(MessageFormat.format(CommonConstants.PROMPT_FOR_NEW_VALUES,
							fieldName.toUpperCase(), fieldType));

					// get input for field
					String input = in.hasNext() ? in.next() : in.nextLine();
//					String input = in.nextLine();

					// if value is not empty, then set new value
					// else, skip this field (do not update value)
					if (!input.trim().isEmpty()) {
						// check data types so data is set properly
						if (boolean.class.equals(fieldType)) {
							field.set(item, Boolean.valueOf(input));
						} else if (fieldType.isPrimitive()
								&& (double.class.equals(fieldType) || int.class.equals(fieldType))) {
							// check for double class, set double if true, else set int
							if (double.class.equals(fieldType)) {
								field.set(item, Double.valueOf(input));
							} else {
								field.set(item, Integer.valueOf(input));
							}
						} else if (!fieldType.isPrimitive()) {
							field.set(item, input);
						}
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException | InputMismatchException e) {
				e.printStackTrace();
				System.err.println(MessageFormat.format(CommonConstants.FIELD_UPDATE_ERR, fieldName.toUpperCase()));
				isValid = false;
			}
		}
		return isValid;
	}

	/**
	 * Deletes the FoodItem record from the database. User must confirm decision
	 * before deletion begins. Deletion will also delete relevant items in the
	 * StoreInventory table.
	 */
	@Override
	public void deleteItem(long id, Scanner in) {
		FoodItem item = foodItemRepository.findById(id).orElse(null);

		if (item != null) {
			System.out.println("Deleting this FoodItem will also remove it from all Store Inventories...");
			System.out.println("Are you sure you want to delete this item?(Yes/No)");
			String confirm = in.next();
			if (confirm.equalsIgnoreCase("Yes")) {
				storeInventoryRepository.deleteStoreInventoryForFoodId(id);
				foodItemRepository.delete(item);
				System.out.println(MessageFormat.format(CommonConstants.SUCCESSFUL_DELETE, id));
			} else {
				System.out.println("Deletion cancelled.");
			}

		} else {
			System.err.println(CommonConstants.INVALID_ID_ERR_MSG);
		}
	}

	@Override
	public void getMetrics() {
		String[] expensive = foodItemRepository.findMostExpensiveItem().split(",");
		System.out.println(MessageFormat.format("Most Expensive Item (ID:{0}): {1} - ${2}", expensive[0], expensive[1],
				expensive[2]));

		String[] cheapest = foodItemRepository.findLeastExpensiveItem().split(",");
		System.out.println(MessageFormat.format("Least Expensive Item (ID:{0}): {1} - ${2}", cheapest[0], cheapest[1],
				cheapest[2]));

		storeRepository.retrieveIds().stream().forEach(storeId -> {
			String result = storeInventoryRepository.getInventoryStock(storeId);
			if (result != null) {
				String[] quantities = result.split(",");
				System.out.println(MessageFormat.format("Store {0}: {1}/{2} total quantity", storeId, quantities[0],
						quantities[1]));
			}
		});
	}

}
