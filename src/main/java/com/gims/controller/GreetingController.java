package com.gims.controller;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.gims.constants.Commands;
import com.gims.constants.CommonConstants;
import com.gims.constants.FoodTypes;
import com.gims.exception.NoConfigsFoundException;
import com.gims.foodItems.FoodItem;
import com.gims.service.ConfigService;
import com.gims.service.FoodItemService;
import com.gims.service.StoreInventoryService;
import com.gims.service.StoreService;
import com.gims.shopping.StoreInventory;

/**
 * This controller is the entry point for the application that is called in the
 * main method of this project. Initiates the application data, and handles user
 * input to invoke the associated controller method.
 * 
 * @author Tim Flucker
 *
 */
@Component
public class GreetingController {

	private static FoodItemService foodItemService;
	private static StoreService storeService;
	private static StoreInventoryService storeInventoryService;
	private static ConfigService configService;

	private static long storeId = 0;
	private static String userId = "";
	
	@Autowired
	public GreetingController(FoodItemService foodItemService, StoreService storeService,
			StoreInventoryService storeInventoryService, ConfigService configService) {
		this.foodItemService = foodItemService;
		this.storeService = storeService;
		this.storeInventoryService = storeInventoryService;
		this.configService = configService;
	}

	public static Scanner in = new Scanner(System.in);

	/**
	 * Prompts user for input and then invokes related method based on that user
	 * input. Results from methods are printed to console.
	 */
	public static void getUserInput() {

		String input = "";
		getUserId();
		try {

			while (!input.equals(Commands.EXIT.name())) {
				// print valid list of commands
				System.out.println(CommonConstants.DIVIDING_LINE + CommonConstants.ENTER_COMMANDS_STR
						+ Arrays.asList(Commands.values()) + CommonConstants.DIVIDING_LINE);
				input = in.next().toUpperCase();
				if (input != null && Commands.isValidCommand(input)) {
					callCommandFunction(input);
				} else {
					System.out.println(CommonConstants.INVALID_COMMAND);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(CommonConstants.GENERIC_ERR_MSG);
		} finally {
			in.close();
		}
	}

	/**
	 * Prompts user for userId value and verifies that this value exists in the database.  If userId not found, then create default set of configurations
	 */
	private static void getUserId() {
		System.out.println("Please enter your userId value: ");
		String input = in.next();
		try {
			// check if configs exist for userId, if not then throw NoConfigsFoundException
			configService.checkForConfigWithUserId(input);
		} catch (NoConfigsFoundException nce) {
			// if no configs found for userId, then generate defaults
			configService.generateDefaultConfigs(input);
		}
		userId = input;
	}

	private static long getStoreId() {
		String input = "";
		long storeId = 0;
		List<Long> storeIds = storeService.retrieveIds();
		while (storeId == 0) {
			System.out.println("Please select a valid store id: " + storeIds);
			System.out.println("This store id will be used when retrieving information from the database.");
			input = in.next();
			if (isValidId(input)) {
				storeId = Long.parseLong(input);
			}
		}
		return storeId;
	}

	/**
	 * Tries to match user input 'command' to a valid command. If found then invokes
	 * method related to that command. Either calls the FoodItemController to
	 * manipulate the FoodItem inventory (display all, search, add, edit, delete) of
	 * the GroceryFileUtils to import or export files. Other functionality under
	 * development.
	 * 
	 * @param command
	 */
	public static void callCommandFunction(String command) {
		String input = "";
		List<StoreInventory> storeInventory = new ArrayList<>();
		List<FoodItem> foodItems = new ArrayList<>();
		FoodItem item = null;
		in.reset();
		try {

			switch (command) {
			case "SHOW_ALL":
				foodItems = foodItemService.displayAll();
				printObject(foodItems);
				break;
			case "STORE_INVENTORY":
				storeInventory = storeInventoryService.retrieveStoreInventory(getStoreId());
				printObject(storeInventory);
				break;
			case "SEARCH":
				// print to console and get user input.
				System.out.println(CommonConstants.SEARCH_INSTRUCTIONS);
				input = in.next();
				// check to see if provided input is numeric
				if (isValidId(input)) {
					// if number is provided, search inventory by id
					item = foodItemService.findById(Long.parseLong(input));
					printObject(item);
				} else {
					// if number not provided, search inventory by name
					foodItems = foodItemService.findByName(input);
					printObject(foodItems);
				}
				break;
			case "ADD":
				// print to console and get user input.
				System.out.println(CommonConstants.ADD_INSTRUCTIONS);
				input = in.next().toUpperCase();
				item = foodItemService.addNewFoodItem(FoodTypes.getClazzFromType(input).newInstance(), in);
				printObject(item);
				break;
			case "EDIT":
				System.out.println(CommonConstants.EDIT_INSTRUCTIONS);
				input = in.next();

				// check to see if provided input is numeric
				if (isValidId(input)) {
					item = foodItemService.editFoodItem(Long.parseLong(input), in);
					printObject(item);
				}
				break;
			case "DELETE":
				System.out.println(CommonConstants.DELETE_INSTRUCTIONS);
				input = in.next();

				// check to see if provided input is numeric
				if (isValidId(input)) {
					foodItemService.deleteItem(Long.parseLong(input), in);
				}
				break;
			case "IMPORT":
				System.out.println(CommonConstants.IMPORT_INSTRUCTIONS);
				in.useDelimiter(System.lineSeparator());
				String filepath = in.next();
				filepath = new File(Paths.get(filepath).toUri()).getAbsolutePath();
				FileController.importData(filepath);
				break;
			case "EXPORT":
				FileController.exportData(getStoreId());
				break;
			case "EXPORT_ALL":
				FileController.exportData(-1);
				break;
			case "SHOPPING":
				storeInventoryService.customerShopping(getStoreId());
				break;
			case "RESTOCK":
				storeInventoryService.restockInventory(getStoreId());
				break;
			case "METRICS":
				foodItemService.getMetrics();
				break;
			case "EXIT":
				// prints thank you + good bye text
				System.out.println(CommonConstants.GOODBYE_TEXT);
				break;
			default:
				System.out.println("Command not recognized. Please try again.");
				break;
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			System.err.println(CommonConstants.GENERIC_ERR_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(CommonConstants.GENERIC_ERR_MSG);
		}
	}

	/**
	 * Validates that the provided input can be parsed into a Long value.
	 * 
	 * @param input
	 * @return
	 */
	private static boolean isValidId(String input) {
		boolean result = false;
		try {
			// if number is provided, search inventory by id
			Long.parseLong(input);
			result = true;
		} catch (NumberFormatException e) {
			// do nothing
		}
		return result;
	}

	public static <T> void printObject(T item) {
		ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
		try {
			String output = writer.writeValueAsString(item);
			System.out.println(output);
		} catch (JsonProcessingException e) {
			System.err.println(CommonConstants.UNABLE_TO_PRINT);
		}
	}
}
