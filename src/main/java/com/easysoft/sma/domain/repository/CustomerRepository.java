package com.easysoft.sma.domain.repository;

import com.easysoft.sma.domain.entity.Customer;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {

	public boolean existsByCategoryId(String categoryId);

	public boolean existsByWechatName(String wechatName);
}
