package com.gims.controller;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.gims.constants.Commands;
import com.gims.constants.CommonConstants;
import com.gims.dao.FoodItemRepository;
import com.gims.foodItems.FoodItem;

/**
 * Controller class that contains all methods related to manipulating the
 * FoodItems model, and the classes that inherit it, in the GIMS application.
 * 
 * @author Tim Flucker
 *
 */
public class FoodItemController {

	private static long inventorySize = 0;

	@Autowired
	private FoodItemRepository foodItemRepository;

	/**
	 * Prints all items in the 'inventory' attribute to the console.
	 * 
	 * @throws JsonProcessingException
	 */
	public List<FoodItem> displayAll() {
		Iterable<FoodItem> data = foodItemRepository.findAll();
		List<FoodItem> inventory = StreamSupport.stream(data.spliterator(), false).collect(Collectors.toList());
		System.out.println("Size of inventory: " + inventory.size());
		return inventory;
	}

	/**
	 * Search inventory for the item that has the id provided by the user. Item is
	 * printed to the console
	 * 
	 * @param <T>
	 * 
	 * @param id
	 * @return
	 */
	public static <T> T searchById(long id, List<T> inventory) {
		// TODO: Replace this code with code that queries a database

		// use a stream + lamda function to find an item with a matching id inside the
		// inventory list, else return null
		T result = inventory.stream().filter(food -> ((FoodItem) food).getId() == id).findFirst().orElse(null);

		if (result == null) {
			System.out.println(MessageFormat.format(CommonConstants.NO_ITEM_FOUND_ID, id));
		} else {
			printObject(result);
		}
		return result;
	}

	public FoodItem searchById(long id) {
		FoodItem result = foodItemRepository.findById(id).orElse(null);
		if (result == null) {
			System.out.println(MessageFormat.format(CommonConstants.NO_ITEM_FOUND_ID, id));
		} else {
			printObject(result);
		}
		return result;
	}

	/**
	 * Search inventory for items whose name contains the characters provided by the
	 * user (case-insensitive). Item(s) are printed to the console.
	 * 
	 * @param name
	 * @throws JsonProcessingException
	 */
	public static List<FoodItem> searchByName(String name, List<FoodItem> inventory) throws JsonProcessingException {
		// TODO: Replace this code with code that queries a database
		List<FoodItem> results = new ArrayList<>();

		inventory.stream().forEach(food -> {
			if (food.getName().toUpperCase().contains(name.toUpperCase())) {
				results.add(food);
			}
		});

		if (results.size() == 0) {
			System.out.println(MessageFormat.format(CommonConstants.NO_ITEM_FOUND_NAME, name));
		} else if (results.size() == 1) {
			printObject(results.get(0));
		} else {
			String resultString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(results);
			System.out.println(resultString);
		}
		return results;
	}

	/**
	 * Prints the foodItem to the console for the user. The generic FoodItem is
	 * downcast to its instance type to ensure that the fields are properly
	 * serialized.
	 * 
	 * @param <T>
	 * 
	 * @param food
	 * @throws JsonProcessingException
	 */
	public static <T> void printObject(T food) {
		ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
		try {
			String output = writer.writeValueAsString(food);
			System.out.println(output);
		} catch (JsonProcessingException e) {
			System.err.println(CommonConstants.UNABLE_TO_PRINT);
		}

	}

	/**
	 * Get all of the fields of the generic T object (FoodObject or a sub-object of
	 * it). Call the 'setFoodItemFields' method to prompt user for values for object
	 * fields. If no errors occur while setting field values, add object to
	 * inventory.
	 * 
	 * @param <T>
	 * @param newItem
	 * @param inventory
	 * @param in
	 */
	public static <T> void addFoodItem(T newItem, List<FoodItem> inventory, Scanner in) {

		// set global variable value to be used in the 'setFoodItemFields' method
		inventorySize = inventory.size();

		// get all fields in new generic object by combining the fields of the
		// superclass (FoodItem) with the fields of the provided 'newItem' generic class
		List<Field> fields = Stream.concat(Arrays.stream(FoodItem.class.getDeclaredFields()),
				Arrays.stream(newItem.getClass().getDeclaredFields())).collect(Collectors.toList());

		// call 'setFoodItemFields' method which will prompt user for input for each
		// field in the provided 'item' parameter
		boolean validAddition = setFoodItemFields(newItem, fields, in, Commands.ADD.name());

		// add newItem to inventory, if valid addition
		if (validAddition) {
			// TODO: Replace this code with code that saves the 'newItem'
			inventory.add((FoodItem) newItem);
			System.out.println(MessageFormat.format(CommonConstants.ADD_SUCCESS_MSG, inventory.size()));
			FoodItemController.printObject(newItem);
		}

	}

	/**
	 * Find the FoodItem object whose id matches the method argument, then get that
	 * object's fields. Call the 'setFoodItemFields' method to prompt user for new
	 * values.
	 * 
	 * @param id
	 * @param inventory
	 * @param in
	 */
	public static void editFoodItem(long id, List<? extends FoodItem> inventory, Scanner in) {
		// TODO: Replace this code with code that queries a database
		FoodItem item = searchById(id, inventory);

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
			FoodItemController.printObject(item);

		} else {
			System.err.println(CommonConstants.INVALID_ID_ERR_MSG);
		}
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
	private static <T> boolean setFoodItemFields(T item, List<Field> fields, Scanner in, String modificationType) {
		String fieldName = "";
		in.useDelimiter(System.lineSeparator());
		boolean isValid = true;
		for (Field field : fields) {
			field.setAccessible(true);
			fieldName = field.getName();
			Class<?> fieldType = field.getType();
			try {
				// set id value for ADD command
				if (modificationType.equals(Commands.ADD.name()) && fieldName.equals("id")) {
					inventorySize += 1;
					field.setLong(item, inventorySize);
				} else if ((modificationType.equals(Commands.EDIT.name()) && fieldName.equals("id"))
						|| fieldName.equals("serialVersionUID")) {
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
//					String input = in.hasNext() ? in.next() : in.nextLine();
					String input = in.nextLine();

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
	 * Using the id parameter, search the inventory for an item with that id. If
	 * found, remove that item from the inventory, and if not found print an error
	 * message.
	 * 
	 * @param id
	 * @param inventory
	 * @throws JsonProcessingException
	 */
	public static void deleteFoodItem(long id, List<? extends FoodItem> inventory, Scanner in) {

		// find foodItem by id
		FoodItem item = searchById(id, inventory);
		// if item is not null, then remove from inventory and print a success message
		// else, print an error message.
		if (item != null) {

			System.out.println("Are you sure you want to delete this item?(Yes/No)");
			String confirm = in.next();
			if (confirm.equalsIgnoreCase("Yes")) {
				inventory.remove(item);
				System.out.println(MessageFormat.format(CommonConstants.SUCCESSFUL_DELETE, id));
			} else {
				System.out.println("Deletion cancelled.");
			}

		} else {
			System.err.println(CommonConstants.INVALID_ID_ERR_MSG);
		}

	}
}
