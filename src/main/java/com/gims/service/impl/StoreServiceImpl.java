package com.gims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gims.dao.StoreRepository;
import com.gims.service.StoreService;

/**
 * Implementation of StoreService methods. Manipulates data and transforms it
 * based on business logic.
 * 
 * @author Tim Flucker
 *
 */
@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreRepository storeRepository;

	/**
	 * retrieves the id values of all Store records.
	 */
	@Override
	public List<Long> retrieveIds() {
		return storeRepository.retrieveIds();
	}

}
