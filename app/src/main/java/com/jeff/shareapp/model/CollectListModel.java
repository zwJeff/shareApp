package com.jeff.shareapp.model;

// default package

import java.util.Date;

/**
 * CollectListModel entity. @author MyEclipse Persistence Tools
 */

public class CollectListModel implements java.io.Serializable {

	// Fields

	private Integer collectId;
	private Integer collectRecourceId;
	private Integer collectUserId;
	private Date collectTime;

	// Constructors

	/** default constructor */
	public CollectListModel() {
	}

	/** full constructor */
	public CollectListModel(Integer collectId, Integer collectRecourceId,
			Integer collectUserId, Date collectTime) {
		this.collectId = collectId;
		this.collectRecourceId = collectRecourceId;
		this.collectUserId = collectUserId;
		this.collectTime = collectTime;
	}

	// Property accessors

	public CollectListModel(int resource_id, int user_id,
			java.sql.Date currentSqlDate) {
		// TODO Auto-generated constructor stub
		this.collectRecourceId = resource_id;
		this.collectUserId = user_id;
		this.collectTime = currentSqlDate;
	}

	public Integer getCollectId() {
		return this.collectId;
	}

	public void setCollectId(Integer collectId) {
		this.collectId = collectId;
	}

	public Integer getCollectRecourceId() {
		return this.collectRecourceId;
	}

	public void setCollectRecourceId(Integer collectRecourceId) {
		this.collectRecourceId = collectRecourceId;
	}

	public Integer getCollectUserId() {
		return this.collectUserId;
	}

	public void setCollectUserId(Integer collectUserId) {
		this.collectUserId = collectUserId;
	}

	public Date getCollectTime() {
		return this.collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

}