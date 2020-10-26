package com.easysoft.sma.domain.repository;

import java.util.List;

import com.easysoft.sma.domain.entity.Product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {

	public List<Product> findByStatusOrderByName(String status);
	
	public List<Product> findByCategoryId(String categoryId);

	public boolean existsByCategoryId(String categoryId);
	
	public boolean existsBySalesYearAndName(int salesYear, String name);
}
