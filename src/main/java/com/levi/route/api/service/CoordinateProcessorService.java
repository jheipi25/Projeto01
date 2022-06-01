package com.levi.route.api.service;

import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.entity.Route;
import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.RouteStatus;
import com.levi.route.api.enun.StopStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.levi.route.api.entity.Route.isInProgress;
import static com.levi.route.api.entity.Route.isPending;


import static com.levi.route.api.entity.Stop.isInProgress;
import static com.levi.route.api.entity.Stop.isPending;
import static com.levi.route.api.service.CoordinateService.PREVIOUS_COORDINATE_PREFIX;
import static com.levi.route.api.util.DistanceCalculatorUtil.distance;

@Service
@Slf4j
public class CoordinateProcessorService {

    private final RouteService routeService;

    private final StopService stopService;

    private final CoordinateService coordinateService;

    private final RedisTemplate redisTemplate;

    public CoordinateProcessorService(RouteService routeService, StopService stopService, CoordinateService coordinateService, RedisTemplate redisTemplate) {
        this.routeService = routeService;
        this.stopService = stopService;
        this.coordinateService = coordinateService;
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "coordinate", groupId = "1234", containerFactory = "kafkaListenerContainerFactory")
    public void processCoordinate(@Payload Coordinate coordinate) {
        log.info("Creating coordinate and process");
        coordinateService.persist(coordinate);
        List<Route> routes = this.routeService.findPendingOrProgress();
        routes.forEach(route -> {
            if (isRouteCoordinate(coordinate, route)) {
                processRoute(route, coordinate);
            }
        });
        savePreviousCoordinateInCache(coordinate);
    }

    private void savePreviousCoordinateInCache(@Payload Coordinate coordinate) {
        redisTemplate.opsForValue().set(PREVIOUS_COORDINATE_PREFIX + coordinate.getVehicleId(), coordinate, 1, TimeUnit.HOURS);
    }

    private void processRoute(Route route, Coordinate coordinate) {
        Optional<Coordinate> previousCoordinate = coordinateService.findPreviousCoordinate(coordinate.getVehicleId(), coordinate.getInstant());
        startRouteIfNecessary(route);
        for (Stop stop : route.getPlannedStops()) {
            previousCoordinate.ifPresent(value -> processStop(coordinate, value, stop));
        }
        endRouteIfNecessary(route);
    }

    private void processStop(Coordinate coordinate, Coordinate previousCoordinate, Stop stop) {
        if (isPending(stop)) {
            startStopIfNecessary(coordinate, previousCoordinate, stop);
        } else if (isInProgress(stop)) {
            endStopIfNecessary(coordinate, stop);
        }
    }

    private boolean isRouteCoordinate(Coordinate coordinate, Route route) {
        return coordinate.getVehicleId().equals(route.getAssignedVehicle());
    }

    private void startRouteIfNecessary(Route route) {
        if (isPending(route)) {
            route.setStatus(RouteStatus.PROGRESS);
            route.setStartDate(new Date());
            routeService.persist(route);
        }
    }

    private void endRouteIfNecessary(Route route) {
        long finishedStops = route.getPlannedStops().stream()
                .filter(Stop::isFinished)
                .count();

        if (isInProgress(route) && finishedStops == route.getPlannedStops().size()) {
                route.setStatus(RouteStatus.FINISHED);
                route.setEndDate(new Date());
                routeService.persist(route);
        }
    }

    private void startStopIfNecessary(Coordinate coordinate, Coordinate previousCoordinate, Stop stop) {
        if (hasTwoCoordinatesWithinStop(coordinate, previousCoordinate, stop) &&
                hasStayedLongEnough(coordinate, previousCoordinate)) {

            stop.setStopStatus(StopStatus.PROGRESS);
            stop.setStartDate(new Date());
            stopService.persist(stop);
        }
    }

    private void endStopIfNecessary(Coordinate coordinate, Stop stop) {
        if (distance(coordinate, stop) > stop.getDeliveryRadius()) {

            stop.setStopStatus(StopStatus.FINISHED);
            stop.setEndDate(new Date());
            stopService.persist(stop);
        }
    }

    private boolean hasStayedLongEnough(Coordinate coordinate, Coordinate previousCoordinate) {
        return Duration.between(previousCoordinate.getInstant().toInstant(), coordinate.getInstant().toInstant()).toMinutes() <= 5;
    }

    private boolean hasTwoCoordinatesWithinStop(Coordinate coordinate, Coordinate previousCoordinate, Stop stop) {
        return isCoordinateWithinStop(coordinate, stop) && isCoordinateWithinStop(previousCoordinate, stop);
    }

    private boolean isCoordinateWithinStop(Coordinate coordinate, Stop stop) {
        return distance(coordinate, stop) <= stop.getDeliveryRadius();
    }

}
