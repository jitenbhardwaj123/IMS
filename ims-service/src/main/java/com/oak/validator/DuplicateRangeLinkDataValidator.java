package com.oak.validator;

import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.RangeLinkDataDto;
import com.oak.entity.RangeLinkDataEntity;
import com.oak.repository.RangeLinkDataRepository;

public class DuplicateRangeLinkDataValidator extends BaseValidator<DuplicateRangeLinkData, RangeLinkDataDto> {
	@Autowired
	private RangeLinkDataRepository repo;

	@Override
	public boolean isValid(RangeLinkDataDto data, ConstraintValidatorContext context) {
		if (data != null && data.getAssetId() != null && data.getSourceId() != null && data.getElementId() != null
				&& data.getStartDate() != null) {
			Optional<RangeLinkDataEntity> existingEntity = repo.findByAssetIdAndSourceIdAndElementIdAndStartDate(
					data.getAssetId(), data.getSourceId(), data.getElementId(), data.getStartDate());
			if (existingEntity.isPresent() && (data.getId() == null || !existingEntity.get().getId().equals(data.getId()))) {
				addCustomViolation(context, "data.range.duplicate", data.getLinkRecordId(), data.getAssetId(), data.getSourceId(), data.getElementId(), data.getStartDate());
				return false;
			}
		}
		return true;
	}
}
