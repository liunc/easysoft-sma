package com.easysoft.sma.domain.repository;

import com.easysoft.sma.domain.entity.ProductCategory;

import org.springframework.data.repository.CrudRepository;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, String> {

	public boolean existsByName(String name);
}
