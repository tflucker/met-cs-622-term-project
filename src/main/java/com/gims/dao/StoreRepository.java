package com.gims.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gims.shopping.Store;

/**
 * JPA repository that interacts with the Store database table. Uses built-in
 * functionality from the CRUD repository interface in addition to custom
 * queries.
 * 
 * @author Tim Flucker
 *
 */
@Repository
public interface StoreRepository extends CrudRepository<Store, Long> {

	@Query(value = "select ID from STORE", nativeQuery = true)
	public List<Long> retrieveIds();
}
