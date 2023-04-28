package com.gims.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gims.foodItems.FoodItem;

/**
 * JPA repository that interacts with the FoodItem database table. Uses built-in
 * functionality from the CRUD repository interface in addition to custom
 * queries.
 * 
 * @author Tim Flucker
 *
 */
@Repository
public interface FoodItemRepository extends CrudRepository<FoodItem, Long> {

	@Query(value = "select * from FOOD_ITEM where NAME like %:search% order by ID asc", nativeQuery = true)
	public List<FoodItem> searchByName(@Param(value = "search") String search);

	@Query(value = "select ID, NAME, max(RETAIL_PRICE) from FOOD_ITEM", nativeQuery = true)
	public String findMostExpensiveItem();

	@Query(value = "select ID, NAME, min(RETAIL_PRICE) from FOOD_ITEM", nativeQuery = true)
	public String findLeastExpensiveItem();
}
