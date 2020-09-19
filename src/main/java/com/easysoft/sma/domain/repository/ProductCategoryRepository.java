package com.easysoft.sma.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easysoft.sma.domain.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

	public boolean existsByName(String name);
}
