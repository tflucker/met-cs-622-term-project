package com.gims.controller;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gims.config.Config;
import com.gims.constants.CommonConstants;
import com.gims.service.ConfigService;
import com.gims.service.StoreInventoryService;
import com.gims.shopping.StoreInventory;

/**
 * A controller class that contains file operations for the GIMS application.
 * 
 * @author Tim Flucker
 *
 */
@Component
public class FileController {

	private static StoreInventoryService storeInventoryService;

	private static ConfigService configService;

	@Autowired
	public FileController(StoreInventoryService storeInventoryService, ConfigService configService) {
		this.storeInventoryService = storeInventoryService;
		this.configService = configService;
	}

	public static List<StoreInventory> importData(String filename) {
		List<StoreInventory> masterInventory = new ArrayList<>();
		boolean moreData = true;
		try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream(filename))) {

			while (moreData) {
				StoreInventory itemInventory = (StoreInventory) inFile.readObject();
				if (itemInventory != null) {
					masterInventory.add(itemInventory);
				} else {
					moreData = false;
				}
			}
		} catch (EOFException e) {
			System.out.println("Finished loading data from: \n\t" + filename + "\n");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		masterInventory.stream().forEach(inventory -> {
			storeInventoryService.checkForData(inventory);
		});

		return masterInventory;
	}

	public static List<Config> importConfigs(String filename) {
		List<Config> allConfigs = new ArrayList<>();
		boolean moreData = true;

		try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream(filename))) {
			while (moreData) {
				Config config = (Config) inFile.readObject();
				if (config != null) {
					allConfigs.add(config);
				} else {
					moreData = false;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		allConfigs.stream().forEach(c -> {
			configService.saveConfig(c);
		});

		return allConfigs;
	}

	/**
	 * Takes the current inventory as a parameter, creates a new data (.dat) file
	 * and then writes each item in the inventory to the new file. Prints message
	 * once all data has been written.
	 * 
	 * @param inventory
	 */
	public static void exportData(long storeId) {
		List<StoreInventory> inventory = new ArrayList<>();
		String filename = "groceryStore" + storeId + "Inventory_" + System.currentTimeMillis() + ".dat";

		// determine data being imported based on method parameter value
		if (storeId == -1) {
			inventory = storeInventoryService.findAll();
			filename = "groceryStore_ALL_Inventory_" + System.currentTimeMillis() + ".dat";
		} else {
			inventory = storeInventoryService.retrieveStoreInventory(storeId);
			filename = "groceryStore_" + storeId + "_Inventory_" + System.currentTimeMillis() + ".dat";
		}
		//
		try (ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream(filename))) {

			// iterate through all items in the inventory
			inventory.stream().forEach(item -> {
				try {
					// write object to file
					outFile.writeObject(item);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			System.out.println(MessageFormat.format(CommonConstants.EXPORT_SUCCESS_MSG, filename));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
