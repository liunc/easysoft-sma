package com.easysoft.sma.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.easysoft.lib.jdb.domain.valueobject.ZeroOne;

/**
 * 客户实体类
 * 
 * @author 刘年超
 */
@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {

	private static final long serialVersionUID = -8116240182698898840L;

	@Column(name = "category_id", columnDefinition = "CHAR(32) COMMENT '分类ID'", nullable = false)
	private String categoryId;

	@Column(name = "wechat_name", columnDefinition = "VARCHAR(40) COMMENT '微信名'", nullable = false)
	private String wechatName;

	@Column(columnDefinition = "VARCHAR(40) COMMENT '姓名'", nullable = false)
	private String name;

	@Column(columnDefinition = "CHAR(1) COMMENT '状态 1有效 0无效'", nullable = false)
	private String status;

	@Column(columnDefinition = "VARCHAR(512) COMMENT '备注'")
	private String remark;

	@Column(columnDefinition = "DECIMAL(16,2) DEFAULT 0  COMMENT '余额'")
	private BigDecimal balance;

	/**
	 * @return the category
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @return the wechatName
	 */
	public String getWechatName() {
		return wechatName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	public void create(String categoryId, String wechatName, String name, String remark) {

		super.create();
		this.categoryId = categoryId;
		this.wechatName = wechatName;
		this.name = name;
		this.balance = BigDecimal.valueOf(0);
		this.remark = remark;
		this.status = ZeroOne.ONE;
	}

	public void update(String categoryId, String wechatName, String name, String remark) {

		this.categoryId = categoryId;
		this.wechatName = wechatName;
		this.name = name;
		this.remark = remark;
	}

	/**
	 * 充值
	 * 
	 * @param amount 充值的金额
	 */
	public void recharge(BigDecimal amount) {

		this.balance = this.balance.add(amount);
	}

	/**
	 * 消费
	 * 
	 * @param amount 消费金额
	 */
	public void consume(BigDecimal amount) {

		if (this.balance.compareTo(amount) == -1) {
			this.balance = BigDecimal.valueOf(0);
			return;
		}

		this.balance = this.balance.subtract(amount);
	}

	public void changeStatus() {
		if (ZeroOne.ONE.equals(this.status)) {
			this.status = ZeroOne.ZERO;
			return;
		}
		this.status = ZeroOne.ONE;
	}
	
	public boolean hasBalance() {
		return this.balance.compareTo(BigDecimal.valueOf(0)) > 0;
	}
}
