package com.easysoft.sma.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easysoft.sma.domain.entity.CustomerCategory;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory, String> {

	public boolean existsByName(String name);
}
