package com.levi.route.api.service;

import java.util.Date;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.repository.CoordinateRepository;

@Service
@Slf4j
public class CoordinateService {
	
	private final CoordinateRepository coordinateRepository;

	private final RedisTemplate redisTemplate;

	public static String PREVIOUS_COORDINATE_PREFIX = "PREVIOUS_COORDINATE_";

	public CoordinateService(CoordinateRepository coordinateRepository, RedisTemplate redisTemplate) {
		this.coordinateRepository = coordinateRepository;
		this.redisTemplate = redisTemplate;
	}

	public Optional<Coordinate> findPreviousCoordinate(Long vehicleId, Date instant) {
		log.info("Finding coordinates");
		Coordinate previousCoordinate = (Coordinate) redisTemplate.opsForValue().get(PREVIOUS_COORDINATE_PREFIX + vehicleId);
		if(previousCoordinate == null){
			return coordinateRepository.findPreviousCoordinate(vehicleId, instant);
		}
		return Optional.of(previousCoordinate);
	}
	
	public Coordinate persist(Coordinate coordinate) {
		log.info("Creating coordinate");
		return this.coordinateRepository.save(coordinate);
	}

}
