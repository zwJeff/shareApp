package com.jeff.shareapp.model;
// default package

import java.util.Date;


/**
 * ResourceModel entity. @author MyEclipse Persistence Tools
 */

public class ResourceModel  implements java.io.Serializable {


    // Fields    

     private Integer resourceId;
     private String resourceName;
     private String resourceUrl;
     private String resourceDescribe;
     private Integer resourceCourseType;
     private Integer resourceFileType;
     private Integer resourceAuthor;
     private Date resourceUploadTime;
     private Integer resourceDownloadTime;
     private Integer resourceCollectTime;


    // Constructors

    /** default constructor */
    public ResourceModel() {
    }

	/** minimal constructor */
    public ResourceModel(String resourceName, String resourceUrl, Integer resourceCourseType, Integer resourceFileType, Integer resourceAuthor, Date resourceUploadTime, Integer resourceDownloadTime, Integer resourceCollectTime) {
        this.resourceName = resourceName;
        this.resourceUrl = resourceUrl;
        this.resourceCourseType = resourceCourseType;
        this.resourceFileType = resourceFileType;
        this.resourceAuthor = resourceAuthor;
        this.resourceUploadTime = resourceUploadTime;
        this.resourceDownloadTime = resourceDownloadTime;
        this.resourceCollectTime = resourceCollectTime;
    }
    
    /** full constructor */
    public ResourceModel(String resourceName, String resourceUrl, String resourceDescribe, Integer resourceCourseType, Integer resourceFileType, Integer resourceAuthor, Date resourceUploadTime, Integer resourceDownloadTime, Integer resourceCollectTime) {
        this.resourceName = resourceName;
        this.resourceUrl = resourceUrl;
        this.resourceDescribe = resourceDescribe;
        this.resourceCourseType = resourceCourseType;
        this.resourceFileType = resourceFileType;
        this.resourceAuthor = resourceAuthor;
        this.resourceUploadTime = resourceUploadTime;
        this.resourceDownloadTime = resourceDownloadTime;
        this.resourceCollectTime = resourceCollectTime;
    }

   
    // Property accessors

    public ResourceModel(int resourceAuthor, int resourceCourseType, String resourceName,
			String resourceUrl, int resourceFileType,
			Date resourceUploadTime, int resourceDownloadTime, int resourceCollectTime) {
		// TODO Auto-generated constructor stub
    	this.resourceName = resourceName;
        this.resourceUrl = resourceUrl;
        this.resourceDescribe = resourceDescribe;
        this.resourceCourseType = resourceCourseType;
        this.resourceFileType = resourceFileType;
        this.resourceAuthor = resourceAuthor;
        this.resourceUploadTime = resourceUploadTime;
        this.resourceDownloadTime = resourceDownloadTime;
        this.resourceCollectTime = resourceCollectTime;
	}

	public Integer getResourceId() {
        return this.resourceId;
    }
    
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return this.resourceName;
    }
    
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return this.resourceUrl;
    }
    
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceDescribe() {
        return this.resourceDescribe;
    }
    
    public void setResourceDescribe(String resourceDescribe) {
        this.resourceDescribe = resourceDescribe;
    }

    public Integer getResourceCourseType() {
        return this.resourceCourseType;
    }
    
    public void setResourceCourseType(Integer resourceCourseType) {
        this.resourceCourseType = resourceCourseType;
    }

    public Integer getResourceFileType() {
        return this.resourceFileType;
    }
    
    public void setResourceFileType(Integer resourceFileType) {
        this.resourceFileType = resourceFileType;
    }

    public Integer getResourceAuthor() {
        return this.resourceAuthor;
    }
    
    public void setResourceAuthor(Integer resourceAuthor) {
        this.resourceAuthor = resourceAuthor;
    }

    public Date getResourceUploadTime() {
        return this.resourceUploadTime;
    }
    
    public void setResourceUploadTime(Date resourceUploadTime) {
        this.resourceUploadTime = resourceUploadTime;
    }

    public Integer getResourceDownloadTime() {
        return this.resourceDownloadTime;
    }
    
    public void setResourceDownloadTime(Integer resourceDownloadTime) {
        this.resourceDownloadTime = resourceDownloadTime;
    }

    public Integer getResourceCollectTime() {
        return this.resourceCollectTime;
    }
    
    public void setResourceCollectTime(Integer resourceCollectTime) {
        this.resourceCollectTime = resourceCollectTime;
    }
   








}