package com.jeff.shareapp.model;
// default package



/**
 * PictureListModel entity. @author MyEclipse Persistence Tools
 */

public class PictureListModel  implements java.io.Serializable {


    // Fields    

     private Integer pictureId;
     private Integer pictureNewsType;
     private String pictureDescribe;
     private String pictureUrl;
     private String pictureContext;
     private Integer resourceId;


    // Constructors

    /** default constructor */
    public PictureListModel() {
    }

	/** minimal constructor */
    public PictureListModel(Integer pictureId, Integer pictureNewsType, String pictureDescribe, String pictureUrl) {
        this.pictureId = pictureId;
        this.pictureNewsType = pictureNewsType;
        this.pictureDescribe = pictureDescribe;
        this.pictureUrl = pictureUrl;
    }
    
    /** full constructor */
    public PictureListModel(Integer pictureId, Integer pictureNewsType, String pictureDescribe, String pictureUrl, String pictureContext, Integer resourceId) {
        this.pictureId = pictureId;
        this.pictureNewsType = pictureNewsType;
        this.pictureDescribe = pictureDescribe;
        this.pictureUrl = pictureUrl;
        this.pictureContext = pictureContext;
        this.resourceId = resourceId;
    }

   
    // Property accessors

    public Integer getPictureId() {
        return this.pictureId;
    }
    
    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public Integer getPictureNewsType() {
        return this.pictureNewsType;
    }
    
    public void setPictureNewsType(Integer pictureNewsType) {
        this.pictureNewsType = pictureNewsType;
    }

    public String getPictureDescribe() {
        return this.pictureDescribe;
    }
    
    public void setPictureDescribe(String pictureDescribe) {
        this.pictureDescribe = pictureDescribe;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }
    
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureContext() {
        return this.pictureContext;
    }
    
    public void setPictureContext(String pictureContext) {
        this.pictureContext = pictureContext;
    }

    public Integer getResourceId() {
        return this.resourceId;
    }
    
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
   








}