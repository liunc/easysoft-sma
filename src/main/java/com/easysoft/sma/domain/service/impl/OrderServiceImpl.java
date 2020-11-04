package com.easysoft.sma.domain.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import com.easysoft.lib.common.LocalMessageSource;
import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.common.exception.GoneException;
import com.easysoft.lib.jdb.domain.dto.PageResponse;
import com.easysoft.lib.jdb.domain.valueobject.ZeroOne;
import com.easysoft.sma.CustomProperties;
import com.easysoft.sma.domain.dto.ProductCategoryPageResponse;
import com.easysoft.sma.domain.dto.SalesOrderPageResponse;
import com.easysoft.sma.domain.dto.SalesOrderProductAddRequest;
import com.easysoft.sma.domain.dto.SalesOrderProductItem;
import com.easysoft.sma.domain.dto.SalesOrderScheduledExportResponse;
import com.easysoft.sma.domain.entity.Customer;
import com.easysoft.sma.domain.entity.CustomerAddress;
import com.easysoft.sma.domain.entity.Product;
import com.easysoft.sma.domain.entity.QCustomer;
import com.easysoft.sma.domain.entity.QCustomerCategory;
import com.easysoft.sma.domain.entity.QProduct;
import com.easysoft.sma.domain.entity.QProductCategory;
import com.easysoft.sma.domain.entity.QSalesOrder;
import com.easysoft.sma.domain.entity.QSalesOrderProduct;
import com.easysoft.sma.domain.entity.SalesOrder;
import com.easysoft.sma.domain.entity.SalesOrderProduct;
import com.easysoft.sma.domain.service.CustomerService;
import com.easysoft.sma.domain.service.ExcelService;
import com.easysoft.sma.domain.service.OrderService;
import com.easysoft.sma.domain.valueobject.AddressCategory;
import com.easysoft.sma.domain.valueobject.DeliveryMode;
import com.easysoft.sma.domain.valueobject.SalesOrderStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.easysoft.sma.domain.repository.CustomerAddressRepository;
import com.easysoft.sma.domain.repository.CustomerRepository;
import com.easysoft.sma.domain.repository.ProductRepository;
import com.easysoft.sma.domain.repository.SalesOrderProductRepository;
import com.easysoft.sma.domain.repository.SalesOrderRepository;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerService customerService;


    @Autowired
    private CustomerAddressRepository customerAddressRepository;
    
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SalesOrderProductRepository salesOrderProductRepository;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private LocalMessageSource messageSource;

    @Autowired
    private CustomProperties properties;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public PageResponse<SalesOrderPageResponse> findSalesOrder(String wechatName, String name, String consignee,
            String consigneeTelephone, String deliveryMode, String delay, String status, Pageable pageable) {

        QSalesOrder qso = QSalesOrder.salesOrder;
        QCustomer qc = QCustomer.customer;
        QCustomerCategory qcc = QCustomerCategory.customerCategory;
        JPAQuery<SalesOrderPageResponse> query = jpaQueryFactory
                .select(Projections.bean(SalesOrderPageResponse.class, qso.id, qc.id.as("customerId"),
                        qcc.name.as("customerCategoryName"), qc.wechatName.as("customerWechatName"),
                        qc.name.as("customerName"), qso.sender, qso.senderTelephone, qso.senderAddress, qso.consignee,
                        qso.consigneeTelephone, qso.consigneeAddress, qso.sequenceNumber, qso.amountCalculated,
                        qso.amountReceived, qso.discountedAmount, qso.scheduleDate, qso.createTime.as("orderTime"),
                        qso.deliveryMode, qso.status, qso.snapshot, qso.remark))
                .from(qso).leftJoin(qc).on(qso.customerId.eq(qc.id)).leftJoin(qcc).on(qc.categoryId.eq(qcc.id));

        if (StringUtils.hasText(wechatName)) {
            query.where(qc.wechatName.like("%" + wechatName + "%"));
        }
        if (StringUtils.hasText(name)) {
            query.where(qc.name.like("%" + name + "%"));
        }
        if (StringUtils.hasText(consignee)) {
            query.where(qso.consignee.like("%" + consignee + "%"));
        }
        if (StringUtils.hasText(consigneeTelephone)) {
            query.where(qso.consigneeTelephone.like("%" + consigneeTelephone + "%"));
        }
        if (StringUtils.hasText(deliveryMode)) {
            query.where(qso.deliveryMode.eq(deliveryMode));
        }
        if (ZeroOne.ONE.equalsIgnoreCase(delay)) {
            query.where(qso.delay.eq(ZeroOne.get(delay)));
        }
        else{
            query.where(qso.delay.isNull());
        }
        if (StringUtils.hasText(status)) {
            query.where(qso.status.eq(status));
        }

        SalesOrderPageResponse.setOrder(query, qso, qc, qcc, pageable.getSort());
        
        QueryResults<SalesOrderPageResponse> result = query.offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetchResults();
		return new PageResponse<SalesOrderPageResponse>(result.getTotal(), result.getResults());
    }
/*
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(String customerId, String sender, String senderTelephone, String senderAddress,
                        String consignee, String consigneeTelephone, String consigneeAddress, int sequenceNumber,
                        BigDecimal amountCalculated, BigDecimal discountedAmount, String deliveryMode, String delay, String remark,
                        List<SalesOrderProductAddRequest> products) throws BusinessException {

    	if (!this.customerRepository.existsById(customerId)) {
			throw new GoneException(this.messageSource.getMessage("data_not_found", new Object[] {
					this.messageSource.getMessage("customer"), this.messageSource.getMessage("id"), customerId }));
		}

        SalesOrder entity = new SalesOrder();
        entity.create();

        String snapshot = "";
        List<String> productSnapshot = new ArrayList<String>();
        List<SalesOrderProduct> salesOrderProducts = new ArrayList<SalesOrderProduct>();
        for (SalesOrderProductAddRequest product : products) {
            Product productEntity = this.productRepository.findById(product.getProductId())
                    .orElseThrow(() -> new GoneException(this.messageSource.getMessage("data_not_found",
                            new Object[] { this.messageSource.getMessage("product"),
                                    this.messageSource.getMessage("id"), product.getProductId() })));
            SalesOrderProduct salesOrderProduct = new SalesOrderProduct();
            salesOrderProduct.create(productEntity, entity.getId(), product.getActualPrice(), product.getQuantity());
            salesOrderProducts.add(salesOrderProduct);
            productSnapshot.add(String.format("%s%s%s %s*%s=%s%s", productEntity.getName(),
                    salesOrderProduct.getQuantity(), productEntity.getPackUnit(), productEntity.getPrice(),
                    salesOrderProduct.getQuantity(), productEntity.getPrice().multiply(salesOrderProduct.getQuantity()),
                    this.messageSource.getMessage("yuan")));
        }
        snapshot = String.join(",", productSnapshot);

        if (StringUtils.hasText(sender) && StringUtils.hasText(senderTelephone)) {
            this.customerService.createOrUpdateAddress(customerId, AddressCategory.SENDER, sender, senderTelephone,
                    senderAddress);
        }

        if (StringUtils.hasText(consignee) && StringUtils.hasText(consigneeTelephone)
                && StringUtils.hasText(consigneeAddress)) {
            this.customerService.createOrUpdateAddress(customerId, AddressCategory.CONSIGNEE, consignee,
                    consigneeTelephone, consigneeAddress);
        }

        entity.create(customerId, sender, senderTelephone, senderAddress, consignee, consigneeTelephone,
                consigneeAddress, sequenceNumber, amountCalculated, discountedAmount, deliveryMode, snapshot, delay, remark);

        this.salesOrderRepository.save(entity);

        this.salesOrderProductRepository.saveAll(salesOrderProducts);
    }

    @Override
    public SalesOrder findOrder(String id) throws EasysoftException {
        return this.salesOrderRepository.findById(id)
                .orElseThrow(() -> new BusinessException(this.messageSource.getMessage("data_not_found", new Object[] {
                        this.messageSource.getMessage("order"), this.messageSource.getMessage("id"), id })));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String id, String sender, String senderTelephone, String senderAddress, String consignee,
            String consigneeTelephone, String consigneeAddress, int sequenceNumber, BigDecimal amountCalculated,
            BigDecimal discountedAmount, String deliveryMode, String delay, String remark,
            List<KeyValueObject<String, BigDecimal>> products) throws EasysoftException {

        SalesOrder entity = this.findOrder(id);

        this.salesOrderProductRepository.deleteByOrderId(id);
        String snapshot = "";
        List<String> productSnapshot = new ArrayList<String>();
        List<SalesOrderProduct> salesOrderProducts = new ArrayList<SalesOrderProduct>();
        for (KeyValueObject<String, BigDecimal> product : products) {
            Product productEntity = this.productRepository.findById(product.getKey())
                    .orElseThrow(() -> new EasysoftException(this.messageSource.getMessage("data_not_found",
                            new Object[] { this.messageSource.getMessage("product"),
                                    this.messageSource.getMessage("id"), product.getKey() })));

            SalesOrderProduct salesOrderProduct = new SalesOrderProduct();
            salesOrderProduct.create(productEntity, entity.getId(), product.getValue());
            salesOrderProducts.add(salesOrderProduct);
            productSnapshot.add(String.format("%s%s%s %s*%s=%s%s", productEntity.getName(),
                    salesOrderProduct.getQuantity(), productEntity.getPackUnit(), productEntity.getPrice(),
                    salesOrderProduct.getQuantity(), productEntity.getPrice().multiply(salesOrderProduct.getQuantity()),
                    this.messageSource.getMessage("yuan")));
        }
        snapshot = String.join(",", productSnapshot);

        if (StringUtils.hasText(sender) && StringUtils.hasText(senderTelephone)) {
            this.customerService.createOrUpdateAddress(entity.getCustomerId(), AddressCategory.SENDER, sender,
                    senderTelephone, senderAddress);
        }

        if (StringUtils.hasText(consignee) && StringUtils.hasText(consigneeTelephone)
                && StringUtils.hasText(consigneeAddress)) {
            this.customerService.createOrUpdateAddress(entity.getCustomerId(), AddressCategory.CONSIGNEE, consignee,
                    consigneeTelephone, consigneeAddress);
        }

        entity.update(sender, senderTelephone, senderAddress, consignee, consigneeTelephone, consigneeAddress,
                sequenceNumber, amountCalculated, discountedAmount, deliveryMode, snapshot, delay, remark);

        this.salesOrderRepository.save(entity);

        this.salesOrderProductRepository.saveAll(salesOrderProducts);

    }

    @Override
    public List<SalesOrderProductItem> findSalesOrderProduct(String orderId) {
        QProduct p = QProduct.product;
        QProductCategory pc = QProductCategory.productCategory;
        QSalesOrderProduct sop = QSalesOrderProduct.salesOrderProduct;
        JPAQuery<SalesOrderProductItem> query = jpaQueryFactory
                .select(Projections.bean(SalesOrderProductItem.class, p.id, pc.name.as("categoryName"), p.salesYear,
                        p.name, p.price, p.packUnit, p.spec, p.specUnit, p.supportDeliveryMode, sop.quantity))
                .from(p).leftJoin(pc).on(p.categoryId.eq(pc.id)).leftJoin(sop)
                .on(p.id.eq(sop.productId).and(sop.orderId.eq(orderId))).where(p.available.eq(Available.YES));
        List<SalesOrderProductItem> results = query.orderBy(p.name.asc()).fetch();
        query = jpaQueryFactory
                .select(Projections.bean(SalesOrderProductItem.class, sop.productId, pc.name.as("categoryName"),
                        sop.salesYear, sop.name, sop.price, sop.packUnit, sop.spec, sop.specUnit, p.supportDeliveryMode,
                        sop.quantity))
                .from(sop).leftJoin(pc).on(sop.categoryId.eq(pc.id)).leftJoin(p).on(sop.productId.eq(p.id))
                .where(sop.orderId.eq(orderId).and(p.available.eq(Available.NO)));
        results.addAll(query.orderBy(p.name.asc()).fetch());
        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws EasysoftException {

        if (!this.salesOrderRepository.existsById(id)) {
            this.messageSource.getMessage("data_not_found",
                    new Object[] { this.messageSource.getMessage("order"), this.messageSource.getMessage("id"), id });
        }

        this.salesOrderProductRepository.deleteByOrderId(id);
        this.salesOrderRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(String id) throws EasysoftException {

        SalesOrder salesOrder = this.findOrder(id);
        salesOrder.cancel();
        this.salesOrderRepository.save(salesOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void schedule(Date scheduleDate, List<String> orderIds) throws EasysoftException {

        /* if (scheduleDate.before(new Date())) {
            throw new EasysoftException(this.messageSource.getMessage("schedule_date_invalid"));
        } *//*
        List<SalesOrder> entities = new ArrayList<SalesOrder>();
        for (String id : orderIds) {
            SalesOrder entity = this.findOrder(id);
            if (entity.missingConsignee()) {
                Customer customer = this.customerService.findCustomer(entity.getCustomerId());
                throw new EasysoftException(this.messageSource.getMessage("missing_consignee_information",
                        new Object[] { customer.getWechatName() }));
            }
            entity.schedule(scheduleDate);
        }

        this.salesOrderRepository.saveAll(entities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void undoSchedule(String id) throws EasysoftException {
  
            SalesOrder entity = this.findOrder(id);
            entity.undoSchedule(); 

        this.salesOrderRepository.save(entity);
    }

    @Override
    public String exportScheduled(String wechatName, String consigneeTelephone, String deliveryMode) {

        int size = this.properties.getExportPageSize();

        SXSSFWorkbook workbook = this.excelService.createWorkbook(this.properties.getExcelRowAccessWindowSize());
        SXSSFSheet sheet = this.excelService.createSheet(workbook,
                this.messageSource.getMessage("delivery_mode_" + DeliveryMode.EXPRESS));

        List<String> header = Arrays.asList(this.messageSource.getMessage("serial_number"),
                this.messageSource.getMessage("consignee"), this.messageSource.getMessage("consignee_telephone"),
                this.messageSource.getMessage("consignee_address"), this.messageSource.getMessage("product"),
                this.messageSource.getMessage("single_weight"), this.messageSource.getMessage("number_of_packages"),
                this.messageSource.getMessage("sender"), this.messageSource.getMessage("sender_telephone"),
                this.messageSource.getMessage("sender_address"),
                this.messageSource.getMessage("remark"));
        this.excelService.createRow(sheet, 0, header);

        List<SalesOrderScheduledExportResponse> mergeEntities = new ArrayList<SalesOrderScheduledExportResponse>();
        int pageIndex = 0;
        int rowIndex = 1;
        while (true) {
            QueryResults<SalesOrderScheduledExportResponse> entities = this.findSalesOrderScheduled(wechatName,
                    consigneeTelephone, deliveryMode, SalesOrderStatus.SCHEDULED, PageRequest.of(pageIndex, size));
            if (entities.isEmpty()) {
                break;
            }
            for (SalesOrderScheduledExportResponse entity : entities.getResults()) {
                // 快递并且收件人不是默认收件人时，产生一条记录，不用与其它记录进行合并。
                if (DeliveryMode.EXPRESS.equals(entity.getDeliveryMode())
                        && !this.properties.getDefaultConsigneeTelephone().equals(entity.getConsigneeTelephone())) {
                    List<String> values = Arrays.asList(String.valueOf(rowIndex), entity.getConsignee(),
                            entity.getConsigneeTelephone(), entity.getConsigneeAddress(), entity.getProductCategory(),
                            String.format("%s%s", 
                            String.valueOf(entity.getSpec().stripTrailingZeros()),
                            entity.getSpecUnit()),
                            String.valueOf(entity.getQuantity().stripTrailingZeros()),
                            StringUtils.hasText(entity.getSender()) ? entity.getSender()
                                    : this.properties.getDefaultSender(),
                            StringUtils.hasText(entity.getSenderTelephone()) ? entity.getSenderTelephone()
                                    : this.properties.getDefaultSenderTelephone(),
                            StringUtils.hasText(entity.getSenderAddress()) ? entity.getSenderAddress()
                                    : this.properties.getDefaultSenderAddress(),
                                    entity.getRemark());
                    this.excelService.createRow(sheet, rowIndex, values);
                    rowIndex++;
                    continue;
                }

                // 上门或自取，收件人都是默认收件人，只需要按产品不同，数量上进行累加，备注也要累加。
                Optional<SalesOrderScheduledExportResponse> optional = mergeEntities.stream()
                        .filter(item -> item.getProductId().equals(entity.getProductId())).findFirst();
                if (optional.isPresent()) {
                    SalesOrderScheduledExportResponse dto = optional.get();
                    dto.setQuantity(dto.getQuantity().add(entity.getQuantity()));
                } else {
                    SalesOrderScheduledExportResponse dto = new SalesOrderScheduledExportResponse();
                    if (DeliveryMode.EXPRESS.equals(entity.getDeliveryMode())) {
                        dto.setConsignee(entity.getConsignee());
                        dto.setConsigneeTelephone(entity.getConsigneeTelephone());
                        dto.setConsigneeAddress(entity.getConsigneeAddress());
                    } else {
                        dto.setConsignee(this.properties.getDefaultConsignee());
                        dto.setConsigneeTelephone(this.properties.getDefaultConsigneeTelephone());
                        dto.setConsigneeAddress(this.properties.getDefaultConsigneeAddress());
                    }
                    dto.setSpec(entity.getSpec());
                    dto.setSpecUnit(entity.getSpecUnit());
                    dto.setQuantity(entity.getQuantity());
                    dto.setSender(entity.getSender());
                    dto.setSenderTelephone(entity.getSenderTelephone());
                    dto.setSenderAddress(entity.getSenderAddress());
                    dto.setDeliveryMode(entity.getDeliveryMode());
                    dto.setProductId(entity.getProductId());
                    dto.setProductCategory(entity.getProductCategory());

                    if(StringUtils.hasText(entity.getRemark())){
                        dto.setRemark(dto.getRemark() + String.valueOf(entity.getQuantity().stripTrailingZeros()) + entity.getPackUnit() + entity.getRemark());
                    }

                    mergeEntities.add(dto);
                }

            }
            pageIndex++;
        }

        for (SalesOrderScheduledExportResponse entity : mergeEntities) {
            List<String> values = Arrays.asList(String.valueOf(rowIndex), entity.getConsignee(),
                    entity.getConsigneeTelephone(), entity.getConsigneeAddress(), entity.getProductCategory(),
                    String.format("%s%s", String.valueOf(entity.getSpec().stripTrailingZeros()), entity.getSpecUnit()), 
                    String.valueOf(entity.getQuantity().stripTrailingZeros()),
                    StringUtils.hasText(entity.getSender()) ? entity.getSender() : this.properties.getDefaultSender(),
                    StringUtils.hasText(entity.getSenderTelephone()) ? entity.getSenderTelephone()
                            : this.properties.getDefaultSenderTelephone(),
                    StringUtils.hasText(entity.getSenderAddress()) ? entity.getSenderAddress()
                            : this.properties.getDefaultSenderAddress(),
                    entity.getRemark());
            this.excelService.createRow(sheet, rowIndex, values);
            rowIndex++;
        }

        String fileName = this.excelService.generateFileName();
        this.excelService.saveFile(workbook, fileName);
        return fileName;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void placeOrder(List<String> orderIds) throws EasysoftException {

        List<SalesOrder> entities = new ArrayList<SalesOrder>();
        for (String id : orderIds) {
            SalesOrder entity = this.findOrder(id);
            entity.placeOrder();
        }

        this.salesOrderRepository.saveAll(entities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receive(List<String> orderIds) throws EasysoftException {

        List<SalesOrder> entities = new ArrayList<SalesOrder>();
        for (String id : orderIds) {
            SalesOrder entity = this.findOrder(id);
            Customer customer = this.customerService.findCustomer(entity.getCustomerId());
            BigDecimal amount = entity.collection(customer.getBalance());
            if (amount.compareTo(BigDecimal.valueOf(0)) == 1) {
                this.customerService.consume(entity.getCustomerId(), amount, "Order Id: " + id);
            }
        }

        this.salesOrderRepository.saveAll(entities);
    }

    private QueryResults<SalesOrderScheduledExportResponse> findSalesOrderScheduled(String wechatName,
            String consigneeTelephone, String deliveryMode, String status, Pageable pageable) {

        QCustomer qc = QCustomer.customer;
        QSalesOrder qso = QSalesOrder.salesOrder;
        QSalesOrderProduct qsop = QSalesOrderProduct.salesOrderProduct;
        QProductCategory qpc = QProductCategory.productCategory;
        JPAQuery<SalesOrderScheduledExportResponse> query = jpaQueryFactory
                .select(Projections.bean(SalesOrderScheduledExportResponse.class, qso.sender, qso.senderTelephone,
                        qso.senderAddress, qso.consignee, qso.consigneeTelephone, qso.consigneeAddress, qsop.spec,
                        qsop.specUnit, qsop.quantity, qsop.packUnit,
                        qso.deliveryMode, qsop.productId, qpc.name.as("productCategory"),
                        qso.remark))
                .from(qso).leftJoin(qsop).on(qso.id.eq(qsop.orderId)).leftJoin(qc).on(qso.customerId.eq(qc.id))
                .leftJoin(qpc).on(qsop.categoryId.eq(qpc.id));
        query.orderBy(qso.sequenceNumber.asc());

        if (StringUtils.hasText(wechatName)) {
            query.where(qc.wechatName.like("%" + wechatName + "%"));
        }
        if (StringUtils.hasText(consigneeTelephone)) {
            query.where(qso.consigneeTelephone.like("%" + consigneeTelephone + "%"));
        }
        if (StringUtils.hasText(deliveryMode)) {
            query.where(qso.deliveryMode.eq(deliveryMode));
        }
        if (StringUtils.hasText(status)) {
            query.where(qso.status.eq(status));
        }

        return query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
    }
    
    private void createOrUpdateAddress(String customerId, String category, String linkman, String telephone,
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
*/

	@Override
	public void create(String customerId, String sender, String senderTelephone, String senderAddress, String consignee,
			String consigneeTelephone, String consigneeAddress, int sequenceNumber, BigDecimal amountCalculated,
			BigDecimal discountedAmount, String deliveryMode, String delay, String remark,
			List<SalesOrderProductAddRequest> products) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SalesOrder findOrder(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(String id, String sender, String senderTelephone, String senderAddress, String consignee,
			String consigneeTelephone, String consigneeAddress, int sequenceNumber, BigDecimal amountCalculated,
			BigDecimal discountedAmount, String deliveryMode, String delay, String remark,
			List<SalesOrderProductAddRequest> products) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SalesOrderProductItem> findSalesOrderProduct(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel(String id) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedule(Date scheduleDate, List<String> orderIds) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undoSchedule(String id) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String exportScheduled(String wechatName, String consigneeTelephone, String deliveryMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void placeOrder(List<String> orderIds) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receive(List<String> orderIds) throws BusinessException {
		// TODO Auto-generated method stub
		
	}
}