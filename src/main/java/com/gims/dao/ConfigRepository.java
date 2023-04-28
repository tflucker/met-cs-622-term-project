package com.gims.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gims.config.Config;

@Repository
public interface ConfigRepository extends CrudRepository<Config, Long> {

	@Query(value = "select * from CONFIG where USER_ID = :userId and TYPE = APP", nativeQuery = true)
	public List<Config> getAppConfigs(@Param(value = "userId") String userId);

	@Query(value = "select * from CONFIG where USER_ID = :userId and STORE_ID = :storeId and TYPE = STORE", nativeQuery = true)
	public List<Config> getStoreConfigs(@Param(value = "userId") String userId, @Param(value = "storeId") long storeId);
	
	@Query(value = "select * from CONFIG where USER_ID = :userId and NAME = :name", nativeQuery = true)
	public Config findConfigByNameAndUserId(@Param(value="name") String name, @Param(value="userId") String userId);

	@Query(value = "select * from CONFIG where USER_ID = :userId and NAME = :name and STORE_ID = :storeId", nativeQuery = true)
	public Config findStoreConfig(@Param(value="name") String name, @Param(value="userId") String userId, @Param(value="storeId") long storeId);

	
	@Query(value = "select count(*) from CONFIG where USER_ID = :userId", nativeQuery = true)
	public long findByUserId(@Param(value="userId")String userId);
}
