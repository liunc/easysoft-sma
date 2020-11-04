package com.easysoft.sma.domain.service.impl;

import com.easysoft.lib.common.LocalMessageSource;
import com.easysoft.lib.common.exception.BadRequestException;
import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.common.exception.ConflictException;
import com.easysoft.lib.common.exception.GoneException;
import com.easysoft.lib.common.exception.NotFoundException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.dto.TextValueObject;
import com.easysoft.sma.domain.dto.CustomerAddRequest;
import com.easysoft.sma.domain.dto.CustomerAddressDetailResponse;
import com.easysoft.sma.domain.dto.CustomerAddressPageRequest;
import com.easysoft.sma.domain.dto.CustomerAddressPageResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryAddRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryDetailResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryPageRequest;
import com.easysoft.sma.domain.dto.CustomerCategoryPageResponse;
import com.easysoft.sma.domain.dto.CustomerCategoryUpdateRequest;
import com.easysoft.sma.domain.dto.CustomerDetailResponse;
import com.easysoft.sma.domain.dto.CustomerPageRequest;
import com.easysoft.sma.domain.dto.CustomerPageResponse;
import com.easysoft.sma.domain.dto.CustomerUpdateRequest;
import com.easysoft.sma.domain.dto.TransactionRecordPageResponse;
import com.easysoft.sma.domain.entity.Customer;
import com.easysoft.sma.domain.entity.CustomerCategory;
import com.easysoft.sma.domain.entity.QCustomer;
import com.easysoft.sma.domain.entity.QCustomerCategory;
import com.easysoft.sma.domain.repository.CustomerAddressRepository;
import com.easysoft.sma.domain.repository.CustomerCategoryRepository;
import com.easysoft.sma.domain.repository.CustomerRepository;
import com.easysoft.sma.domain.repository.SalesOrderRepository;
import com.easysoft.sma.domain.repository.TransactionRecordRepository;
import com.easysoft.sma.domain.service.CustomerService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerCategoryRepository customerCategoryRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private CustomerAddressRepository customerAddressRepository;
    
    @Autowired
    private TransactionRecordRepository transactionRecordRepository;
    
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private LocalMessageSource messageSource;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private CustomerCategory findCategoryById(String id) throws NotFoundException {

		return this.customerCategoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(this.messageSource.getMessage("data_not_found",
						new Object[] { this.messageSource.getMessage("customer_category"),
								this.messageSource.getMessage("id"), id })));
	}

	private void hasSameCategoryName(String name) throws ConflictException {
		if (this.customerCategoryRepository.existsByName(name)) {
			throw new ConflictException(this.messageSource.getMessage("data_exists", new Object[] {
					this.messageSource.getMessage("customer_category"), this.messageSource.getMessage("name"), name }));
		}
	}
	
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

    private Customer findById(String id) throws NotFoundException {

        return this.customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this.messageSource.getMessage("data_not_found", new Object[] {
                        this.messageSource.getMessage("customer"), this.messageSource.getMessage("id"), id })));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCustomer(CustomerAddRequest request) throws BusinessException {

        this.checkCategory(request.getCategoryId());

        this.hasSameWechatName(request.getWechatName());

        Customer customer = new Customer();
        customer.create(request.getCategoryId(), request.getWechatName(), request.getName(), request.getRemark());

        this.customerRepository.save(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomer(CustomerUpdateRequest request) throws BusinessException {

        Customer entity = this.findById(request.getId());
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
    public void deleteCustomer(String id) throws BusinessException {

        if (!this.customerRepository.existsById(id)) {
            this.messageSource.getMessage("data_not_found", new Object[] { this.messageSource.getMessage("customer"),
                    this.messageSource.getMessage("id"), id });
        }

        if (this.salesOrderRepository.existsByCustomerId(id)) {
            this.messageSource.getMessage("exists_sales_order");
        }

        this.transactionRecordRepository.deleteByCustomerId(id);
        this.customerAddressRepository.deleteByCustomerId(id);
        this.customerRepository.deleteById(id);

    }

    @Override
    public CustomerDetailResponse findCustomerById(String id) throws BusinessException {

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
    public PageResponse<CustomerPageResponse> findCustomerByPage(Pageable pageable, CustomerPageRequest request) {
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
    
    @Override
	@Transactional(rollbackFor = Exception.class)
	public void addCustomerCategory(CustomerCategoryAddRequest request) throws BusinessException {

		this.hasSameCategoryName(request.getName());

		CustomerCategory entity = new CustomerCategory();
		entity.create(request.getName(), request.getRemark());
		this.customerCategoryRepository.save(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateCustomerCategory(CustomerCategoryUpdateRequest request) throws BusinessException {

		CustomerCategory entity = this.findCategoryById(request.getId());
		String name = request.getName();
		if (!entity.getName().equals(name)) {
			this.hasSameCategoryName(name);
		}

		entity.update(name, request.getRemark());
		this.customerCategoryRepository.save(entity);
	}

    @Override
	@Transactional(rollbackFor = Exception.class)
	public void changeCustomerCategoryStatus(String id) throws BusinessException {

		CustomerCategory entity = this.findCategoryById(id);
		entity.changeStatus();
		this.customerCategoryRepository.save(entity);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteCustomerCategory(String id) throws BusinessException {

		if (!this.customerCategoryRepository.existsById(id)) {
			throw new GoneException(this.messageSource.getMessage("data_not_found", new Object[] {
					this.messageSource.getMessage("customer_category"), this.messageSource.getMessage("id"), id }));
		}

		boolean existsCustomer = this.customerRepository.existsByCategoryId(id);
		if (existsCustomer) {
			throw new ConflictException(this.messageSource.getMessage("exists_customer"));
		}

		this.customerCategoryRepository.deleteById(id);

	}

	@Override
	public CustomerCategoryDetailResponse findCustomerCategoryById(String id) throws BusinessException {

		return new CustomerCategoryDetailResponse(this.findCategoryById(id));
	}

	@Override
	public List<TextValueObject> findCustomerCategoryByStatus(String status) {
		QCustomerCategory pcc = QCustomerCategory.customerCategory;

		JPAQuery<TextValueObject> query = jpaQueryFactory
				.select(Projections.bean(TextValueObject.class, pcc.id.as("value"), pcc.name.as("text"))).from(pcc);

		if (StringUtils.hasText(status)) {
			query.where(pcc.status.eq(status));
		}
		return query.orderBy(pcc.name.asc()).fetch();
	}

	@Override
	public PageResponse<CustomerCategoryPageResponse> findCustomerCategoryByPage(Pageable pageable, CustomerCategoryPageRequest request) {

		QCustomerCategory cc = QCustomerCategory.customerCategory;
		JPAQuery<CustomerCategoryPageResponse> query = jpaQueryFactory
				.select(Projections.bean(CustomerCategoryPageResponse.class, cc.id, cc.name, cc.status, cc.remark)).from(cc);

		if (request != null) {
			if (StringUtils.hasText(request.getName())) {
				query.where(cc.name.like("%" + request.getName() + "%"));
			}
			if (StringUtils.hasText(request.getStatus())) {
				query.where(cc.status.eq(request.getStatus()));
			}
		}

		CustomerCategoryPageResponse.setOrder(query, cc, pageable.getSort());
		QueryResults<CustomerCategoryPageResponse> result = query.offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetchResults();
		return new PageResponse<CustomerCategoryPageResponse>(result.getTotal(), result.getResults());
	}
	
	 

	

	    

	    @Override
	    public void existsCustomer(String id) throws BusinessException {
	        if (!this.customerRepository.existsById(id)) {
	            throw new NotFoundException(this.messageSource.getMessage("data_not_found", new Object[] {
	                    this.messageSource.getMessage("customer"), this.messageSource.getMessage("id"), id }));
	        }
	    }

		@Override
		public void changeCustomerStatus(String id) throws BusinessException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public PageResponse<CustomerAddressPageResponse> findAddress(Pageable pageable,
				CustomerAddressPageRequest request) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<CustomerAddressDetailResponse> findAddress(String customerId, String category) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CustomerAddressDetailResponse findAddressById(String id) throws BusinessException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void createOrUpdateAddress(String customerId, String category, String linkman, String telephone,
				String address) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updateAddress(String id, String linkman, String telephone, String address)
				throws BusinessException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void deleteAddress(String id) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public PageResponse<TransactionRecordPageResponse> findTransactionRecordByPage(String wechatName, String name,
				String category, Pageable pageable) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void recharge(String customerId, String transactionSource, BigDecimal amount, String remark)
				throws BusinessException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void consume(String customerId, BigDecimal amount, String remark) throws BusinessException {
			// TODO Auto-generated method stub
			
		}


	    
	    /*
	    @Override
	    public QueryResults<CustomerAddressPageResponse> findAddress(String wechatName, String name, String linkman,
	            String telephone, Pageable pageable) {

	        QCustomerAddress qca = QCustomerAddress.customerAddress;
	        QCustomer qc = QCustomer.customer;
	        QCustomerCategory qcc = QCustomerCategory.customerCategory;
	        JPAQuery<CustomerAddressPageResponse> query = jpaQueryFactory
	                .select(Projections.bean(CustomerAddressPageResponse.class, qca.id, qc.id.as("customerId"),
	                        qcc.name.as("customerCategoryName"), qc.wechatName.as("customerWechatName"),
	                        qc.name.as("customerName"), qca.category, qca.linkman, qca.telephone, qca.address))
	                .from(qca).leftJoin(qc).on(qca.customerId.eq(qc.id)).leftJoin(qcc).on(qc.categoryId.eq(qcc.id));

	        if (StringUtils.hasText(wechatName)) {
	            query.where(qc.wechatName.like("%" + wechatName + "%"));
	        }
	        if (StringUtils.hasText(name)) {
	            query.where(qc.name.like("%" + name + "%"));
	        }
	        if (StringUtils.hasText(linkman)) {
	            query.where(qca.linkman.like("%" + linkman + "%"));
	        }

	        if (StringUtils.hasText(telephone)) {
	            query.where(qca.telephone.like("%" + telephone + "%"));
	        }

	        CustomerAddressPageResponse.setOrder(query, qca, qc, qcc, pageable.getSort());
	        return query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
	    }

	    @Override
	    public List<CustomerAddress> findAddress(String customerId, String category) {
	        return this.customerAddressRepository.findByCustomerIdAndCategory(customerId, category);
	    }

	    @Override
	    public CustomerAddress findAddress(String id) throws EasysoftException {
	        return this.customerAddressRepository.findById(id)
	                .orElseThrow(() -> new EasysoftException(this.messageSource.getMessage("data_not_found", new Object[] {
	                        this.messageSource.getMessage("customer_address"), this.messageSource.getMessage("id"), id })));
	    }

	    @Override
	    public void createOrUpdateAddress(String customerId, String category, String linkman, String telephone,
	            String address) {
	        CustomerAddress addressForSave = null;
	        for (CustomerAddress addressEntity : this.customerAddressRepository.findByCustomerIdAndCategory(customerId,
	                category)) {
	            if (linkman.equals(addressEntity.getLinkman()) && telephone.equals(addressEntity.getTelephone())) {
	                addressEntity.update(linkman, telephone, address);
	                addressForSave = addressEntity;
	                break;
	            }
	        }

	        if (addressForSave == null) {
	            addressForSave = new CustomerAddress();
	            addressForSave.create(customerId, category, linkman, telephone, address);
	        }
	        this.customerAddressRepository.save(addressForSave);
	    }

	    @Override
	    @Transactional(rollbackFor = Exception.class)
	    public void updateAddress(String id, String linkman, String telephone, String address) throws EasysoftException {

	        CustomerAddress entity = this.findAddress(id);

	        entity.update(linkman, telephone, address);
	        this.customerAddressRepository.save(entity);
	    }

	    @Override
	    public void deleteAddress(String id) {

	        this.customerAddressRepository.deleteById(id);
	    }

	    @Override
	    public QueryResults<TransactionRecordPageResponse> findTransactionRecord(String wechatName, String name, String source,
	            Pageable pageable) {
	        QTransactionRecord qtr = QTransactionRecord.transactionRecord;
	        QCustomer qc = QCustomer.customer;
	        QCustomerCategory qcc = QCustomerCategory.customerCategory;
	        JPAQuery<TransactionRecordPageResponse> query = jpaQueryFactory
	                .select(Projections.bean(TransactionRecordPageResponse.class, qcc.name.as("customerCategoryName"),
	                        qc.wechatName.as("customerWechatName"), qc.name.as("customerName"), qtr.category, qtr.source,
	                        qtr.amount, qtr.recordTime, qtr.remark))
	                .from(qtr).leftJoin(qc).on(qtr.customerId.eq(qc.id)).leftJoin(qcc).on(qc.categoryId.eq(qcc.id));

	        if (StringUtils.hasText(wechatName)) {
	            query.where(qc.wechatName.like("%" + wechatName + "%"));
	        }
	        if (StringUtils.hasText(name)) {
	            query.where(qc.name.like("%" + name + "%"));
	        }

	        if (StringUtils.hasText(source)) {
	            query.where(qtr.source.eq(source));
	        }

	        TransactionRecordPageResponse.setOrder(query, qtr, qc, qcc, pageable.getSort());
	        return query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
	    }

	    @Override
	    @Transactional(rollbackFor = Exception.class)
	    public void recharge(String customerId, String source, BigDecimal amount, String remark) throws EasysoftException {
	        
	        Customer customer = this.findCustomer(customerId);
	        customer.recharge(amount);

	        List<TransactionRecord> records = new ArrayList<TransactionRecord>(); 
	        TransactionRecord record = new TransactionRecord();
	        record.recharge(customerId, source, amount, remark);
	        records.add(record);

	        List<SalesOrder> salesOrders = this.salesOrderRepository.findByCustomerIdAndStatus(customerId, SalesOrderStatus.PENDING_COLLECTION);
	        if(salesOrders != null && salesOrders.size() > 0){ 
	            
	            List<SalesOrder> salesOrders1 = new ArrayList<SalesOrder>();
	            for(SalesOrder salesOrder : salesOrders){

	                if(!customer.hasBalance()){
	                    break;
	                }

	                BigDecimal amount1 = salesOrder.collection(customer.getBalance());
	                customer.consume(amount1);
	                salesOrders1.add(salesOrder);

	                TransactionRecord consumeRecord = new TransactionRecord();
	                consumeRecord.consume(customerId, amount1, "Order Id: " + salesOrder.getId());
	                records.add(consumeRecord);
	            }

	            if(salesOrders1.size() > 0){
	                this.salesOrderRepository.saveAll(salesOrders1);
	            }
	        }

	        this.customerRepository.save(customer);
	        this.transactionRecordRepository.saveAll(records);
	    }

	    @Override
	    @Transactional(rollbackFor = Exception.class)
	    public void consume(String customerId, BigDecimal amount, String remark) throws BusinessException {
	        Customer customer = this.findCustomer(customerId);
	        customer.consume(amount);
	        this.customerRepository.save(customer);

	        TransactionRecord record = new TransactionRecord();
	        record.consume(customerId, amount, remark);
	        this.transactionRecordRepository.save(record);
	    }
*/
}