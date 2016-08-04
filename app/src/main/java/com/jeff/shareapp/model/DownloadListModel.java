package com.jeff.shareapp.model;

// default package

import java.util.Date;

/**
 * DownloadListModel entity. @author MyEclipse Persistence Tools
 */

public class DownloadListModel implements java.io.Serializable {

	// Fields

	private Integer downloadId;
	private Integer downloadRecourceId;
	private Integer downloadUserId;
	private Date downloadTime;

	// Constructors

	/** default constructor */
	public DownloadListModel() {
	}

	/** full constructor */
	public DownloadListModel(Integer downloadId, Integer downloadRecourceId,
			Integer downloadUserId, Date downloadTime) {
		this.downloadId = downloadId;
		this.downloadRecourceId = downloadRecourceId;
		this.downloadUserId = downloadUserId;
		this.downloadTime = downloadTime;
	}

	// Property accessors

	public DownloadListModel(String user_id, String resource_id,
			java.sql.Date currentSqlDate) {
		// TODO Auto-generated constructor stub
		this.downloadRecourceId =Integer.parseInt(resource_id);
		this.downloadUserId = Integer.parseInt(user_id);
		this.downloadTime = currentSqlDate;
	}

	public Integer getDownloadId() {
		return this.downloadId;
	}

	public void setDownloadId(Integer downloadId) {
		this.downloadId = downloadId;
	}

	public Integer getDownloadRecourceId() {
		return this.downloadRecourceId;
	}

	public void setDownloadRecourceId(Integer downloadRecourceId) {
		this.downloadRecourceId = downloadRecourceId;
	}

	public Integer getDownloadUserId() {
		return this.downloadUserId;
	}

	public void setDownloadUserId(Integer downloadUserId) {
		this.downloadUserId = downloadUserId;
	}

	public Date getDownloadTime() {
		return this.downloadTime;
	}

	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}

}