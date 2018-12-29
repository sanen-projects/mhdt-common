package com.mhdt.analyse;

import com.mhdt.analyse.Validate;
import com.mhdt.toolkit.DateUtility;

/**
 * <pre>
 * 
 * @author 懒得出风头
 * Date: 2017/11/07
 * Time: 11:34
 * </pre>
 */
public class SimpleCounter implements Counter {

	int originalValue;

	int value;

	public SimpleCounter(int originalValue) {
		this.originalValue = originalValue;
		reset();
	}

	Integer last_day;
	int last_increase_time;
	

	@Override
	public void increaseWithDay() {
		increaseWithDay(1);
	}
	
	public void increaseWithDay(int incremental) {
		int now = Integer.parseInt(DateUtility.getNow("dd"));

		if (!Validate.isNullOrEmpty(last_day) && now > last_day) {
			reset();
		} else {
			value += incremental;
		}
		this.last_day = now;
		this.last_increase_time = DateUtility.getNowForInt();
	}
	

	@Override
	public void reset() {

		value = originalValue;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public int getLastIncreaseTime() {
		return last_increase_time;
	}

}
