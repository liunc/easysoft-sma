package com.easysoft.sma.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easysoft.sma.domain.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

	public boolean existsByCategoryId(String categoryId);

	public boolean existsByWechatName(String wechatName);
}
