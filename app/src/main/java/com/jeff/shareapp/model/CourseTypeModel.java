package com.jeff.shareapp.model;
// default package



/**
 * CourseTypeModel entity. @author MyEclipse Persistence Tools
 */

public class CourseTypeModel  implements java.io.Serializable {


    // Fields    

     private Integer courseId;
     private String courseName;
     private String courseIconUrl;
     private Boolean isIndexCourse;


    // Constructors

    /** default constructor */
    public CourseTypeModel() {
    }

	/** minimal constructor */
    public CourseTypeModel(Integer courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }
    
    /** full constructor */
    public CourseTypeModel(Integer courseId, String courseName, String courseIconUrl, Boolean isIndexCourse) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseIconUrl = courseIconUrl;
        this.isIndexCourse = isIndexCourse;
    }

   
    // Property accessors

    public Integer getCourseId() {
        return this.courseId;
    }
    
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return this.courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseIconUrl() {
        return this.courseIconUrl;
    }
    
    public void setCourseIconUrl(String courseIconUrl) {
        this.courseIconUrl = courseIconUrl;
    }

    public Boolean getIsIndexCourse() {
        return this.isIndexCourse;
    }
    
    public void setIsIndexCourse(Boolean isIndexCourse) {
        this.isIndexCourse = isIndexCourse;
    }
   








}