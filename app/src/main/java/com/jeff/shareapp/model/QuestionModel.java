package com.jeff.shareapp.model;
// default package



/**
 * QuestionModel entity. @author MyEclipse Persistence Tools
 */

public class QuestionModel  implements java.io.Serializable {


    // Fields    

     private Integer questionId;
     private String questionDescription;
     private Integer questionType;
     private String answerA;
     private String answerB;
     private String answerC;
     private String answerD;
     private String answerText;
     private String trueAnswer;
     private Integer questionScore;


    // Constructors

    /** default constructor */
    public QuestionModel() {
    }

	/** minimal constructor */
    public QuestionModel(Integer questionId, String questionDescription, Integer questionType) {
        this.questionId = questionId;
        this.questionDescription = questionDescription;
        this.questionType = questionType;
    }
    
    
    
    public QuestionModel(String questionDescription, Integer questionType, String answerA, String answerB, String answerC, String answerD, String answerText, String trueAnswer, Integer questionScore) {
        
        this.questionDescription = questionDescription;
        this.questionType = questionType;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.answerText = answerText;
        this.trueAnswer = trueAnswer;
        this.questionScore = questionScore;
    }
    
    /** full constructor */
    public QuestionModel(Integer questionId, String questionDescription, Integer questionType, String answerA, String answerB, String answerC, String answerD, String answerText, String trueAnswer, Integer questionScore) {
        this.questionId = questionId;
        this.questionDescription = questionDescription;
        this.questionType = questionType;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.answerText = answerText;
        this.trueAnswer = trueAnswer;
        this.questionScore = questionScore;
    }

   
    // Property accessors

    public Integer getQuestionId() {
        return this.questionId;
    }
    
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestionDescription() {
        return this.questionDescription;
    }
    
    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public Integer getQuestionType() {
        return this.questionType;
    }
    
    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getAnswerA() {
        return this.answerA;
    }
    
    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return this.answerB;
    }
    
    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return this.answerC;
    }
    
    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return this.answerD;
    }
    
    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getAnswerText() {
        return this.answerText;
    }
    
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getTrueAnswer() {
        return this.trueAnswer;
    }
    
    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public Integer getQuestionScore() {
        return this.questionScore;
    }
    
    public void setQuestionScore(Integer questionScore) {
        this.questionScore = questionScore;
    }
   








}