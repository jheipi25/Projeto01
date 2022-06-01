package com.levi.route.api.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.levi.route.api.entity.Coordinate;

@Transactional(readOnly = true)
public interface CoordinateRepository extends JpaRepository<Coordinate, Long>  {

	@Query(value = "SELECT * FROM coordinate c WHERE c.vehicle_id = :vehicle_id AND c.instant < :instant ORDER BY c.instant DESC LIMIT 1", nativeQuery = true)
	Optional<Coordinate> findPreviousCoordinate(@Param("vehicle_id") long vehicleId, @Param("instant") Date instant);
	
}
