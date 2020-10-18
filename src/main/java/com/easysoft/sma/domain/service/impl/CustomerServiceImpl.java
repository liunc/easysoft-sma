package com.easysoft.sma.domain.service.impl;

import com.easysoft.lib.common.LocalMessageSource;
import com.easysoft.lib.common.exception.BadRequestException;
import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.common.exception.ConflictException;
import com.easysoft.lib.common.exception.NotFoundException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.sma.domain.dto.CustomerAddRequest;
import com.easysoft.sma.domain.dto.CustomerDetailResponse;
import com.easysoft.sma.domain.dto.CustomerPageRequest;
import com.easysoft.sma.domain.dto.CustomerPageResponse;
import com.easysoft.sma.domain.dto.CustomerUpdateRequest;
import com.easysoft.sma.domain.entity.Customer;
import com.easysoft.sma.domain.entity.QCustomer;
import com.easysoft.sma.domain.entity.QCustomerCategory;
import com.easysoft.sma.domain.repository.CustomerCategoryRepository;
import com.easysoft.sma.domain.repository.CustomerRepository;
import com.easysoft.sma.domain.service.CustomerService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerCategoryRepository customerCategoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LocalMessageSource messageSource;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private void checkCategory(String id) throws BadRequestException {

        if (!this.customerCategoryRepository.existsById(id)) {
            throw new BadRequestException(this.messageSource.getMessage("data_not_found", new Object[] {
                    this.messageSource.getMessage("customer_category"), this.messageSource.getMessage("id"), id }));
        }
    }

    private void hasSameWechatName(String wechatName) throws ConflictException {
        if (this.customerRepository.existsByWechatName(wechatName)) {
            throw new ConflictException(this.messageSource.getMessage("data_exists",
                    new Object[] { this.messageSource.getMessage("customer"),
                            this.messageSource.getMessage("wechat_name"), wechatName }));
        }
    }

    private Customer findCustomer(String id) throws NotFoundException {

        return this.customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this.messageSource.getMessage("data_not_found", new Object[] {
                        this.messageSource.getMessage("customer"), this.messageSource.getMessage("id"), id })));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(CustomerAddRequest request) throws BusinessException {

        this.checkCategory(request.getCategoryId());

        this.hasSameWechatName(request.getWechatName());

        Customer customer = new Customer();
        customer.create(request.getCategoryId(), request.getWechatName(), request.getName(), request.getRemark());

        this.customerRepository.save(customer);

        return customer.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CustomerUpdateRequest request) throws BusinessException {

        Customer entity = this.findCustomer(request.getId());
        if (!entity.getCategoryId().equals(request.getCategoryId())) {
            this.checkCategory(request.getCategoryId());
        }

        if (!entity.getWechatName().equals(request.getWechatName())) {
            this.hasSameWechatName(request.getWechatName());
        }

        entity.update(request.getCategoryId(), request.getWechatName(), request.getName(), request.getRemark());
        this.customerRepository.save(entity);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws BusinessException {
        if (!this.customerRepository.existsById(id)) {
            this.messageSource.getMessage("data_not_found", new Object[] { this.messageSource.getMessage("customer"),
                    this.messageSource.getMessage("id"), id });
        }

        // if (this.salesOrderRepository.existsByCustomerId(id)) {
        // this.messageSource.getMessage("exists_sales_order");
        // }

        // this.transactionRecordRepository.deleteByCustomerId(id);
        // this.customerAddressRepository.deleteByCustomerId(id);
        this.customerRepository.deleteById(id);

    }

    @Override
    public CustomerDetailResponse find(String id) throws BusinessException {

        QCustomer qc = QCustomer.customer;
        QCustomerCategory qcc = QCustomerCategory.customerCategory;
        JPAQuery<CustomerDetailResponse> query = jpaQueryFactory.select(Projections.bean(CustomerDetailResponse.class,
                qc.id, qcc.name.as("categoryName"), qc.wechatName, qc.name, qc.status, qc.balance, qc.remark,
                qc.creater, qc.createTime, qc.updater, qc.updateTime)).from(qc).leftJoin(qcc)
                .on(qc.categoryId.eq(qcc.id)).where(qc.id.eq(id));

        CustomerDetailResponse response = query.fetchFirst();
        if (response == null) {
            throw new NotFoundException(this.messageSource.getMessage("data_not_found", new Object[] {
                    this.messageSource.getMessage("customer"), this.messageSource.getMessage("id"), id }));
        }
        return response;
    }

    @Override
    public PageResponse<CustomerPageResponse> page(Pageable pageable, CustomerPageRequest request) {
        QCustomer qc = QCustomer.customer;
        QCustomerCategory qcc = QCustomerCategory.customerCategory;
        JPAQuery<CustomerPageResponse> query = jpaQueryFactory.select(Projections.bean(CustomerPageResponse.class,
                qc.id, qcc.name.as("categoryName"), qc.wechatName, qc.name, qc.status, qc.balance, qc.remark)).from(qc)
                .leftJoin(qcc).on(qc.categoryId.eq(qcc.id));
        if (StringUtils.hasText(request.getCategoryId())) {
            query.where(qcc.id.eq(request.getCategoryId()));
        }
        if (StringUtils.hasText(request.getWechatName())) {
            query.where(qc.wechatName.like("%" + request.getWechatName() + "%"));
        }

        if (StringUtils.hasText(request.getName())) {
            query.where(qc.name.like("%" + request.getName() + "%"));
        }

        if (StringUtils.hasText(request.getStatus())) {
            query.where(qc.status.eq(request.getStatus()));
        }

        CustomerPageResponse.setOrder(query, qcc, qc, pageable.getSort());
        QueryResults<CustomerPageResponse> result = query.offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        return new PageResponse<CustomerPageResponse>(result.getTotal(), result.getResults());

    }

}