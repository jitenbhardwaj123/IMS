package com.oak.service.impl;

import java.math.BigDecimal;

import com.oak.dto.ExecutionResult;
import com.oak.dto.RangeDecimalDataDto;
import com.oak.dto.SnapshotDecimalDataDto;

public class DummyService {
	
	public static ExecutionResult calculateIntegrityStatus(SnapshotDecimalDataDto reading, RangeDecimalDataDto lowLimit, RangeDecimalDataDto highLimit, String absoluteMaxLimit) {
		ExecutionResult result = new ExecutionResult();
		String status = "";
		String statusReason = "";
		
		if (reading.getReading().compareTo(new BigDecimal(absoluteMaxLimit)) > 0) {
			status = "Critical";
			statusReason = String.format("The annulus pressure reading [%s] is above the absolute maximum pressure [%s]", reading.getReading(), absoluteMaxLimit);
		} else if (lowLimit == null && highLimit == null) {
			status = "Unavailable";
			statusReason = "Neither Low nor High Limit is available";
		} else if (lowLimit != null && highLimit == null) {
			if (reading.getReading().compareTo(lowLimit.getReading()) < 0) {
				status = "Unsafe";
				statusReason = String.format("The annulus pressure reading [%s] is below the low limit [%s]", reading.getReading(), lowLimit.getReading());
			} else {
				status = "Safe";
				statusReason = String.format("The annulus pressure reading [%s] is above the low limit [%s]", reading.getReading(), lowLimit.getReading());
			}
		} else if (lowLimit == null && highLimit != null) {
			if (reading.getReading().compareTo(highLimit.getReading()) > 0) {
				status = "Unsafe";
				statusReason = String.format("The annulus pressure reading [%s] is above the high limit [%s]", reading.getReading(), highLimit.getReading());
			} else {
				status = "Safe";
				statusReason = String.format("The annulus pressure reading [%s] is below the high limit [%s]", reading.getReading(), highLimit.getReading());
			}
		} else {
			if (reading.getReading().compareTo(lowLimit.getReading()) < 0) {
				status = "Unsafe";
				statusReason = String.format("The annulus pressure reading [%s] is below the low limit [%s]", reading.getReading(), lowLimit.getReading());
			} else if (reading.getReading().compareTo(highLimit.getReading()) > 0) {
				status = "Unsafe";
				statusReason = String.format("The annulus pressure reading [%s] is above the high limit [%s]", reading.getReading(), highLimit.getReading());
			} else {
				status = "Safe";
				statusReason = String.format("The annulus pressure reading [%s] is within the low and high limits [low limit: %s, high limit: %s]", reading.getReading(), lowLimit.getReading(), highLimit.getReading());
			}
			
		}
			
		result.setApplicableDate(reading.getReadingDate());
		result.getResults().put("STATUS", status);
		result.getResults().put("STATUS_REASON", statusReason);
		return result;
	}
}
