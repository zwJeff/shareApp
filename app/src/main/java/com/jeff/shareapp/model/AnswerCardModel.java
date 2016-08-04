package com.jeff.shareapp.model;

// default package

import java.util.Date;

/**
 * AnswerCardModel entity. @author MyEclipse Persistence Tools
 */

public class AnswerCardModel implements java.io.Serializable {

	// Fields

	private Integer answerCardId;
	private Integer taskId;
	private Integer taskStatu;
	private Date finishTime;
	private Integer answerUserId;
	private String question0Answer;
	private Integer question0Score;
	private String question1Answer;
	private Integer question1Score;
	private String question2Answer;
	private Integer question2Score;
	private String question3Answer;
	private Integer question3Score;
	private String question4Answer;
	private Integer question4Score;
	private String question5Answer;
	private Integer question5Score;
	private String question6Answer;
	private Integer question6Score;
	private String question7Answer;
	private Integer question7Score;
	private String question8Answer;
	private Integer question8Score;
	private String question9Answer;
	private Integer question9Score;
	private Integer totalScore;

	// Constructors

	/** default constructor */
	public AnswerCardModel() {
	}

	/** minimal constructor */
	public AnswerCardModel(Integer answerCardId, Integer taskId,
			Integer taskStatu, Integer answerUserId) {
		this.answerCardId = answerCardId;
		this.taskId = taskId;
		this.taskStatu = taskStatu;
		this.answerUserId = answerUserId;
	}

	public AnswerCardModel( Integer taskId,
			Integer taskStatu, Date finishTime, Integer answerUserId,
			String question0Answer, Integer question0Score,
			String question1Answer, Integer question1Score,
			String question2Answer, Integer question2Score,
			String question3Answer, Integer question3Score,
			String question4Answer, Integer question4Score,
			String question5Answer, Integer question5Score,
			String question6Answer, Integer question6Score,
			String question7Answer, Integer question7Score,
			String question8Answer, Integer question8Score,
			String question9Answer, Integer question9Score, Integer totalScore) {
		this.taskId = taskId;
		this.taskStatu = taskStatu;
		this.finishTime = finishTime;
		this.answerUserId = answerUserId;
		this.question0Answer = question0Answer;
		this.question0Score = question0Score;
		this.question1Answer = question1Answer;
		this.question1Score = question1Score;
		this.question2Answer = question2Answer;
		this.question2Score = question2Score;
		this.question3Answer = question3Answer;
		this.question3Score = question3Score;
		this.question4Answer = question4Answer;
		this.question4Score = question4Score;
		this.question5Answer = question5Answer;
		this.question5Score = question5Score;
		this.question6Answer = question6Answer;
		this.question6Score = question6Score;
		this.question7Answer = question7Answer;
		this.question7Score = question7Score;
		this.question8Answer = question8Answer;
		this.question8Score = question8Score;
		this.question9Answer = question9Answer;
		this.question9Score = question9Score;
		this.totalScore = totalScore;
	}

	/** full constructor */
	public AnswerCardModel(Integer answerCardId, Integer taskId,
			Integer taskStatu, Date finishTime, Integer answerUserId,
			String question0Answer, Integer question0Score,
			String question1Answer, Integer question1Score,
			String question2Answer, Integer question2Score,
			String question3Answer, Integer question3Score,
			String question4Answer, Integer question4Score,
			String question5Answer, Integer question5Score,
			String question6Answer, Integer question6Score,
			String question7Answer, Integer question7Score,
			String question8Answer, Integer question8Score,
			String question9Answer, Integer question9Score, Integer totalScore) {
		this.answerCardId = answerCardId;
		this.taskId = taskId;
		this.taskStatu = taskStatu;
		this.finishTime = finishTime;
		this.answerUserId = answerUserId;
		this.question0Answer = question0Answer;
		this.question0Score = question0Score;
		this.question1Answer = question1Answer;
		this.question1Score = question1Score;
		this.question2Answer = question2Answer;
		this.question2Score = question2Score;
		this.question3Answer = question3Answer;
		this.question3Score = question3Score;
		this.question4Answer = question4Answer;
		this.question4Score = question4Score;
		this.question5Answer = question5Answer;
		this.question5Score = question5Score;
		this.question6Answer = question6Answer;
		this.question6Score = question6Score;
		this.question7Answer = question7Answer;
		this.question7Score = question7Score;
		this.question8Answer = question8Answer;
		this.question8Score = question8Score;
		this.question9Answer = question9Answer;
		this.question9Score = question9Score;
		this.totalScore = totalScore;
	}

	// Property accessors

	public Integer getAnswerCardId() {
		return this.answerCardId;
	}

	public void setAnswerCardId(Integer answerCardId) {
		this.answerCardId = answerCardId;
	}

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getTaskStatu() {
		return this.taskStatu;
	}

	public void setTaskStatu(Integer taskStatu) {
		this.taskStatu = taskStatu;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getAnswerUserId() {
		return this.answerUserId;
	}

	public void setAnswerUserId(Integer answerUserId) {
		this.answerUserId = answerUserId;
	}

	public String getQuestion0Answer() {
		return this.question0Answer;
	}

	public void setQuestion0Answer(String question0Answer) {
		this.question0Answer = question0Answer;
	}

	public Integer getQuestion0Score() {
		return this.question0Score;
	}

	public void setQuestion0Score(Integer question0Score) {
		this.question0Score = question0Score;
	}

	public String getQuestion1Answer() {
		return this.question1Answer;
	}

	public void setQuestion1Answer(String question1Answer) {
		this.question1Answer = question1Answer;
	}

	public Integer getQuestion1Score() {
		return this.question1Score;
	}

	public void setQuestion1Score(Integer question1Score) {
		this.question1Score = question1Score;
	}

	public String getQuestion2Answer() {
		return this.question2Answer;
	}

	public void setQuestion2Answer(String question2Answer) {
		this.question2Answer = question2Answer;
	}

	public Integer getQuestion2Score() {
		return this.question2Score;
	}

	public void setQuestion2Score(Integer question2Score) {
		this.question2Score = question2Score;
	}

	public String getQuestion3Answer() {
		return this.question3Answer;
	}

	public void setQuestion3Answer(String question3Answer) {
		this.question3Answer = question3Answer;
	}

	public Integer getQuestion3Score() {
		return this.question3Score;
	}

	public void setQuestion3Score(Integer question3Score) {
		this.question3Score = question3Score;
	}

	public String getQuestion4Answer() {
		return this.question4Answer;
	}

	public void setQuestion4Answer(String question4Answer) {
		this.question4Answer = question4Answer;
	}

	public Integer getQuestion4Score() {
		return this.question4Score;
	}

	public void setQuestion4Score(Integer question4Score) {
		this.question4Score = question4Score;
	}

	public String getQuestion5Answer() {
		return this.question5Answer;
	}

	public void setQuestion5Answer(String question5Answer) {
		this.question5Answer = question5Answer;
	}

	public Integer getQuestion5Score() {
		return this.question5Score;
	}

	public void setQuestion5Score(Integer question5Score) {
		this.question5Score = question5Score;
	}

	public String getQuestion6Answer() {
		return this.question6Answer;
	}

	public void setQuestion6Answer(String question6Answer) {
		this.question6Answer = question6Answer;
	}

	public Integer getQuestion6Score() {
		return this.question6Score;
	}

	public void setQuestion6Score(Integer question6Score) {
		this.question6Score = question6Score;
	}

	public String getQuestion7Answer() {
		return this.question7Answer;
	}

	public void setQuestion7Answer(String question7Answer) {
		this.question7Answer = question7Answer;
	}

	public Integer getQuestion7Score() {
		return this.question7Score;
	}

	public void setQuestion7Score(Integer question7Score) {
		this.question7Score = question7Score;
	}

	public String getQuestion8Answer() {
		return this.question8Answer;
	}

	public void setQuestion8Answer(String question8Answer) {
		this.question8Answer = question8Answer;
	}

	public Integer getQuestion8Score() {
		return this.question8Score;
	}

	public void setQuestion8Score(Integer question8Score) {
		this.question8Score = question8Score;
	}

	public String getQuestion9Answer() {
		return this.question9Answer;
	}

	public void setQuestion9Answer(String question9Answer) {
		this.question9Answer = question9Answer;
	}

	public Integer getQuestion9Score() {
		return this.question9Score;
	}

	public void setQuestion9Score(Integer question9Score) {
		this.question9Score = question9Score;
	}

	public Integer getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

}