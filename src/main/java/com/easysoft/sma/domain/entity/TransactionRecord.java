package com.easysoft.sma.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.easysoft.sma.domain.valueobject.TransactionSource;
import com.easysoft.sma.domain.valueobject.TransactionCategory;

/**
 * 交易记录实体类
 * 
 * @author 刘年超
 */
@Entity
@Table(name = "transaction_record")
public class TransactionRecord {

	@Id
	@Column(columnDefinition = "CHAR(32) COMMENT 'ID, 主键'")
	private String id;

	@Column(name = "customer_id", columnDefinition = "CHAR(32) COMMENT '客户ID'", nullable = false)
	private String customerId;

	@Column(name = "category", columnDefinition = "CHAR(1) COMMENT '交易类型 1充值 2消费 3退款'", nullable = false)
	private String category;

	@Column(name = "source", columnDefinition = "CHAR(1) COMMENT '交易来源 1微信 2支付宝 3现金 4账户'", nullable = false)
	private String source;

	@Column(name = "record_time", columnDefinition = "DATETIME COMMENT '记录时间'", nullable = false)
	private Date recordTime;

	@Column(name = "amount", columnDefinition = "DECIMAL(10,2) COMMENT '金额'", nullable = false)
	private BigDecimal amount;

	@Column(columnDefinition = "VARCHAR(512) COMMENT '备注'")
	private String remark;

	public String getId() {
		return id;
	}

	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 充值
	 * @param customerId 客户ID
	 * @param source 交易来源
	 * @param amount 金额
	 * @param remark 备注
	 */
	public void recharge(String customerId, String source, BigDecimal amount, String remark) {
		this.create(customerId, TransactionCategory.RECHARGE, source, amount, remark);
	}

	/**
	 * 消费
	 * @param customerId 客户ID
	 * @param amount 金额
	 * @param remark 备注
	 */
	public void consume(String customerId, BigDecimal amount, String remark) {
		this.create(customerId, TransactionCategory.CONSUME, TransactionSource.ACCOUNT, amount, remark);
	}

	public void refund(String customerId, String source, BigDecimal amount, String remark) {
		this.create(customerId, TransactionCategory.REFUND, source, amount, remark);
	}
	
	private void create(String customerId, String category, String source, BigDecimal amount,
			String remark) {
		this.id = UUID.randomUUID().toString().replaceAll("-", "");
		this.customerId = customerId;
		this.category = category;
		this.source = source;
		this.recordTime = new Date();
		this.amount = amount;
		this.remark = remark;
	}
}
