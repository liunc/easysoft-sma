package com.easysoft.sma.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.easysoft.lib.common.LocalMessageSource;
import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.common.exception.ConflictException;
import com.easysoft.lib.common.exception.GoneException;
import com.easysoft.lib.common.exception.NotFoundException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.sma.domain.dto.ProductCategoryAddRequest;
import com.easysoft.sma.domain.dto.ProductCategoryDetailResponse;
import com.easysoft.sma.domain.dto.ProductCategoryPageRequest;
import com.easysoft.sma.domain.dto.ProductCategoryPageResponse;
import com.easysoft.sma.domain.dto.ProductCategoryUpdateRequest;
import com.easysoft.sma.domain.entity.ProductCategory;
import com.easysoft.sma.domain.entity.QProductCategory;
import com.easysoft.sma.domain.repository.ProductCategoryRepository;
import com.easysoft.sma.domain.repository.ProductRepository;
import com.easysoft.sma.domain.service.ProductCategoryService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	@Autowired
	private LocalMessageSource messageSource;

	private ProductCategory findById(String id) throws NotFoundException {

		return this.productCategoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(this.messageSource.getMessage("data_not_found",
						new Object[] { this.messageSource.getMessage("product_category"),
								this.messageSource.getMessage("id"), id })));
	}

	private void hasSameName(String name) throws ConflictException {
		if (this.productCategoryRepository.existsByName(name)) {
			throw new ConflictException(this.messageSource.getMessage("data_exists", new Object[] {
					this.messageSource.getMessage("product_category"), this.messageSource.getMessage("name"), name }));
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(ProductCategoryAddRequest request) throws BusinessException {

		this.hasSameName(request.getName());

		ProductCategory entity = new ProductCategory();
		entity.create(request.getName(), request.getRemark());
		this.productCategoryRepository.save(entity);
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(ProductCategoryUpdateRequest request) throws BusinessException {

		ProductCategory entity = this.findById(request.getId());
		String name = request.getName();
		if (!entity.getName().equals(name)) {
			this.hasSameName(name);
		}

		entity.update(name, request.getRemark());
		this.productCategoryRepository.save(entity);
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changeStatus(String id) throws BusinessException {

		ProductCategory entity = this.findById(id);
		entity.changeStatus();
		this.productCategoryRepository.save(entity);
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id) throws BusinessException {

		if (!this.productCategoryRepository.existsById(id)) {
			throw new GoneException(this.messageSource.getMessage("data_not_found", new Object[] {
					this.messageSource.getMessage("product_category"), this.messageSource.getMessage("id"), id }));
		}

		boolean existsProduct = this.productRepository.existsByCategoryId(id);
		if (existsProduct) {
			throw new ConflictException(this.messageSource.getMessage("exists_product"));
		}

		this.productCategoryRepository.deleteById(id);
		
	}

	@Override
	public ProductCategoryDetailResponse find(String id) throws BusinessException {

		return new ProductCategoryDetailResponse(this.findById(id));
	}

	@Override
	public List<TextValueObject> findAll(String status) {

		QProductCategory pc = QProductCategory.productCategory;

        JPAQuery<TextValueObject> query = jpaQueryFactory
                .select(Projections.bean(TextValueObject.class, pc.id.as("value"), pc.name.as("text"))).from(pc);

        if (StringUtils.hasText(status)) {
            query.where(pc.status.eq(status));
        }
        return query.orderBy(pc.name.asc()).fetch();
	}

	@Override
	public PageResponse<ProductCategoryPageResponse> page(Pageable pageable, ProductCategoryPageRequest request) {
		
		QProductCategory pc = QProductCategory.productCategory;
		JPAQuery<ProductCategoryPageResponse> query = jpaQueryFactory
				.select(Projections.bean(ProductCategoryPageResponse.class, pc.id, pc.name, pc.status, pc.remark)).from(pc);

		if (request != null) {
			if (StringUtils.hasText(request.getName())) {
				query.where(pc.name.like("%" + request.getName() + "%"));
			}
			if (StringUtils.hasText(request.getStatus())) {
				query.where(pc.status.eq(request.getStatus()));
			}
		}

		ProductCategoryPageResponse.setOrder(query, pc, pageable.getSort());
		QueryResults<ProductCategoryPageResponse> result = query.offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetchResults();
		return new PageResponse<ProductCategoryPageResponse>(result.getTotal(), result.getResults());
	}

}
