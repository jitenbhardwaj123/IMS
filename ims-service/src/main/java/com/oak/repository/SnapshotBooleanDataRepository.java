package com.oak.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.dto.SnapshotBooleanDataDto;
import com.oak.entity.SnapshotBooleanDataEntity;

public interface SnapshotBooleanDataRepository extends CommonCustomJpaRepository<SnapshotBooleanDataEntity, Long> {
	static final String FIND_CLOSEST = "SELECT sd1 FROM SnapshotBooleanDataEntity sd1 WHERE "
			+ "sd1.elementId = :elementId "
			+ "and sd1.assetId = :assetId "
			+ "and sd1.readingDate = (select max(sd2.readingDate) FROM SnapshotBooleanDataEntity sd2 WHERE "
			+ "sd1.elementId = sd2.elementId and sd1.assetId = sd2.assetId and sd2.readingDate <= :readingDate)";
	
	public static final String BATCH_CREATE_SQL = "INSERT INTO snapshot_boolean_data(id, asset_id, element_id, source_id, reading_date, reading, date_created, last_updated) "
			+ "VALUES (nextval('snapshot_boolean_data_seq'),?,?,?,?,?,?,?)";
	
	public static final String BATCH_UPDATE_SQL = "UPDATE snapshot_boolean_data SET reading=?, last_updated=?"
			+ " WHERE asset_id=? AND element_id=? AND source_id=? AND reading_date=?";

	default void batchCreate(JdbcTemplate jdbcTemplate, List<SnapshotBooleanDataDto> dtos, int batchSize) {
		jdbcTemplate.batchUpdate(BATCH_CREATE_SQL, dtos, batchSize,
				new ParameterizedPreparedStatementSetter<SnapshotBooleanDataDto>() {
					@Override
					public void setValues(PreparedStatement ps, SnapshotBooleanDataDto dto) throws SQLException {
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
							ps.setTimestamp(i++, Timestamp.from(dto.getReadingDate()), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
						} else {
							ps.setNull(i++, java.sql.Types.TIMESTAMP);
						}
						if (dto.getReading() != null) {
							ps.setBoolean(i++, dto.getReading());
						} else {
							ps.setNull(i++, java.sql.Types.BOOLEAN);
						}
						Instant now = Instant.now();
						ps.setTimestamp(i++, Timestamp.from(now), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
						ps.setTimestamp(i++, Timestamp.from(now), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
					}
				});
	}
	
	default void batchUpdate(JdbcTemplate jdbcTemplate, List<SnapshotBooleanDataDto> dtos, int batchSize) {
		jdbcTemplate.batchUpdate(BATCH_UPDATE_SQL, dtos, batchSize,
				new ParameterizedPreparedStatementSetter<SnapshotBooleanDataDto>() {
			@Override
			public void setValues(PreparedStatement ps, SnapshotBooleanDataDto dto) throws SQLException {
				int i = 1;
				if (dto.getReading() != null) {
					ps.setBoolean(i++, dto.getReading());
				} else {
					ps.setNull(i++, java.sql.Types.BOOLEAN);
				}
				Instant now = Instant.now();
				ps.setTimestamp(i++, Timestamp.from(now), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
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
					ps.setTimestamp(i++, Timestamp.from(dto.getReadingDate()), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
				} else {
					ps.setNull(i++, java.sql.Types.TIMESTAMP);
				}
			}
		});
	}
	
	List<SnapshotBooleanDataEntity> findByElementIdInAndAssetIdIn(List<Long> elementIds, List<Long> assetIds);
	
	List<SnapshotBooleanDataEntity> findByElementIdInAndAssetIdAndSourceIdAndReadingDate(List<Long> elementIds, long assetId, long sourceId, Instant readingDate);

	Optional<SnapshotBooleanDataEntity> findByAssetIdAndSourceIdAndElementIdAndReadingDate(long assetId, long sourceId, long elementId, Instant readingDate);
	
	List<SnapshotBooleanDataEntity> findByAssetIdAndSourceIdAndElementIdAndReadingDateIn(long assetId, long sourceId, long elementId, List<Instant> readingDates);
	
	List<SnapshotBooleanDataEntity> findByElementIdInAndAssetIdInAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(
			List<Long> elementIds, List<Long> assetIds, Instant rangeStart, Instant rangeEnd);
	
	void deleteByElementIdIn(List<Long> elementIds);
	
	void deleteByElementIdInAndAssetIdAndReadingDate(List<Long> elementIds, long assetId, Instant readingDate);
	
	void deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqualAndReadingDateLessThanEqual(
			List<Long> elementIds, Long assetId, Instant rangeStart, Instant rangeEnd);
	
	void deleteByElementIdInAndAssetIdAndReadingDateGreaterThanEqual(
			List<Long> elementIds, Long assetId, Instant rangeStart);
	
	@Query(FIND_CLOSEST)
	List<SnapshotBooleanDataEntity> findClosestReading(@Param("assetId") long assetId, @Param("elementId") long elementId, @Param("readingDate") Instant readingDate);
}
