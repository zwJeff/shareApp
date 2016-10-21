package com.jeff.shareapp.model;

import java.util.Date;

public class TaskRespModel {

	// Fields

	private Integer taskId;
	private String taskName;
	private String resourceName;
	private int resourceFileType;
	private Integer resourceId;
	private Integer paperId;
	private String teacherName;
	private Date newTime;
	private int taskStatu;
	private int totalNumber;
	private int finishNumber;
	
	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public int getFinishNumber() {
		return finishNumber;
	}

	public void setFinishNumber(int finishNumber) {
		this.finishNumber = finishNumber;
	}


	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Date getNewTime() {
		return newTime;
	}

	public void setNewTime(Date newTime) {
		this.newTime = newTime;
	}

	public int getTaskStatu() {
		return taskStatu;
	}

	public void setTaskStatu(int taskStatu) {
		this.taskStatu = taskStatu;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public int getResourceFileType() {
		return resourceFileType;
	}

	public void setResourceFileType(int resourceFileType) {
		this.resourceFileType = resourceFileType;
	}

}
