package com.oak.validator;

import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.SnapshotTextDataDto;
import com.oak.entity.SnapshotTextDataEntity;
import com.oak.repository.SnapshotTextDataRepository;

public class DuplicateSnapshotTextDataValidator extends BaseValidator<DuplicateSnapshotTextData, SnapshotTextDataDto> {
	@Autowired
	private SnapshotTextDataRepository repo;

	@Override
	public boolean isValid(SnapshotTextDataDto data, ConstraintValidatorContext context) {
		if (data != null && data.getAssetId() != null && data.getSourceId() != null && data.getElementId() != null
				&& data.getReadingDate() != null) {
			Optional<SnapshotTextDataEntity> existingEntity = repo.findByAssetIdAndSourceIdAndElementIdAndReadingDate(
					data.getAssetId(), data.getSourceId(), data.getElementId(), data.getReadingDate());
			if (existingEntity.isPresent() && (data.getId() == null || !existingEntity.get().getId().equals(data.getId()))) {
				addCustomViolation(context, "data.snapshot.duplicate", data.getReading(), data.getAssetId(), data.getSourceId(), data.getElementId(), data.getReadingDate());
				return false;
			}
		}
		return true;
	}
}
