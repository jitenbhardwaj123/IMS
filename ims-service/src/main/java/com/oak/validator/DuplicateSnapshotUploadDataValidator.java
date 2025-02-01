package com.oak.validator;

import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.SnapshotUploadDataDto;
import com.oak.entity.SnapshotUploadDataEntity;
import com.oak.repository.SnapshotUploadDataRepository;

public class DuplicateSnapshotUploadDataValidator extends BaseValidator<DuplicateSnapshotUploadData, SnapshotUploadDataDto> {
	@Autowired
	private SnapshotUploadDataRepository repo;

	@Override
	public boolean isValid(SnapshotUploadDataDto data, ConstraintValidatorContext context) {
		if (data != null && data.getAssetId() != null && data.getSourceId() != null && data.getElementId() != null
				&& data.getReadingDate() != null) {
			Optional<SnapshotUploadDataEntity> existingEntity = repo.findByAssetIdAndSourceIdAndElementIdAndReadingDate(
					data.getAssetId(), data.getSourceId(), data.getElementId(), data.getReadingDate());
			if (existingEntity.isPresent() && (data.getId() == null || !existingEntity.get().getId().equals(data.getId()))) {
				addCustomViolation(context, "data.snapshot.duplicate", data.getUploadRecordId(), data.getAssetId(), data.getSourceId(), data.getElementId(), data.getReadingDate());
				return false;
			}
		}
		return true;
	}
}
