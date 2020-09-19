package com.easysoft.sma.domain.repository;

import com.easysoft.lib.jdb.domain.repository.BaseRepository;
import com.easysoft.sma.domain.entity.ProductCategory;

public interface ProductCategoryRepository extends BaseRepository<ProductCategory, String> {

	public boolean existsByName(String name);
}
