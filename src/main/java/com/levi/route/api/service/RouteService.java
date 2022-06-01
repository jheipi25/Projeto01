package com.levi.route.api.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.levi.route.api.entity.Route;
import com.levi.route.api.repository.RouteRepository;

@Service
@Slf4j
public class RouteService {

	private final RouteRepository routeRepository;

	public RouteService(RouteRepository routeRepository) {
		this.routeRepository = routeRepository;
	}

	public Route persist(Route route) {
		log.info("Creating or updating route: {}", route);
		return this.routeRepository.save(route);
	}

	public List<Route> findPendingOrProgress() {
		log.info("Finding pending route ");
		return this.routeRepository.findPendingOrProgress();
	}

	public String findStatusInDate(String date, Long routeId) {
		log.info("Finding routes by status in {}", date);
		return this.routeRepository.findStatusInDate(date, routeId);
	}
	
	public void remove(Long id) {
		log.info("Removing route ID {}", id);
		this.routeRepository.deleteById(id);
	}

}
