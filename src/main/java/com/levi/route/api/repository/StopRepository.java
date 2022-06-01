package com.levi.route.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.levi.route.api.entity.Stop;

public interface StopRepository extends JpaRepository<Stop, Long> {

	@Query("SELECT s FROM Stop s WHERE end_date <= :date AND id_route = :id_route")
	List<Stop> findFinishedStopsByRoute(@Param("date") String date, @Param("id_route") Long routeId);
	
	@Query(value = "Select * From Stop AS s WHERE s.stop_status = 'FINISHED' AND s.id_route = :id_route"
			+ "     ORDER BY (s.end_date - s.start_date) DESC"
			,nativeQuery = true)
	List<Stop> findLongerStopsByRoute(@Param("id_route") Long routeId);
}
