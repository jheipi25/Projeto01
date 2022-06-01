package com.levi.route.api.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dto.StopDto;
import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.StopStatus;
import com.levi.route.api.service.StopService;


@RestController
@RequestMapping("/routeProcessor/stop")
@CrossOrigin(origins = "*")
@Slf4j
public class StopController {
	
	private final StopService stopService;

	public StopController(StopService stopService) {
		this.stopService = stopService;
	}

	@PostMapping
	public StopDto create(@Valid @RequestBody StopDto stopDto) {
		log.info("Add stop: {}", stopDto.toString());
		
		Stop stop = Stop.fromDto(stopDto);

		if(stop.getStopStatus() == null) {
			stop.setStopStatus(StopStatus.PENDING);
		}
		this.stopService.persist(stop);
		
		return stopDto;
	}
	
	@DeleteMapping(value = "/{id}")
	public void remove(@PathVariable("id") Long id) {
		log.info("Removing stop ID: {}", id);
		this.stopService.remove(id);
	}

}
