package com.easysoft.sma.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easysoft.sma.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	public List<Product> findByStatusOrderByName(String status);
	
	public List<Product> findByCategoryId(String categoryId);

	public boolean existsByCategoryId(String categoryId);
	
	public boolean existsBySalesYearAndName(int salesYear, String name);
}
