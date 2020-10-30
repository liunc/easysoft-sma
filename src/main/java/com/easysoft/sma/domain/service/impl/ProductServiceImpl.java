package com.easysoft.sma.domain.service.impl;

import com.easysoft.lib.common.LocalMessageSource;
import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.common.exception.GoneException;
import com.easysoft.lib.common.exception.NotFoundException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.sma.domain.dto.ProductAddRequest;
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

    private Product findById(String id) throws NotFoundException {

        return this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this.messageSource.getMessage("data_not_found", new Object[] {
                        this.messageSource.getMessage("product"), this.messageSource.getMessage("id"), id })));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ProductAddRequest request) throws BusinessException {

        DeliveryMode.check(request.getSupportDeliveryMode());

        ProductCategory category = this.findCategoryById(request.getCategoryId());

        Product product = new Product();
        product.create(request.getCategoryId(), category.getName(), request.getSalesYear(), request.getPrice(),
                request.getPackUnit(), request.getSpec(), request.getSpecUnit(), request.getSupportDeliveryMode(),
                request.getRemark());

        this.productRepository.save(product);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductUpdateRequest request) throws BusinessException {

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
	public void changeStatus(String id) throws BusinessException {

		Product entity = this.findById(id);
		entity.changeStatus();
		this.productRepository.save(entity);
		
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws BusinessException {

        if (!this.productRepository.existsById(id)) {
            throw new GoneException(this.messageSource.getMessage("data_not_found", new Object[] {
                this.messageSource.getMessage("product"), this.messageSource.getMessage("id"), id }));
        }

        // something
        this.productRepository.deleteById(id);
    }

    @Override
    public ProductDetailResponse find(String id) throws BusinessException {

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
    public PageResponse<ProductPageResponse> page(Pageable pageable, ProductPageRequest request) {
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

}