package com.oak.validator;

import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.SnapshotDecimalDataDto;
import com.oak.entity.SnapshotDecimalDataEntity;
import com.oak.repository.SnapshotDecimalDataRepository;

public class DuplicateSnapshotDecimalDataValidator extends BaseValidator<DuplicateSnapshotDecimalData, SnapshotDecimalDataDto> {
	@Autowired
	private SnapshotDecimalDataRepository repo;

	@Override
	public boolean isValid(SnapshotDecimalDataDto data, ConstraintValidatorContext context) {
		if (data != null && data.getAssetId() != null && data.getSourceId() != null && data.getElementId() != null
				&& data.getReadingDate() != null) {
			Optional<SnapshotDecimalDataEntity> existingEntity = repo.findByAssetIdAndSourceIdAndElementIdAndReadingDate(
					data.getAssetId(), data.getSourceId(), data.getElementId(), data.getReadingDate());
			if (existingEntity.isPresent() && (data.getId() == null || !existingEntity.get().getId().equals(data.getId()))) {
				addCustomViolation(context, "data.snapshot.duplicate", data.getReading(), data.getAssetId(), data.getSourceId(), data.getElementId(), data.getReadingDate());
				return false;
			}
		}
		return true;
	}
}
