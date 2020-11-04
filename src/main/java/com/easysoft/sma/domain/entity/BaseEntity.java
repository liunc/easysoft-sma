package com.easysoft.sma.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 实体基类
 * @EntityListeners(AuditingEntityListener.class): 声明实体监听器,用于实体修改时做处理
 * @MappedSuperclass: 声明该类为实体父类,不会映射单独的表,而是把字段映射到子类表中
 * 
 * @author 刘年超
 *
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -3913324620273016503L;

	@Id
	@Column(columnDefinition = "CHAR(32) COMMENT 'ID, 主键'", updatable = false)
	private String id;

	@CreatedBy
	@Column(columnDefinition = "VARCHAR(20) COMMENT '创建人'", nullable  = false, updatable = false)
	private String creater;

	@CreatedDate
	@Column(name = "create_time", columnDefinition = "DATETIME COMMENT '创建时间'", nullable  = false, updatable = false)
	private Date createTime;

	@LastModifiedBy
	@Column(columnDefinition = "VARCHAR(20) COMMENT '最后修改人'", nullable  = false)
	private String updater;

	@LastModifiedDate
	@Column(name = "update_time", columnDefinition = "DATETIME COMMENT '最后修改时间'", nullable  = false)
	private Date updateTime;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the creater
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @return the updater
	 */
	public String getUpdater() {
		return updater;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void create() {
		this.id = UUID.randomUUID().toString().replaceAll("-", "");
	}
}
