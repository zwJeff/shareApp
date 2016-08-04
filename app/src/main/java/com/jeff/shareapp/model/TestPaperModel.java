package com.jeff.shareapp.model;
// default package



/**
 * TestPaperModel entity. @author MyEclipse Persistence Tools
 */

public class TestPaperModel  implements java.io.Serializable {


    // Fields    

     private Integer paperId;
     private Integer question0Id;
     private Integer question1Id;
     private Integer question2Id;
     private Integer question3Id;
     private Integer question4Id;
     private Integer question5Id;
     private Integer question6Id;
     private Integer question7Id;
     private Integer question8Id;
     private Integer question9Id;


    // Constructors

    /** default constructor */
    public TestPaperModel() {
    }

	/** minimal constructor */
    public TestPaperModel(Integer paperId) {
        this.paperId = paperId;
    }

    
    public TestPaperModel(Integer question0Id, Integer question1Id, Integer question2Id, Integer question3Id, Integer question4Id, Integer question5Id, Integer question6Id, Integer question7Id, Integer question8Id, Integer question9Id) {
    
        this.question0Id = question0Id;
        this.question1Id = question1Id;
        this.question2Id = question2Id;
        this.question3Id = question3Id;
        this.question4Id = question4Id;
        this.question5Id = question5Id;
        this.question6Id = question6Id;
        this.question7Id = question7Id;
        this.question8Id = question8Id;
        this.question9Id = question9Id;
    }

    
    
    /** full constructor */
    public TestPaperModel(Integer paperId, Integer question0Id, Integer question1Id, Integer question2Id, Integer question3Id, Integer question4Id, Integer question5Id, Integer question6Id, Integer question7Id, Integer question8Id, Integer question9Id) {
        this.paperId = paperId;
        this.question0Id = question0Id;
        this.question1Id = question1Id;
        this.question2Id = question2Id;
        this.question3Id = question3Id;
        this.question4Id = question4Id;
        this.question5Id = question5Id;
        this.question6Id = question6Id;
        this.question7Id = question7Id;
        this.question8Id = question8Id;
        this.question9Id = question9Id;
    }

   
    // Property accessors

    public Integer getPaperId() {
        return this.paperId;
    }
    
    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getQuestion0Id() {
        return this.question0Id;
    }
    
    public void setQuestion0Id(Integer question0Id) {
        this.question0Id = question0Id;
    }

    public Integer getQuestion1Id() {
        return this.question1Id;
    }
    
    public void setQuestion1Id(Integer question1Id) {
        this.question1Id = question1Id;
    }

    public Integer getQuestion2Id() {
        return this.question2Id;
    }
    
    public void setQuestion2Id(Integer question2Id) {
        this.question2Id = question2Id;
    }

    public Integer getQuestion3Id() {
        return this.question3Id;
    }
    
    public void setQuestion3Id(Integer question3Id) {
        this.question3Id = question3Id;
    }

    public Integer getQuestion4Id() {
        return this.question4Id;
    }
    
    public void setQuestion4Id(Integer question4Id) {
        this.question4Id = question4Id;
    }

    public Integer getQuestion5Id() {
        return this.question5Id;
    }
    
    public void setQuestion5Id(Integer question5Id) {
        this.question5Id = question5Id;
    }

    public Integer getQuestion6Id() {
        return this.question6Id;
    }
    
    public void setQuestion6Id(Integer question6Id) {
        this.question6Id = question6Id;
    }

    public Integer getQuestion7Id() {
        return this.question7Id;
    }
    
    public void setQuestion7Id(Integer question7Id) {
        this.question7Id = question7Id;
    }

    public Integer getQuestion8Id() {
        return this.question8Id;
    }
    
    public void setQuestion8Id(Integer question8Id) {
        this.question8Id = question8Id;
    }

    public Integer getQuestion9Id() {
        return this.question9Id;
    }
    
    public void setQuestion9Id(Integer question9Id) {
        this.question9Id = question9Id;
    }
   








}