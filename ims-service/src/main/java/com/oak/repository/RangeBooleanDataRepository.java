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
import com.oak.dto.RangeBooleanDataDto;
import com.oak.entity.RangeBooleanDataEntity;

public interface RangeBooleanDataRepository extends CommonCustomJpaRepository<RangeBooleanDataEntity, Long> {
	static final String FIND_BY_ELEMENTS_ASSETS_DATE_RANGE = "SELECT wde FROM RangeBooleanDataEntity wde WHERE "
			+ "wde.elementId in (:elementIds) "
			+ "and wde.assetId in (:assetIds) "
			+ "and wde.startDate <= :rangeEnd "
			+ "and (wde.endDate is null or wde.endDate >= :rangeStart)";
	
	static final String FIND_APPLICABLE_BY_DATE = "SELECT wde FROM RangeBooleanDataEntity wde WHERE "
			+ "wde.elementId = :elementId "
			+ "and wde.assetId = :assetId "
			+ "and wde.startDate <= :checkDate "
			+ "and (wde.endDate is null or wde.endDate >= :checkDate)";
	
	public static final String BATCH_CREATE_SQL = "INSERT INTO range_boolean_data(id, asset_id, element_id, source_id, start_date, end_date, reading, date_created, last_updated) "
			+ "VALUES (nextval('range_boolean_data_seq'),?,?,?,?,?,?,?,?)";
	
	public static final String BATCH_UPDATE_SQL = "UPDATE range_boolean_data SET end_date=?, reading=?, last_updated=?"
			+ " WHERE asset_id=? AND element_id=? AND source_id=? AND start_date=?";

	default void batchCreate(JdbcTemplate jdbcTemplate, List<RangeBooleanDataDto> dtos, int batchSize) {
		jdbcTemplate.batchUpdate(BATCH_CREATE_SQL, dtos, batchSize,
				new ParameterizedPreparedStatementSetter<RangeBooleanDataDto>() {
					@Override
					public void setValues(PreparedStatement ps, RangeBooleanDataDto dto) throws SQLException {
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
						if (dto.getStartDate() != null) {
							ps.setTimestamp(i++, Timestamp.from(dto.getStartDate()), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
						} else {
							ps.setNull(i++, java.sql.Types.TIMESTAMP);
						}
						if (dto.getEndDate() != null) {
							ps.setTimestamp(i++, Timestamp.from(dto.getEndDate()), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
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
	
	default void batchUpdate(JdbcTemplate jdbcTemplate, List<RangeBooleanDataDto> dtos, int batchSize) {
		jdbcTemplate.batchUpdate(BATCH_UPDATE_SQL, dtos, batchSize,
				new ParameterizedPreparedStatementSetter<RangeBooleanDataDto>() {
			@Override
			public void setValues(PreparedStatement ps, RangeBooleanDataDto dto) throws SQLException {
				int i = 1;
				if (dto.getEndDate() != null) {
					ps.setTimestamp(i++, Timestamp.from(dto.getEndDate()), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
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
				if (dto.getStartDate() != null) {
					ps.setTimestamp(i++, Timestamp.from(dto.getStartDate()), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
				} else {
					ps.setNull(i++, java.sql.Types.TIMESTAMP);
				}
			}
		});
	}
	
	List<RangeBooleanDataEntity> findByElementIdInAndAssetIdIn(List<Long> elementIds, List<Long> assetIds);
	
	List<RangeBooleanDataEntity> findByElementIdInAndAssetIdAndSourceIdAndStartDateAndEndDate(List<Long> elementIds, long assetId, long sourceId, Instant startDate, Instant endDate);

	Optional<RangeBooleanDataEntity> findByAssetIdAndSourceIdAndElementIdAndStartDate(long assetId, long sourceId, long elementId, Instant startDate);
	
	@Query(FIND_BY_ELEMENTS_ASSETS_DATE_RANGE)
	List<RangeBooleanDataEntity> findByElementIdInAndAssetIdInAndEndDateAfterAndStartDateBefore(
			@Param("elementIds") List<Long> elementIds, @Param("assetIds") List<Long> assetIds, @Param("rangeStart") Instant rangeStart, @Param("rangeEnd") Instant rangeEnd);
	
	@Query(FIND_APPLICABLE_BY_DATE)
	List<RangeBooleanDataEntity> findApplicableForDate(
			@Param("elementId") Long elementId, @Param("assetId") Long assetId, @Param("checkDate") Instant checkDate);
	
	List<RangeBooleanDataEntity> findByAssetIdAndSourceIdAndElementIdAndStartDateIn(long assetId, long sourceId, long elementId, List<Instant> startDates);
}
