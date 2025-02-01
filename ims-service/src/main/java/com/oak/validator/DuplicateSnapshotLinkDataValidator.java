package com.oak.validator;

import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.SnapshotLinkDataDto;
import com.oak.entity.SnapshotLinkDataEntity;
import com.oak.repository.SnapshotLinkDataRepository;

public class DuplicateSnapshotLinkDataValidator extends BaseValidator<DuplicateSnapshotLinkData, SnapshotLinkDataDto> {
	@Autowired
	private SnapshotLinkDataRepository repo;

	@Override
	public boolean isValid(SnapshotLinkDataDto data, ConstraintValidatorContext context) {
		if (data != null && data.getAssetId() != null && data.getSourceId() != null && data.getElementId() != null
				&& data.getReadingDate() != null) {
			Optional<SnapshotLinkDataEntity> existingEntity = repo.findByAssetIdAndSourceIdAndElementIdAndReadingDate(
					data.getAssetId(), data.getSourceId(), data.getElementId(), data.getReadingDate());
			if (existingEntity.isPresent() && (data.getId() == null || !existingEntity.get().getId().equals(data.getId()))) {
				addCustomViolation(context, "data.snapshot.duplicate", data.getLinkRecordId(), data.getAssetId(), data.getSourceId(), data.getElementId(), data.getReadingDate());
				return false;
			}
		}
		return true;
	}
}
