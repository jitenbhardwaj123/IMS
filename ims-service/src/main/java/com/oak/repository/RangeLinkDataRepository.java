package com.oak.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import com.oak.common.orm.repository.CommonCustomJpaRepository;
import com.oak.dto.RangeLinkDataDto;
import com.oak.entity.RangeLinkDataEntity;

public interface RangeLinkDataRepository extends CommonCustomJpaRepository<RangeLinkDataEntity, Long> {
	static final String FIND_BY_ELEMENTS_ASSETS_DATE_RANGE = "SELECT wde FROM RangeLinkDataEntity wde WHERE "
			+ "wde.elementId in (:elementIds) "
			+ "and wde.assetId in (:assetIds) "
			+ "and wde.startDate <= :rangeEnd "
			+ "and (wde.endDate is null or wde.endDate >= :rangeStart)";
	
	public static final String BATCH_SQL = "INSERT INTO range_link_data(id, asset_id, element_id, source_id, start_date, end_date, link_record_id, date_created, last_updated) "
			+ "VALUES (nextval('range_link_data_seq'),?,?,?,?,?,?,now(),now())";

	default void batch(JdbcTemplate jdbcTemplate, List<RangeLinkDataDto> dtos, int batchSize) {
		jdbcTemplate.batchUpdate(BATCH_SQL, dtos, batchSize,
				new ParameterizedPreparedStatementSetter<RangeLinkDataDto>() {
					@Override
					public void setValues(PreparedStatement ps, RangeLinkDataDto dto) throws SQLException {
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
							ps.setTimestamp(i++, Timestamp.from(dto.getStartDate()));
						} else {
							ps.setNull(i++, java.sql.Types.TIMESTAMP);
						}
						if (dto.getEndDate() != null) {
							ps.setTimestamp(i++, Timestamp.from(dto.getEndDate()));
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
	
	List<RangeLinkDataEntity> findByElementIdInAndAssetIdIn(List<Long> elementIds, List<Long> assetIds);
	
	List<RangeLinkDataEntity> findByElementIdInAndAssetIdAndSourceIdAndStartDateAndEndDate(List<Long> elementIds, long assetId, long sourceId, Instant startDate, Instant endDate);

	Optional<RangeLinkDataEntity> findByAssetIdAndSourceIdAndElementIdAndStartDate(long assetId, long sourceId, long elementId, Instant startDate);
	
	@Query(FIND_BY_ELEMENTS_ASSETS_DATE_RANGE)
	List<RangeLinkDataEntity> findByElementIdInAndAssetIdInAndEndDateAfterAndStartDateBefore(
			@Param("elementIds") List<Long> elementIds, @Param("assetIds") List<Long> assetIds, @Param("rangeStart") Instant rangeStart, @Param("rangeEnd") Instant rangeEnd);
}
