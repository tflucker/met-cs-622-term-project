package com.gims.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gims.shopping.StoreInventory;

/**
 * JPA repository that interacts with the StoreInventory database table. Uses built-in
 * functionality from the CRUD repository interface in addition to custom
 * queries.
 * 
 * @author Tim Flucker
 *
 */
@Repository
public interface StoreInventoryRepository extends CrudRepository<StoreInventory, Long>{

	@Query(value = "select * from STORE_INVENTORY where S_ID = :storeId", nativeQuery = true)
	public List<StoreInventory> findAllAtLocation(@Param(value="storeId") long storeId);
	
	
	@Modifying
	@Transactional
	@Query(value = "delete from STORE_INVENTORY where F_ID = :foodId", nativeQuery = true)
	public void deleteStoreInventoryForFoodId(@Param(value="foodId") long foodId);
	
	@Query(value="select sum(QUANTITY), sum(MAX_QUANTITY) from STORE_INVENTORY where s_id = :storeId", nativeQuery=true)
	public String getInventoryStock(@Param(value="storeId") long storeId);	
}
