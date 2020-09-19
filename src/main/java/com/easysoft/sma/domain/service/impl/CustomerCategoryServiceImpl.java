package com.easysoft.sma.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.easysoft.lib.common.LocalMessageSource;
import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.common.exception.ConflictException;
import com.easysoft.lib.common.exception.NotFoundException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.lib.jdb.infrastructure.PageHelper;
import com.easysoft.sma.domain.dto.CustomerCategoryAddRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryPageRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryPageResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryUpdateRequest;
import com.easysoft.sma.domain.entity.CustomerCategory;
import com.easysoft.sma.domain.entity.QCustomerCategory;
import com.easysoft.sma.domain.repository.CustomerCategoryRepository;
import com.easysoft.sma.domain.repository.CustomerRepository;
import com.easysoft.sma.domain.service.CustomerCategoryService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class CustomerCategoryServiceImpl implements CustomerCategoryService {

	@Autowired
	private CustomerCategoryRepository customerCategoryRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	@Autowired
	private LocalMessageSource messageSource;

	@Autowired
	private PageHelper pageHelper;

	private CustomerCategory findById(String id) throws NotFoundException {
		
		return this.customerCategoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(
						this.messageSource.getMessage("data_not_found",
								new Object[] { this.messageSource.getMessage("customer_category"),
										this.messageSource.getMessage("id"), id })));
	}

	private void hasSameName(String name) throws ConflictException {
		if (this.customerCategoryRepository.existsByName(name)) {
			throw new ConflictException(
					this.messageSource.getMessage("data_exists",
							new Object[] { this.messageSource.getMessage("customer_category"),
									this.messageSource.getMessage("name"), name }));
		}
	}

	@Override
	public void add(CustomerCategoryAddRequest request) throws BusinessException {

		this.hasSameName(request.getName());

		CustomerCategory entity = new CustomerCategory();
		entity.create(request.getName(), request.getRemark());
		this.customerCategoryRepository.save(entity);
	}

	@Override
	public void update(CustomerCategoryUpdateRequest request) throws BusinessException {

		CustomerCategory entity = this.findById(request.getId());
		String name = request.getName();
		if (!entity.getName().equals(name)) {
			this.hasSameName(name);
		}

		entity.update(name, request.getRemark());
		this.customerCategoryRepository.save(entity);
	}

	@Override
	public void delete(String id) throws BusinessException {

		if (!this.customerCategoryRepository.existsById(id)) {
			throw new BusinessException(410, "Gone",
					this.messageSource.getMessage("data_not_found",
							new Object[] { this.messageSource.getMessage("customer_category"),
									this.messageSource.getMessage("id"), id }));
		}

		boolean existsCustomer = this.customerRepository.existsByCategoryId(id);
		if (existsCustomer) {
			throw new ConflictException(this.messageSource.getMessage("exists_customer"));
		}

		this.customerCategoryRepository.deleteById(id);

	}

	@Override
	public CustomerCategoryDetailResponse find(String id) throws BusinessException {

		return new CustomerCategoryDetailResponse(this.findById(id));
	}

	@Override
	public List<TextValueObject> findAll() {
		QCustomerCategory cc = QCustomerCategory.customerCategory;
		return jpaQueryFactory.select(Projections.bean(TextValueObject.class, cc.id.as("value"), cc.name.as("text")))
				.from(cc).orderBy(cc.name.asc()).fetch();
	}

	@Override
	public PageResponse<CustomerCategoryPageResponse> page(CustomerCategoryPageRequest request) {

		QCustomerCategory cc = QCustomerCategory.customerCategory;
		JPAQuery<CustomerCategoryPageResponse> query = jpaQueryFactory
				.select(Projections.bean(CustomerCategoryPageResponse.class, cc.id, cc.name, cc.remark)).from(cc);

		if (StringUtils.hasText(request.getName())) {
			query.where(cc.name.like("%" + request.getName() + "%"));
		}

		Pageable pageable = this.pageHelper.getPageRequest(request.getPage(), request.getSize(), request.getSortField(), request.getSort());
		CustomerCategoryPageResponse.setOrder(query, cc, pageable.getSort());
		QueryResults<CustomerCategoryPageResponse> result = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
		return new PageResponse<CustomerCategoryPageResponse>(result.getTotal(), result.getResults());
	}

}
