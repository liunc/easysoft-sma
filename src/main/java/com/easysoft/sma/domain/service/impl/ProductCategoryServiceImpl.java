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

		entity.update(name, request.getStatus(), request.getRemark());
		this.productCategoryRepository.save(entity);
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id) throws BusinessException {

		if (!this.productCategoryRepository.existsById(id)) {
			throw new BusinessException(410, "Gone", this.messageSource.getMessage("data_not_found", new Object[] {
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
	public List<TextValueObject> findAll() {
		QProductCategory cc = QProductCategory.productCategory;
		return jpaQueryFactory.select(Projections.bean(TextValueObject.class, cc.id.as("value"), cc.name.as("text")))
				.from(cc).orderBy(cc.name.asc()).fetch();
	}

	@Override
	public PageResponse<ProductCategoryPageResponse> page(Pageable pageable, ProductCategoryPageRequest request) {
		QProductCategory cc = QProductCategory.productCategory;
		JPAQuery<ProductCategoryPageResponse> query = jpaQueryFactory
				.select(Projections.bean(ProductCategoryPageResponse.class, cc.id, cc.name, cc.remark)).from(cc);

		if (request != null) {
			if (StringUtils.hasText(request.getName())) {
				query.where(cc.name.like("%" + request.getName() + "%"));
			}
			if (StringUtils.hasText(request.getStatus())) {
				query.where(cc.status.eq(request.getStatus()));
			}
		}

		ProductCategoryPageResponse.setOrder(query, cc, pageable.getSort());
		QueryResults<ProductCategoryPageResponse> result = query.offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetchResults();
		return new PageResponse<ProductCategoryPageResponse>(result.getTotal(), result.getResults());
	}

}
