package com.easysoft.sma.domain.repository;

import java.util.List;

import com.easysoft.lib.jdb.domain.repository.BaseRepository;
import com.easysoft.sma.domain.entity.Product;

public interface ProductRepository extends BaseRepository<Product, String> {

	public List<Product> findByStatusOrderByName(String status);
	
	public List<Product> findByCategoryId(String categoryId);

	public boolean existsByCategoryId(String categoryId);
	
	public boolean existsBySalesYearAndName(int salesYear, String name);
}
