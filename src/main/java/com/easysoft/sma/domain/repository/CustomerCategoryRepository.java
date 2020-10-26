package com.easysoft.sma.domain.repository;

import com.easysoft.sma.domain.entity.CustomerCategory;

import org.springframework.data.repository.CrudRepository;

public interface CustomerCategoryRepository extends CrudRepository<CustomerCategory, String> {

	public boolean existsByName(String name);

	public boolean existsById(String id);
}
