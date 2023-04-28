package com.gims.service;

import java.util.List;

/**
 * Service class which defines methods related to the Store entity. Actual
 * implementation can be found in the FoodItemServiceImpl class.
 * 
 * @author Tim Flucker
 *
 */
public interface StoreService {

	public List<Long> retrieveIds();
}
