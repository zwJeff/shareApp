package com.jeff.shareapp.model;

import java.util.Date;

public class ResourceRespModel {
	private Integer resourceId;
	private String authorName;
	private int authorId;
	private String courseName;
	private int courseId;
	private String resourceName;
	private String resourceUrl;
	private String resourceDescribe;
	private int resourceFileType;
	private Date resourceUploadTime;
	private Date resourceDownloadDate;
	private Integer resourceDownloadTime;
	private Integer resourceCollectTime;
	private int isCollected;
	private String resourceAuthor;
	
	public String getResourceAuthor() {
		return resourceAuthor;
	}
	public void setResourceAuthor(String resourceAuthor) {
		this.resourceAuthor = resourceAuthor;
	}
	public void setIsCollected(int isCollected) {
		this.isCollected = isCollected;
	}
	public int getIsCollected() {
		return isCollected;
	}

	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public String getResourceDescribe() {
		return resourceDescribe;
	}
	public void setResourceDescribe(String resourceDescribe) {
		this.resourceDescribe = resourceDescribe;
	}
	public int getResourceFileType() {
		return resourceFileType;
	}
	public void setResourceFileType(int resourceFileType) {
		this.resourceFileType = resourceFileType;
	}
	public Date getResourceUploadTime() {
		return resourceUploadTime;
	}
	public void setResourceUploadTime(Date resourceUploadTime) {
		this.resourceUploadTime = resourceUploadTime;
	}
	public Integer getResourceDownloadTime() {
		return resourceDownloadTime;
	}
	public void setResourceDownloadTime(Integer resourceDownloadTime) {
		this.resourceDownloadTime = resourceDownloadTime;
	}
	public Integer getResourceCollectTime() {
		return resourceCollectTime;
	}
	public void setResourceCollectTime(Integer resourceCollectTime) {
		this.resourceCollectTime = resourceCollectTime;
	}
	public Date getResourceDownloadDate() {
		return resourceDownloadDate;
	}
	public void setResourceDownloadDate(Date resourceDownloadDate) {
		this.resourceDownloadDate = resourceDownloadDate;
	}
	
	
	public ResourceRespModel(Integer resourceId, String authorName,
			int courseId, String resourceName, int resourceFileType,
			Date resourceUploadTime) {
		super();
		this.resourceId = resourceId;
		this.authorName = authorName;
		this.courseId = courseId;
		this.resourceName = resourceName;
		this.resourceFileType = resourceFileType;
		this.resourceUploadTime = resourceUploadTime;
	}
	
	/**
	 * �����б?�����
	 * @param resourceId
	 * @param authorName
	 * @param resourceName
	 * @param resourceFileType
	 * @param resourceDownloadDate
	 */
	public ResourceRespModel(Integer resourceId, String authorName,
			String resourceName, int resourceFileType) {
		super();
		this.resourceId = resourceId;
		this.authorName = authorName;
		this.resourceName = resourceName;
		this.resourceFileType = resourceFileType;
	}
	public ResourceRespModel(Integer resourceId, String resourceName,
			Integer resourceFileType, Date resourceUploadTime) {
		// TODO Auto-generated constructor stub
		super();
		this.resourceId = resourceId;
		this.resourceUploadTime=resourceUploadTime;
		this.resourceName = resourceName;
		this.resourceFileType = resourceFileType;
	}
	public ResourceRespModel() {
		// TODO Auto-generated constructor stub
	}
	

	

	
	
}
