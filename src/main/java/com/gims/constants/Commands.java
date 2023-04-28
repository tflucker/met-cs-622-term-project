package com.gims.constants;

/**
 * Enum defining the approved commands that the user can provide in the console
 * to access the application functionality.
 * 
 * @author Tim Flucker
 *
 */
public enum Commands {

	SHOW_ALL, STORE_INVENTORY, SEARCH, ADD, EDIT, DELETE, IMPORT, EXPORT, EXPORT_ALL, SHOPPING, RESTOCK, METRICS, EXIT;

	public static boolean isValidCommand(String value) {
		boolean result = false;
		for (Commands command : Commands.values()) {
			if (command.name().equals(value)) {
				result = true;
			}
		}
		return result;
	}
}
