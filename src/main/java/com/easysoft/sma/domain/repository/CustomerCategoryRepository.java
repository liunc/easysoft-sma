package com.easysoft.sma.domain.repository;

import com.easysoft.lib.jdb.domain.repository.BaseRepository;
import com.easysoft.sma.domain.entity.CustomerCategory;

public interface CustomerCategoryRepository extends BaseRepository<CustomerCategory, String> {

	public boolean existsByName(String name);
}
