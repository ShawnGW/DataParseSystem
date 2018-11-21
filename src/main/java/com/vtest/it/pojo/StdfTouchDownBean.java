package com.vtest.it.pojo;

import java.io.Serializable;

public class StdfTouchDownBean  implements Serializable {
	private Integer primaryTouchDownTimes;
	private Long primaryTouchDownDuringTime;
	private Integer reTestTouchDownTimes;
	private Long reTestTouchDownDuringTime;
	private long singleTouchDownDuringTime;
	private double primaryRate;
	private double reTestRate;
	private long testDuringTime;
	public Integer getPrimaryTouchDownTimes() {
		return primaryTouchDownTimes;
	}
	public void setPrimaryTouchDownTimes(Integer primaryTouchDownTimes) {
		this.primaryTouchDownTimes = primaryTouchDownTimes;
	}
	public Long getPrimaryTouchDownDuringTime() {
		return primaryTouchDownDuringTime;
	}
	public void setPrimaryTouchDownDuringTime(Long primaryTouchDownDuringTime) {
		this.primaryTouchDownDuringTime = primaryTouchDownDuringTime;
	}
	public Integer getReTestTouchDownTimes() {
		return reTestTouchDownTimes;
	}
	public void setReTestTouchDownTimes(Integer reTestTouchDownTimes) {
		this.reTestTouchDownTimes = reTestTouchDownTimes;
	}
	public Long getReTestTouchDownDuringTime() {
		return reTestTouchDownDuringTime;
	}
	public void setReTestTouchDownDuringTime(Long reTestTouchDownDuringTime) {
		this.reTestTouchDownDuringTime = reTestTouchDownDuringTime;
	}
	public long getSingleTouchDownDuringTime() {
		return singleTouchDownDuringTime;
	}
	public void setSingleTouchDownDuringTime(long singleTouchDownDuringTime) {
		this.singleTouchDownDuringTime = singleTouchDownDuringTime;
	}

	public void setPrimaryRate(double primaryRate) {
		this.primaryRate = primaryRate;
	}

	public double getPrimaryRate() {
		return primaryRate;
	}

	public double getReTestRate() {
		return reTestRate;
	}
	public void setReTestRate(double reTestRate) {
		this.reTestRate = reTestRate;
	}
	public long getTestDuringTime() {
		return testDuringTime;
	}
	public void setTestDuringTime(long testDuringTime) {
		this.testDuringTime = testDuringTime;
	}
	
}
