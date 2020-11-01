package com.easysoft.sma.domain.service.impl;

import com.easysoft.lib.common.LocalMessageSource;
import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.common.exception.ConflictException;
import com.easysoft.lib.common.exception.GoneException;
import com.easysoft.lib.common.exception.NotFoundException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.lib.jdb.domain.valueobject.ZeroOne;
import com.easysoft.sma.domain.dto.ProductAddRequest;
import com.easysoft.sma.domain.dto.ProductCategoryAddRequest;
import com.easysoft.sma.domain.dto.ProductCategoryDetailResponse;
import com.easysoft.sma.domain.dto.ProductCategoryPageRequest;
import com.easysoft.sma.domain.dto.ProductCategoryPageResponse;
import com.easysoft.sma.domain.dto.ProductCategoryUpdateRequest;
import com.easysoft.sma.domain.dto.ProductDetailResponse;
import com.easysoft.sma.domain.dto.ProductPageRequest;
import com.easysoft.sma.domain.dto.ProductPageResponse;
import com.easysoft.sma.domain.dto.ProductUpdateRequest;
import com.easysoft.sma.domain.entity.Product;
import com.easysoft.sma.domain.entity.ProductCategory;
import com.easysoft.sma.domain.entity.QProduct;
import com.easysoft.sma.domain.entity.QProductCategory;
import com.easysoft.sma.domain.repository.ProductCategoryRepository;
import com.easysoft.sma.domain.repository.ProductRepository;
import com.easysoft.sma.domain.service.ProductService;
import com.easysoft.sma.domain.valueobject.DeliveryMode;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	@Autowired
	private LocalMessageSource messageSource;

	private ProductCategory findCategoryById(String id) throws NotFoundException {

		return this.productCategoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(this.messageSource.getMessage("data_not_found", new Object[] {
						this.messageSource.getMessage("product_category"), this.messageSource.getMessage("id"), id })));
	}

	private void hasSameCategoryName(String name) throws ConflictException {
		if (this.productCategoryRepository.existsByName(name)) {
			throw new ConflictException(this.messageSource.getMessage("data_exists", new Object[] {
					this.messageSource.getMessage("product_category"), this.messageSource.getMessage("name"), name }));
		}
	}

	private Product findById(String id) throws NotFoundException {

		return this.productRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(this.messageSource.getMessage("data_not_found", new Object[] {
						this.messageSource.getMessage("product"), this.messageSource.getMessage("id"), id })));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addProduct(ProductAddRequest request) throws BusinessException {

		DeliveryMode.check(request.getSupportDeliveryMode());

		ProductCategory category = this.findCategoryById(request.getCategoryId());
		if (ZeroOne.ONE.equals(category.getStatus())) {
			throw new GoneException(this.messageSource.getMessage("gone_message",
					new Object[] { this.messageSource.getMessage("product_category") }));
		}

		Product product = new Product();
		product.create(request.getCategoryId(), category.getName(), request.getSalesYear(), request.getPrice(),
				request.getPackUnit(), request.getSpec(), request.getSpecUnit(), request.getSupportDeliveryMode(),
				request.getRemark());

		this.productRepository.save(product);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateProduct(ProductUpdateRequest request) throws BusinessException {

		Product entity = this.findById(request.getId());

		if (!entity.getSupportDeliveryMode().equals(request.getSupportDeliveryMode())) {
			DeliveryMode.check(request.getSupportDeliveryMode());
		}
		entity.update(request.getPrice(), request.getPackUnit(), request.getSpec(), request.getSpecUnit(),
				request.getSupportDeliveryMode(), request.getRemark());
		this.productRepository.save(entity);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changeProductStatus(String id) throws BusinessException {

		Product entity = this.findById(id);
		entity.changeStatus();
		this.productRepository.save(entity);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteProduct(String id) throws BusinessException {

		if (!this.productRepository.existsById(id)) {
			throw new NotFoundException(this.messageSource.getMessage("data_not_found", new Object[] {
					this.messageSource.getMessage("product"), this.messageSource.getMessage("id"), id }));
		}

		// something
		this.productRepository.deleteById(id);
	}

	@Override
	public ProductDetailResponse findProductById(String id) throws BusinessException {

		QProduct p = QProduct.product;
		QProductCategory pc = QProductCategory.productCategory;
		JPAQuery<ProductDetailResponse> query = jpaQueryFactory
				.select(Projections.bean(ProductDetailResponse.class, p.id, pc.name.as("categoryName"), p.salesYear,
						p.name, p.price, p.packUnit, p.spec, p.specUnit, p.supportDeliveryMode, p.status, p.remark,
						p.creater, p.createTime, p.updater, p.updateTime))
				.from(p).leftJoin(pc).on(p.categoryId.eq(pc.id));
		query.where(p.id.eq(id));

		ProductDetailResponse response = query.fetchOne();
		if (response == null) {
			throw new NotFoundException(this.messageSource.getMessage("data_not_found", new Object[] {
					this.messageSource.getMessage("product"), this.messageSource.getMessage("id"), id }));
		}
		return response;
	}

	@Override
	public PageResponse<ProductPageResponse> findProductByPage(Pageable pageable, ProductPageRequest request) {
		QProduct p = QProduct.product;
		QProductCategory pc = QProductCategory.productCategory;
		JPAQuery<ProductPageResponse> query = jpaQueryFactory
				.select(Projections.bean(ProductPageResponse.class, p.id, pc.name.as("categoryName"), p.salesYear,
						p.name, p.price, p.packUnit, p.spec, p.specUnit, p.supportDeliveryMode, p.status, p.remark))
				.from(p).leftJoin(pc).on(p.categoryId.eq(pc.id));
		if (StringUtils.hasText(request.getCategoryId())) {
			query.where(pc.id.eq(request.getCategoryId()));
		}
		if (request.getSalesYear() > 0) {
			query.where(p.salesYear.eq(request.getSalesYear()));
		}

		if (StringUtils.hasText(request.getName())) {
			query.where(p.name.like("%" + request.getName() + "%"));
		}
		if (StringUtils.hasText(request.getStatus())) {
			query.where(p.status.eq(request.getStatus()));
		}
		ProductPageResponse.setOrder(query, p, pc, pageable.getSort());

		QueryResults<ProductPageResponse> result = query.offset(pageable.getOffset()).limit(pageable.getPageSize())
				.fetchResults();
		return new PageResponse<ProductPageResponse>(result.getTotal(), result.getResults());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addProductCategory(ProductCategoryAddRequest request) throws BusinessException {

		this.hasSameCategoryName(request.getName());

		ProductCategory entity = new ProductCategory();
		entity.create(request.getName(), request.getRemark());
		this.productCategoryRepository.save(entity);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateProductCategory(ProductCategoryUpdateRequest request) throws BusinessException {

		ProductCategory entity = this.findCategoryById(request.getId());
		String name = request.getName();
		if (!entity.getName().equals(name)) {
			this.hasSameCategoryName(name);
		}

		entity.update(name, request.getRemark());
		this.productCategoryRepository.save(entity);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changeProductCategoryStatus(String id) throws BusinessException {

		ProductCategory entity = this.findCategoryById(id);
		entity.changeStatus();
		this.productCategoryRepository.save(entity);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteProductCategory(String id) throws BusinessException {

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
	public ProductCategoryDetailResponse findProductCategoryById(String id) throws BusinessException {

		return new ProductCategoryDetailResponse(this.findCategoryById(id));
	}

	@Override
	public List<TextValueObject> findProductCategoryByStatus(String status) {

		QProductCategory pc = QProductCategory.productCategory;

		JPAQuery<TextValueObject> query = jpaQueryFactory
				.select(Projections.bean(TextValueObject.class, pc.id.as("value"), pc.name.as("text"))).from(pc);

		if (StringUtils.hasText(status)) {
			query.where(pc.status.eq(status));
		}
		return query.orderBy(pc.name.asc()).fetch();
	}

	@Override
	public PageResponse<ProductCategoryPageResponse> findProductCategoryByPage(Pageable pageable,
			ProductCategoryPageRequest request) {

		QProductCategory pc = QProductCategory.productCategory;
		JPAQuery<ProductCategoryPageResponse> query = jpaQueryFactory
				.select(Projections.bean(ProductCategoryPageResponse.class, pc.id, pc.name, pc.status, pc.remark,
						pc.creater, pc.createTime, pc.updater, pc.updateTime))
				.from(pc);

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