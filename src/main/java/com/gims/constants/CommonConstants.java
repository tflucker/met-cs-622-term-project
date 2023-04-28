package com.gims.constants;

/**
 * Contains static strings referenced throughout the application. Allows for
 * reusability and guarantees that these strings will not be modified.
 * 
 * @author Tim Flucker
 *
 */
public class CommonConstants {

	// Constant values for application to function
	public static final String INITIAL_DATASET_FILEPATH = "importFiles/groceryStoreInventory_default.dat";

	// General Text variables
	public static final String APP_TITLE = "Grocery Inventory Management System (GIMS)";
	public static final String DIVIDING_LINE = "\n----------------------------------------------------------------------------------------------------\n";
	public static final String WELCOME_TEXT = DIVIDING_LINE
			+ "Welcome to the Grocery Inventory Management System (GIMS)! This system allows a user to "
			+ "\nview all, search, add, edit, delete, import, and export data related to food items in a grocery store. "
			+ "\nPlease select one the commands listed below to invoke specific functionality. This project was developed "
			+ "\nby Timothy Flucker during the Spring 2022 semester for the MET CS 622 class offered by Boston University."
			+ DIVIDING_LINE;
	public static final String GOODBYE_TEXT = "Thank you for using this application!\nGoodbye!";
	public static final String NO_DATA_DETECTED = "NO DATA DETECTED. PLEASE IMPORT DATA!";
	public static final String FILEPATH_CONFIRMATION = "Reading Data from {0} \n\nPlease navigate to the console to view / manipulate the data.";

	// strings related to the commands and instructions
	public static final String INITIAL_DATA_INSTRUCTIONS = "Please select one of the following options for the initial data:\n 1 - Default data set (25 records)\n 2 - Provide file to import\n\nPlease select 1 or 2.";
	public static final String ENTER_COMMANDS_STR = "Please enter one of the following commands: ";
	public static final String SEARCH_INSTRUCTIONS = "Please enter an id or type the name of the item ...";
	public static final String ADD_INSTRUCTIONS = "Please enter the type of the item you would like to create.\n Valid options: [PROTEIN, VEGETABLE, FRUIT, DAIRY, GRAIN]";
	public static final String EDIT_INSTRUCTIONS = "Please enter the id of the item you would like to edit.";
	public static final String DELETE_INSTRUCTIONS = "Please enter the id of the item you would like to delete.";
	public static final String IMPORT_INSTRUCTIONS = "Please enter the full filepath of the file you would like to import.";

	// Search Text
	public static final String NO_ITEM_FOUND_ID = "No Food Item found with provided ID {0}";
	public static final String NO_ITEM_FOUND_NAME = "No Food Item found that matches the provided input: {0}";

	// Add + Edit Text
	public static final String CURRENT_VALUES = "Current value of field {0}: {1}";
	public static final String PROMPT_FOR_NEW_VALUES = "Please enter the new value for field {0}, that is a valid {1}";
	public static final String FIELD_UPDATE_ERR = "ERROR: Unable to update field {0}";
	public static final String ADD_SUCCESS_MSG = "SUCCESS: Item {0} has been created!";
	public static final String EDIT_SUCCESS_MSG = "SUCCESS: Item {0} has been updated!";

	// Delete Text
	public static final String SUCCESSFUL_DELETE = "SUCCESS: Item with id {0} successfully deleted.";

	// Restocking Text
	public static final String RESTOCK_TEXT = "Checking inventory to see if items need to be restocked ... \n";
	public static final String RESTOCK_ITEM = "Restocking item {0}: Amount: {1}, Cost: {2} --> Total: ${3}";
	public static final String RESTOCK_TOTAL = "\nTotal Restocking Cost: ${0}";

	// Shopping Text
	public static final String NUMBER_OF_CUSTOMERS = "Number of Customers: {0}";
	public static final String RUNNING_THREAD = "Customer-{0} Running";
	public static final String THREAD_BUYING_SUCCESS = "Customer-{0} Buying {1} of item: {2}";
	public static final String INCOMPLETE_PURCHASE = "Customer-{0} only able to purchase {1} {2} items";
	public static final String THREAD_BUYING_FAIL = "Customer-{0} was unable to buy {1}";
	public static final String ALL_THREADS_DONE = "All Customers have finished shopping!";

	public static final String ITEM_OUT_OF_STOCK = "Item {0} is now OUT OF STOCK.";
	public static final String TOTAL_OUT_OF_STOCK = "Total out of stock items: {0}";
	public static final String TRIGGERING_RESTOCK = "Automatically triggering 'RESTOCK' functionality...";
	
	
	// Generic Error Messages
	public static final String GENERIC_ERR_MSG = "ERROR: An error has occurred. Please restart the application.";
	public static final String INVALID_ID_ERR_MSG = "ERROR: Invalid id value provided.";
	public static final String INVALID_COMMAND = "ERROR: Unrecognized command. Please try again.";
	public static final String UNABLE_TO_PRINT = "ERROR: Unable to print object.";

	// Success messaged relate to File I/O
	public static final String IMPORT_SUCCESS = "Successfully imported {0} items!";

	// Error messages related to File I/O
	public static final String INVALID_IMPORT_ERR_MSG = "ERROR: Invalid data discovered during import. Record has not been created.";
	public static final String IMPORT_FILE_NOT_FOUND = "ERROR: No file found at provided location. Please try again.";
	public static final String FILE_IO_ERR_MSG = "ERROR: An I/O error occurred while accessing the file. Please try again.";
	public static final String INVALID_LINE_DATA = "ERROR: The data on line {0} is invalid. For type {1} expected arguments {2}, actual arguments {3}";
	public static final String EXPORT_SUCCESS_MSG = "SUCCESS: Exported data to file: {0}";
	public static final String EXPORT_ERR_MSG = "ERROR: Unable to export item: {0}. This record will not be included in the export file.";

	// Warning messages related to File I/O
	public static final String EMPTY_LINE_WARNING = "WARNING: An empty line was detected in the import file. No data was created from this line.";
	public static final String UNKNOWN_FOOD_TYPE_WARNING = "WARNING: Data with an unknown value detected on line {0}, no data was created from this line. "
			+ "Please add this data manually.";
	
	// Exception Messages
	public static final String CONFIG_VALUE_ERROR = "ERROR: Invalid configuration value detected! Using default value ...";
	public static final String NO_CONFIGS_FOUND = "ERROR: No configuration value found for userId. Generating default configurations ...";

}
