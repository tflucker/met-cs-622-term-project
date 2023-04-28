# Grocery Store Inventory Management Application (GIMS)
## Created By: Timothy Flucker
## Last Modified: 02/13/2022

# Prerequisites
 - Java 8 Installation 
 - Java IDE downloaded and installed
 - Maven downloaded and added to Path
 

# Abstract
This project was developed by Timothy Flucker in the Spring 2022 semester for the MET 622 - Advanced Programming Techniques in Java class.  It is meant to provide practice concepts such as abstract classes and methods, polymorphism, File I/O operations, custom exceptions, Java Generics, etc. This project is meant to simulate a Grocery Inventory Management system, and has functionality to view a fictional store's inventory as well as manipulate it. The specific functionality this application provides is detailed in the 'Functionality' section below. 

# Functionality
 - ADD: Prompts the user for a type of item that they wish to create. Once this type is provided, the user is prompted to provide values for the various fields of that object. Once all values are provided, that object is added to the inventory. 
 - EDIT: Prompts the user for the ID of the item to be modified. If the ID value is numeric and an item in the inventory has that ID, iterate through all of that item's fields, and prompt the user for new values.
 - DELETE: Prompts the user for the ID of the item. If that ID value is numeric and an item in the inventory has that ID that item will be removed from the inventory.
 - SHOWALL: Displays the full inventory.
 - SEARCH: Prompts the user for an ID value or a name value, then searches the inventory for items that match the provided input.
 - IMPORT: Prompts the user for a filepath, then tries to read that file for data that can be parsed into data objects recognized by the system.
 - EXPORT: Takes the grocery inventory and exports it as a comma separated values (CSV) file, where each item is a line.
 - SHOPPING: Generates a random number of Customer who will concurrently purchase items from the grocery store inventory.  As items are purchased, messages are printed to the console indicating a complete purchase, incomplete purchase, or failure to purchase. If enough items are out of stock, an automatic RESTOCK will be triggered.
 - RESTOCK: Iterates through inventory and finds all items that have less than their max quantity, and updates the quantity to match the max quantity.  It also prints messages to notify the user which items were restocked and how much that cost.
 - EXIT: This command exits out of the application.

# Installation Instructions
1. Download Zip file of code
2. Unzip and save onto computer
3. Open your Java IDE (Eclispe, IntelliJ, or other)
4. Import the project into you Java IDE as a Maven project
	- When importing make sure that you navigate to the location where the 'pom.xml' file is located.
5. Once the project is loaded, run a maven build with the goals 'clean install' to ensure all dependencies are loaded.
6. Run the 'GroceryInventoryManagementSystem.java' class in the 'com.timflucker.groceryInventory' package as a Java Application.
7. Follow the instructions provided in the console and enter commands in as required. 


# Running Project as JAR file - **STILL UNDER DEVELOPMENT**
If you are running the project as a JAR file,:
1. Navigate to the folder containing the JAR file.
2. Open a command prompt at this location
3. Please use the following command: 

```
	java -jar <jar file name>
```