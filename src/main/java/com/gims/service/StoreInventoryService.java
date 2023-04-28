package com.gims.service;

import java.util.List;

import com.gims.shopping.StoreInventory;

/**
 * Service class which defines methods related to the StoreInventory entities.
 * Actual implementation can be found in the StoreInventoryServiceImpl class.
 * 
 * @author Tim Flucker
 *
 */
public interface StoreInventoryService {

	public List<StoreInventory> retrieveStoreInventory(long storeId);

	public List<StoreInventory> restockInventory(long storeId);

	public void customerShopping(long storeId);

	public void checkForData(StoreInventory storeInventory);

	public List<StoreInventory> findAll();
}
