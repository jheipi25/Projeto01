package com.levi.route.api.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.levi.route.api.entity.Stop;
import com.levi.route.api.repository.StopRepository;

@Service
@Slf4j
public class StopService {

	private final StopRepository stopRepository;

	public StopService(StopRepository stopRepository) {
		this.stopRepository = stopRepository;
	}

	public Stop persist(Stop stop) {
		log.info("Creating or updating stop: {}", stop);
		return this.stopRepository.save(stop);
	}

	public void remove(Long id) {
		log.info("Removing stop ID {}", id);
		this.stopRepository.deleteById(id);
	}
	
	public List<Stop> findFinishedStopsByRoute(String date, Long routeId) {
		log.info("Finding finished stops by date {}", date);
		return this.stopRepository.findFinishedStopsByRoute(date, routeId);
	}

	public List<Stop> findLongerStopsByRoute(Long routeId) {
		log.info("Finding longer stop by route {}", routeId);
		return this.stopRepository.findLongerStopsByRoute(routeId);
	}

}
