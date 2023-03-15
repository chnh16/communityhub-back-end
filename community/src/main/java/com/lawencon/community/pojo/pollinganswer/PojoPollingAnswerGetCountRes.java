package com.lawencon.community.pojo.pollinganswer;

public class PojoPollingAnswerGetCountRes {
	private String pollingChoiceId;
	private Long countPollAnswer;
	private Float percent;
	
	public Long getCountPollAnswer() {
		return countPollAnswer;
	}
	public void setCountPollAnswer(Long countPollAnswer) {
		this.countPollAnswer = countPollAnswer;
	}

	public String getPollingChoiceId() {
		return pollingChoiceId;
	}

	public void setPollingChoiceId(String pollingChoiceId) {
		this.pollingChoiceId = pollingChoiceId;
	}

	public Float getPercent() {
		return percent;
	}

	public void setPercent(Float percent) {
		this.percent = percent;
	}
	
	
}
