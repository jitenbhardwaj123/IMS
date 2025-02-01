package com.oak.validator;

import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.RangeUploadDataDto;
import com.oak.entity.RangeUploadDataEntity;
import com.oak.repository.RangeUploadDataRepository;

public class DuplicateRangeUploadDataValidator extends BaseValidator<DuplicateRangeUploadData, RangeUploadDataDto> {
	@Autowired
	private RangeUploadDataRepository repo;

	@Override
	public boolean isValid(RangeUploadDataDto data, ConstraintValidatorContext context) {
		if (data != null && data.getAssetId() != null && data.getSourceId() != null && data.getElementId() != null
				&& data.getStartDate() != null) {
			Optional<RangeUploadDataEntity> existingEntity = repo.findByAssetIdAndSourceIdAndElementIdAndStartDate(
					data.getAssetId(), data.getSourceId(), data.getElementId(), data.getStartDate());
			if (existingEntity.isPresent() && (data.getId() == null || !existingEntity.get().getId().equals(data.getId()))) {
				addCustomViolation(context, "data.range.duplicate", data.getUploadRecordId(), data.getAssetId(), data.getSourceId(), data.getElementId(), data.getStartDate());
				return false;
			}
		}
		return true;
	}
}
