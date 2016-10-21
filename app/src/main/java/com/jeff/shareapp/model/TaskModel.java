package com.jeff.shareapp.model;
// default package

import java.util.Date;


/**
 * TaskModel entity. @author MyEclipse Persistence Tools
 */

public class TaskModel  implements java.io.Serializable {


    // Fields    

     private Integer taskId;
     private String taskName;
     private Integer resourceId;
     private Integer paperId;
     private Integer teacherId;
     private Date newTime;


    // Constructors

    /** default constructor */
    public TaskModel() {
    }

	/** minimal constructor */
    public TaskModel(Integer taskId, String taskName, Integer teacherId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.teacherId = teacherId;
    }
    
    /** full constructor */
    public TaskModel(Integer taskId, String taskName, Integer resourceId, Integer paperId, Integer teacherId, Date newTime) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.resourceId = resourceId;
        this.paperId = paperId;
        this.teacherId = teacherId;
        this.newTime = newTime;
    }

   
    // Property accessors

    public Integer getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return this.taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getResourceId() {
        return this.resourceId;
    }
    
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getPaperId() {
        return this.paperId;
    }
    
    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getTeacherId() {
        return this.teacherId;
    }
    
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Date getNewTime() {
        return this.newTime;
    }
    
    public void setNewTime(Date newTime) {
        this.newTime = newTime;
    }
   








}