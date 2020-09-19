package com.easysoft.sma.domain.repository;

import com.easysoft.lib.jdb.domain.repository.BaseRepository;
import com.easysoft.sma.domain.entity.Customer;

public interface CustomerRepository extends BaseRepository<Customer, String> {

	public boolean existsByCategoryId(String categoryId);

	public boolean existsByWechatName(String wechatName);
}
