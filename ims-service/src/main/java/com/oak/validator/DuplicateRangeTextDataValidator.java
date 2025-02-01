package com.oak.validator;

import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.RangeTextDataDto;
import com.oak.entity.RangeTextDataEntity;
import com.oak.repository.RangeTextDataRepository;

public class DuplicateRangeTextDataValidator extends BaseValidator<DuplicateRangeTextData, RangeTextDataDto> {
	@Autowired
	private RangeTextDataRepository repo;

	@Override
	public boolean isValid(RangeTextDataDto data, ConstraintValidatorContext context) {
		if (data != null && data.getAssetId() != null && data.getSourceId() != null && data.getElementId() != null
				&& data.getStartDate() != null) {
			Optional<RangeTextDataEntity> existingEntity = repo.findByAssetIdAndSourceIdAndElementIdAndStartDate(
					data.getAssetId(), data.getSourceId(), data.getElementId(), data.getStartDate());
			if (existingEntity.isPresent() && (data.getId() == null || !existingEntity.get().getId().equals(data.getId()))) {
				addCustomViolation(context, "data.range.duplicate", data.getReading(), data.getAssetId(), data.getSourceId(), data.getElementId(), data.getStartDate());
				return false;
			}
		}
		return true;
	}
}
