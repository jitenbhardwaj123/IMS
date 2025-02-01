package com.oak.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.dto.SnapshotUploadDataDto;
import com.oak.entity.SnapshotUploadDataEntity;

public interface SnapshotUploadDataRepository extends CommonCustomJpaRepository<SnapshotUploadDataEntity, Long> {
	public static final String BATCH_SQL = "INSERT INTO snapshot_upload_data(id, asset_id, element_id, source_id, reading_date, upload_record_id, date_created, last_updated) "
			+ "VALUES (nextval('snapshot_upload_data_seq'),?,?,?,?,?,now(),now())";

	default void batch(JdbcTemplate jdbcTemplate, List<SnapshotUploadDataDto> dtos, int batchSize) {
		jdbcTemplate.batchUpdate(BATCH_SQL, dtos, batchSize,
				new ParameterizedPreparedStatementSetter<SnapshotUploadDataDto>() {
					@Override
					public void setValues(PreparedStatement ps, SnapshotUploadDataDto dto) throws SQLException {
						int i = 1;
						if (dto.getAssetId() != null) {
							ps.setLong(i++, dto.getAssetId());
						} else {
							ps.setNull(i++, java.sql.Types.NUMERIC);
						}
						if (dto.getElementId() != null) {
							ps.setLong(i++, dto.getElementId());
						} else {
							ps.setNull(i++, java.sql.Types.NUMERIC);
						}
						if (dto.getSourceId() != null) {
							ps.setLong(i++, dto.getSourceId());
						} else {
							ps.setNull(i++, java.sql.Types.NUMERIC);
						}
						if (dto.getReadingDate() != null) {
							ps.setTimestamp(i++, Timestamp.from(dto.getReadingDate()));
						} else {
							ps.setNull(i++, java.sql.Types.TIMESTAMP);
						}
						if (dto.getUploadRecordId() != null) {
							ps.setLong(i++, dto.getUploadRecordId());
						} else {
							ps.setNull(i++, java.sql.Types.NUMERIC);
						}
					}
				});
	}
	
	List<SnapshotUploadDataEntity> findByElementIdInAndAssetIdIn(List<Long> elementIds, List<Long> assetIds);
	
	List<SnapshotUploadDataEntity> findByElementIdInAndAssetIdAndSourceIdAndReadingDate(List<Long> elementIds, long assetId, long sourceId, Instant readingDate);

	Optional<SnapshotUploadDataEntity> findByAssetIdAndSourceIdAndElementIdAndReadingDate(long assetId, long sourceId, long elementId, Instant readingDate);
	
	List<SnapshotUploadDataEntity> findByElementIdInAndAssetIdInAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(
			List<Long> elementIds, List<Long> assetIds, Instant rangeStart, Instant rangeEnd);
}
