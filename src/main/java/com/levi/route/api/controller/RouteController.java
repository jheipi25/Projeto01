package com.levi.route.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dto.RouteDto;
import com.levi.route.api.dto.StopDto;
import com.levi.route.api.entity.Route;
import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.RouteStatus;
import com.levi.route.api.enun.StopStatus;
import com.levi.route.api.service.RouteService;
import com.levi.route.api.service.StopService;

@RestController
@RequestMapping("/routeProcessor/route")
@CrossOrigin(origins = "*")
@Slf4j
public class RouteController {
	
	private final RouteService routeService;
	
	private final StopService stopService;

	public RouteController(RouteService routeService, StopService stopService) {
		this.routeService = routeService;
		this.stopService = stopService;
	}

	@PostMapping
	public RouteDto persist(@Valid @RequestBody RouteDto routeDto) {
		log.info("create route: {}", routeDto.toString());
		Route route = Route.fromDto(routeDto);

		route.setStatus(RouteStatus.PENDING);
		route = this.routeService.persist(route);
	
		for(Stop stop : route.getPlannedStops()) {
			stop.setRoute(route);
			stop.setStopStatus(StopStatus.PENDING);
			stopService.persist(stop);
		}
		
		return routeDto;
	}
	
	@DeleteMapping(value = "/{id}")
	public void remove(@PathVariable("id") Long id) {
		log.info("Removing route ID: {}", id);
		this.routeService.remove(id);
	}
	
	@GetMapping(value = "/statusInDate/{routeId}")
	public String findRoutesByStatusInDate( @PathVariable Long routeId,
			@RequestParam String date) {
		log.info("Finding routes by status in {}", date.toString());
		return this.routeService.findStatusInDate(date, routeId);
	}
	
	@GetMapping(value = "/longerStopsByRoute/{routeId}")
	public List<StopDto> findLongerStopsByRoute(@PathVariable Long routeId)  {
		
		log.info("Finding longer stop by route {}", routeId);
		
		List<Stop> stops = stopService.findLongerStopsByRoute(routeId);
		List<StopDto> stopDtos = new ArrayList<>();

		for (Stop stop : stops) {
			stopDtos.add(Stop.toDto(stop));
		}
		
		return stopDtos;
	}
	
	@GetMapping(value = "/finishedStopsByRoute/{routeId}")
	public List<StopDto> findFinishedStopByRoute(@PathVariable Long routeId,
			@RequestParam("date") String date)  {
		
		log.info("Calculating finished stops by route in: {}", date);
	
	    List<Stop> stops = stopService.findFinishedStopsByRoute(date, routeId);
	    List<StopDto> stopDtos = new ArrayList<>();

		for (Stop stop : stops) {
			stopDtos.add(Stop.toDto(stop));
		}
		return stopDtos;
	}
	
}
