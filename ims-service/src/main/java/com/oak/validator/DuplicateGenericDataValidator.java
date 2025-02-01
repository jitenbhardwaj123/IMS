package com.oak.validator;

import static com.oak.enums.DataCategory.RANGE;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.oak.common.identity.Identifiable;
import com.oak.common.validation.constraint.impl.BaseValidator;
import com.oak.dto.GenericDataDto;
import com.oak.repository.RangeBooleanDataRepository;
import com.oak.repository.RangeDecimalDataRepository;
import com.oak.repository.RangeLinkDataRepository;
import com.oak.repository.RangeTextDataRepository;
import com.oak.repository.RangeUploadDataRepository;
import com.oak.repository.SnapshotBooleanDataRepository;
import com.oak.repository.SnapshotDecimalDataRepository;
import com.oak.repository.SnapshotLinkDataRepository;
import com.oak.repository.SnapshotTextDataRepository;
import com.oak.repository.SnapshotUploadDataRepository;

public class DuplicateGenericDataValidator extends BaseValidator<DuplicateGenericData, List<GenericDataDto>> {
	@Autowired
	private RangeBooleanDataRepository rangeBooleanDataRepository;
	
	@Autowired
	private RangeDecimalDataRepository rangeDecimalDataRepository;
	
	@Autowired
	private RangeTextDataRepository rangeTextDataRepository;
	
	@Autowired
	private RangeLinkDataRepository rangeLinkDataRepository;
	
	@Autowired
	private RangeUploadDataRepository rangeUploadDataRepository;
	
	@Autowired
	private SnapshotBooleanDataRepository snapshotBooleanDataRepository;
	
	@Autowired
	private SnapshotDecimalDataRepository snapshotDecimalDataRepository;
	
	@Autowired
	private SnapshotTextDataRepository snapshotTextDataRepository;
	
	@Autowired
	private SnapshotLinkDataRepository snapshotLinkDataRepository;
	
	@Autowired
	private SnapshotUploadDataRepository snapshotUploadDataRepository;

	@Override
	public boolean isValid(List<GenericDataDto> data, ConstraintValidatorContext context) {
		List<GenericDataDto> invalidData = new ArrayList<>();
		if (isNotEmpty(data)) {
			invalidData = data.stream().filter(this::isInvalid).collect(toList());
		}
		invalidData.forEach(dt -> addCustomViolation(context, "data.generic.duplicate", this.getReading(dt),
				dt.getAssetId(), dt.getSourceId(), dt.getElementId(), dt.getReadingDate()));
		return invalidData.isEmpty();
	}

	public boolean isInvalid(GenericDataDto data) {
		if (data != null && data.getAssetId() != null && data.getSourceId() != null && data.getElementId() != null
				&& data.getReadingDate() != null && data.getMeasurementType() != null
				&& data.getDataCategory() != null) {
			Optional<? extends Identifiable> existingEntity;
			if (RANGE == data.getDataCategory()) {
				existingEntity = this.getRangeData(data);
			} else {
				existingEntity = this.getSnapshotData(data);
			}
			return existingEntity.isPresent()
					&& (data.getId() == null || !existingEntity.get().getId().equals(data.getId()));
		}
		return false;
	}

	private Optional<? extends Identifiable> getRangeData(GenericDataDto data) {
		switch (data.getMeasurementType()) {
		case BOOLEAN_OPTIONS:
			return rangeBooleanDataRepository.findByAssetIdAndSourceIdAndElementIdAndStartDate(data.getAssetId(),
					data.getSourceId(), data.getElementId(), data.getReadingDate());
		case DECIMAL:
			return rangeDecimalDataRepository.findByAssetIdAndSourceIdAndElementIdAndStartDate(data.getAssetId(),
					data.getSourceId(), data.getElementId(), data.getReadingDate());
		case TEXT:
		case TEXT_OPTIONS:
			return rangeTextDataRepository.findByAssetIdAndSourceIdAndElementIdAndStartDate(data.getAssetId(),
					data.getSourceId(), data.getElementId(), data.getReadingDate());
		case LINK_GROUP:
			return rangeLinkDataRepository.findByAssetIdAndSourceIdAndElementIdAndStartDate(data.getAssetId(),
					data.getSourceId(), data.getElementId(), data.getReadingDate());
		case FILE_GROUP:
			return rangeUploadDataRepository.findByAssetIdAndSourceIdAndElementIdAndStartDate(data.getAssetId(),
					data.getSourceId(), data.getElementId(), data.getReadingDate());
		default:
			return Optional.empty();
		}
	}

	private Optional<? extends Identifiable> getSnapshotData(GenericDataDto data) {
		switch (data.getMeasurementType()) {
		case BOOLEAN_OPTIONS:
			return snapshotBooleanDataRepository.findByAssetIdAndSourceIdAndElementIdAndReadingDate(data.getAssetId(),
					data.getSourceId(), data.getElementId(), data.getReadingDate());
		case DECIMAL:
			return snapshotDecimalDataRepository.findByAssetIdAndSourceIdAndElementIdAndReadingDate(data.getAssetId(),
					data.getSourceId(), data.getElementId(), data.getReadingDate());
		case TEXT:
		case TEXT_OPTIONS:
			return snapshotTextDataRepository.findByAssetIdAndSourceIdAndElementIdAndReadingDate(data.getAssetId(),
					data.getSourceId(), data.getElementId(), data.getReadingDate());
		case LINK_GROUP:
			return snapshotLinkDataRepository.findByAssetIdAndSourceIdAndElementIdAndReadingDate(data.getAssetId(),
					data.getSourceId(), data.getElementId(), data.getReadingDate());
		case FILE_GROUP:
			return snapshotUploadDataRepository.findByAssetIdAndSourceIdAndElementIdAndReadingDate(data.getAssetId(),
					data.getSourceId(), data.getElementId(), data.getReadingDate());
		default:
			return Optional.empty();
		}
	}

	private Object getReading(GenericDataDto data) {
		switch (data.getMeasurementType()) {
		case BOOLEAN_OPTIONS:
			return data.isBooleanReading();
		case DECIMAL:
			return data.getDecimalReading();
		case TEXT:
		case TEXT_OPTIONS:
			return data.getTextReading();
		case LINK_GROUP:
			return data.getLinkRecordId();
		case FILE_GROUP:
			return data.getUploadRecordId();
		default:
			return null;
		}
	}
}
