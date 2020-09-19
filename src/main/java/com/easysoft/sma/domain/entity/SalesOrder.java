package com.easysoft.sma.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.easysoft.lib.jdb.domain.valueobject.ZeroOne;
import com.easysoft.sma.domain.valueobject.DeliveryMode;
import com.easysoft.sma.domain.valueobject.SalesOrderStatus;

import org.springframework.util.StringUtils;

/**
 * 销售订单实体类
 */
@Entity
@Table(name = "sales_order")
public class SalesOrder extends BaseEntity {

	private static final long serialVersionUID = -8116240182698898840L;

	@Column(name = "customer_id", columnDefinition = "CHAR(32) COMMENT '客户ID'")
	private String customerId;

	@Column(name = "sender", columnDefinition = "VARCHAR(40) COMMENT '寄件人'", nullable = false)
	private String sender;

	@Column(name = "sender_telephone", columnDefinition = "VARCHAR(20)  COMMENT '寄件人电话'", nullable = false)
	private String senderTelephone;

	@Column(name = "sender_address", columnDefinition = "VARCHAR(120)  COMMENT '寄件人地址'", nullable = false)
	private String senderAddress;

	@Column(name = "consignee", columnDefinition = "VARCHAR(40)  COMMENT '收件人'", nullable = false)
	private String consignee;

	@Column(name = "consignee_telephone", columnDefinition = "VARCHAR(20) COMMENT '收件人电话'", nullable = false)
	private String consigneeTelephone;

	@Column(name = "consignee_address", columnDefinition = "VARCHAR(120) COMMENT '收件人地址'", nullable = false)
	private String consigneeAddress;

	@Column(name = "sequence_number", columnDefinition = "INT COMMENT '接龙号'", nullable = false)
	private int sequenceNumber;

	@Column(name = "amount_calculated", columnDefinition = "DECIMAL(10,2)  COMMENT '计算金额'", nullable = false)
	private BigDecimal amountCalculated;

	@Column(name = "amount_received", columnDefinition = "DECIMAL(10,2) COMMENT '实收金额'", nullable = false)
	private BigDecimal amountReceived;

	@Column(name = "discounted_amount", columnDefinition = "DECIMAL(10,2) COMMENT '折扣金额'", nullable = false)
	private BigDecimal discountedAmount;

	@Column(name = "schedule_date", columnDefinition = "DATETIME COMMENT '排单日期'")
	private Date scheduleDate;

	@Column(name = "delivery_mode", columnDefinition = "CHAR(1) COMMENT '交付方式'", nullable = false)
	private String deliveryMode;

	@Column(name = "status", columnDefinition = "CHAR(1) COMMENT '状态 1待排单 2已排单 3已发货 4待收款 5已完成 6已取消'", nullable = false)
	private String status;

	@Column(name = "delay", columnDefinition = "CHAR(1) COMMENT '是否延时排单 1 延时'")
	private String delay;

	@Column(name = "snapshot", columnDefinition = "VARCHAR(512) COMMENT '快照'", nullable = false)
	private String snapshot;

	@Column(name = "remark", columnDefinition = "VARCHAR(512) COMMENT '备注'", nullable = false)
	private String remark;

	/**
	 * 获取客户ID。
	 * 
	 * @return 客户ID。
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * 获取寄件人。
	 * 
	 * @return 寄件人。
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * 获取寄件人电话。
	 * 
	 * @return 返回寄件人电话。
	 */
	public String getSenderTelephone() {
		return senderTelephone;
	}

	/**
	 * 获取寄件人地址。
	 * 
	 * @return 返回寄件人地址。
	 */
	public String getSenderAddress() {
		return senderAddress;
	}

	/**
	 * 获取收件人。
	 * 
	 * @return 收件人。
	 */
	public String getConsignee() {
		return consignee;
	}

	/**
	 * 获取收件人电话。
	 * 
	 * @return 收件人电话。
	 */
	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}

	/**
	 * 获取收件人地址。
	 * 
	 * @return 收件人地址。
	 */
	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	/**
	 * 获取接龙号。
	 * 
	 * @return 接龙号。
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * 获取计算金额。
	 * 
	 * @return 计算金额。
	 */
	public BigDecimal getAmountCalculated() {
		return amountCalculated;
	}

	public void setAmountCalculated(BigDecimal amountCalculated) {
		this.amountCalculated = amountCalculated;
	}

	/**
	 * 获取实收金额。
	 * 
	 * @return 实收金额。
	 */
	public BigDecimal getAmountReceived() {
		return amountReceived;
	}

	public void setAmountReceived(BigDecimal amountReceived) {
		this.amountReceived = amountReceived;
	}

	/**
	 * 获取折扣金额。
	 * 
	 * @return 折扣金额。
	 */
	public BigDecimal getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(BigDecimal discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	/**
	 * 获取排单日期。
	 * 
	 * @return 排单日期。
	 */
	public Date getSchedulingDate() {
		return scheduleDate;
	}

	/**
	 * 获取交付方式。
	 * 
	 * @return 交付方式 1 快递 2 上门 3 自取。
	 */
	public String getDeliveryMode() {
		return deliveryMode;
	}

	/**
	 * 获取状态。
	 * 
	 * @return 状态 1 待排单 2 已排单 3 已发货 4 待收款 5 已完成 6 已取消。
	 */
	public String getStatus() {
		return status;
	}

	public String getDelay() {
		return delay;
	}

	/**
	 * 获取快照。
	 * 
	 * @return 快照，包含产品的名称、数量、金额等信息。
	 */
	public String getSnapshot() {
		return this.snapshot;
	}

	/**
	 * 获取备注。
	 * 
	 * @return 备注。
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 创建订单。
	 * 
	 * @param customerId         客户ID
	 * @param sender             寄件人
	 * @param senderTelephone    寄件人电话
	 * @param senderAddress      寄件人地址
	 * @param consignee          收件人
	 * @param consigneeTelephone 收件人电话
	 * @param consigneeAddress   收件人地址
	 * @param sequenceNumber     接龙号
	 * @param amountCalculated   计算金额
	 * @param discountedAmount   折扣金额
	 * @param deliveryMode       交付方式
	 * @param snapshot           快照
	 * @param delay              是否延迟发货
	 * @param remark             备注
	 */
	public void create(String customerId, String sender, String senderTelephone, String senderAddress, String consignee,
			String consigneeTelephone, String consigneeAddress, int sequenceNumber, BigDecimal amountCalculated,
			BigDecimal discountedAmount, String deliveryMode, String snapshot, String delay, String remark) {

		this.customerId = customerId;
		this.sender = sender;
		this.senderTelephone = senderTelephone;
		this.senderAddress = senderAddress;
		this.consignee = consignee;
		this.consigneeTelephone = consigneeTelephone;
		this.consigneeAddress = consigneeAddress;
		this.sequenceNumber = sequenceNumber;
		this.amountCalculated = amountCalculated;
		this.amountReceived = BigDecimal.valueOf(0);
		this.discountedAmount = discountedAmount;
		this.deliveryMode = deliveryMode;
		this.status = SalesOrderStatus.WAITING_SCHEDULE;
		this.snapshot = snapshot;
		this.delay = ZeroOne.get(delay);
		this.remark = remark;
	}

	/**
	 * 更新订单。
	 * 
	 * @param sender             寄件人
	 * @param senderTelephone    寄件人电话
	 * @param senderAddress      寄件人地址
	 * @param consignee          收件人
	 * @param consigneeTelephone 收件人电话
	 * @param consigneeAddress   收件人地址
	 * @param sequenceNumber     接龙号
	 * @param amountCalculated   计算金额
	 * @param discountedAmount   折扣金额
	 * @param deliveryMode       交付方式
	 * @param snapshot           快照
	 * @param delay              是否延迟发货
	 * @param remark             备注
	 */
	public void update(String sender, String senderTelephone, String senderAddress, String consignee,
			String consigneeTelephone, String consigneeAddress, int sequenceNumber, BigDecimal amountCalculated,
			BigDecimal discountedAmount, String deliveryMode, String snapshot, String delay, String remark) {
		this.sender = sender;
		this.senderTelephone = senderTelephone;
		this.senderAddress = senderAddress;
		this.consignee = consignee;
		this.consigneeTelephone = consigneeTelephone;
		this.consigneeAddress = consigneeAddress;
		this.sequenceNumber = sequenceNumber;
		this.amountCalculated = amountCalculated;
		this.discountedAmount = discountedAmount;
		this.deliveryMode = deliveryMode;
		this.snapshot = snapshot;
		this.delay = ZeroOne.get(delay);
		this.remark = remark;
	}

	/**
	 * 是否缺少收件人信息
	 * 
	 * @return
	 */
	public boolean missingConsignee() {
		if (DeliveryMode.EXPRESS.equals(this.deliveryMode) || DeliveryMode.HOME.equals(this.deliveryMode)) {
			if (!StringUtils.hasText(this.consignee) || !StringUtils.hasText(this.consigneeTelephone)
					|| !StringUtils.hasText(this.consigneeAddress)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 排单。
	 * 
	 * @param scheduleDate 排单日期
	 */
	public void schedule(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
		this.status = SalesOrderStatus.SCHEDULED;
	}

	/**
	 * 撤回排单
	 */
	public void undoSchedule() {
		this.scheduleDate = null;
		this.status = SalesOrderStatus.WAITING_SCHEDULE;
	}

	/**
	 * 确认发货。
	 */
	public void placeOrder() {
		this.status = SalesOrderStatus.ORDERED;
	}

	/**
	 * 是否已收款。
	 * 
	 * @return 计算金额等于实收金额加折扣金额时返回true，否则false。
	 */
	/*
	 * public boolean isReceived() { return this.amountCalculated ==
	 * this.amountReceived.add(this.discountedAmount); }
	 */

	/**
	 * 收款。
	 * 
	 * @param amount 付款金额
	 * @return 付款金额大于等于实收金额时返回实收金额，否则返回付款金额。
	 */
	public BigDecimal collection(BigDecimal amount) {
		BigDecimal amountReceive = this.amountCalculated.subtract(this.discountedAmount);
		amountReceive = amountReceive.subtract(this.amountReceived);
		int result = amountReceive.compareTo(amount);
		if (result <= 0) {
			this.amountReceived = amountReceive.add(this.amountReceived);
			this.status = SalesOrderStatus.COMPLETED;
			return amountReceive;
		}
		this.amountReceived = amount.add(this.amountReceived);
		this.status = SalesOrderStatus.PENDING_COLLECTION;
		return amount;
	}

	/**
	 * 取消。
	 */
	public void cancel() {
		this.status = SalesOrderStatus.CANCELED;
	}
}
