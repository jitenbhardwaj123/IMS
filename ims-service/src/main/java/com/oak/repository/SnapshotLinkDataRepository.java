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
import com.oak.dto.SnapshotLinkDataDto;
import com.oak.entity.SnapshotLinkDataEntity;

public interface SnapshotLinkDataRepository extends CommonCustomJpaRepository<SnapshotLinkDataEntity, Long> {
	public static final String BATCH_SQL = "INSERT INTO snapshot_link_data(id, asset_id, element_id, source_id, reading_date, link_record_id, date_created, last_updated) "
			+ "VALUES (nextval('snapshot_link_data_seq'),?,?,?,?,?,now(),now())";

	default void batch(JdbcTemplate jdbcTemplate, List<SnapshotLinkDataDto> dtos, int batchSize) {
		jdbcTemplate.batchUpdate(BATCH_SQL, dtos, batchSize,
				new ParameterizedPreparedStatementSetter<SnapshotLinkDataDto>() {
					@Override
					public void setValues(PreparedStatement ps, SnapshotLinkDataDto dto) throws SQLException {
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
						if (dto.getLinkRecordId() != null) {
							ps.setLong(i++, dto.getLinkRecordId());
						} else {
							ps.setNull(i++, java.sql.Types.NUMERIC);
						}
					}
				});
	}
	
	List<SnapshotLinkDataEntity> findByElementIdInAndAssetIdIn(List<Long> elementIds, List<Long> assetIds);
	
	List<SnapshotLinkDataEntity> findByElementIdInAndAssetIdAndSourceIdAndReadingDate(List<Long> elementIds, long assetId, long sourceId, Instant readingDate);

	Optional<SnapshotLinkDataEntity> findByAssetIdAndSourceIdAndElementIdAndReadingDate(long assetId, long sourceId, long elementId, Instant readingDate);
	
	List<SnapshotLinkDataEntity> findByElementIdInAndAssetIdInAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(
			List<Long> elementIds, List<Long> assetIds, Instant rangeStart, Instant rangeEnd);
}
