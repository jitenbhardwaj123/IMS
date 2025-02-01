package com.oak.validator;

import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.SnapshotBooleanDataDto;
import com.oak.entity.SnapshotBooleanDataEntity;
import com.oak.repository.SnapshotBooleanDataRepository;

public class DuplicateSnapshotBooleanDataValidator extends BaseValidator<DuplicateSnapshotBooleanData, SnapshotBooleanDataDto> {
	@Autowired
	private SnapshotBooleanDataRepository repo;

	@Override
	public boolean isValid(SnapshotBooleanDataDto data, ConstraintValidatorContext context) {
		if (data != null && data.getAssetId() != null && data.getSourceId() != null && data.getElementId() != null
				&& data.getReadingDate() != null) {
			Optional<SnapshotBooleanDataEntity> existingEntity = repo.findByAssetIdAndSourceIdAndElementIdAndReadingDate(
					data.getAssetId(), data.getSourceId(), data.getElementId(), data.getReadingDate());
			if (existingEntity.isPresent() && (data.getId() == null || !existingEntity.get().getId().equals(data.getId()))) {
				addCustomViolation(context, "data.snapshot.duplicate", data.getReading(), data.getAssetId(), data.getSourceId(), data.getElementId(), data.getReadingDate());
				return false;
			}
		}
		return true;
	}
}
