package com.levi.route.api.controller;

import java.text.ParseException;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.route.api.dto.CoordinateDto;
import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.service.CoordinateProcessorService;
import com.levi.route.api.service.CoordinateService;

@RestController
@RequestMapping("/routeProcessor/coordinate")
@CrossOrigin(origins = "*")
@Slf4j
public class CoordinateController {


    private final CoordinateService coordinateService;

    private final CoordinateProcessorService processor;

    public CoordinateController(CoordinateService coordinateService, CoordinateProcessorService processor) {
        this.coordinateService = coordinateService;
        this.processor = processor;
    }

    @PostMapping
    public CoordinateDto create(@Valid @RequestBody CoordinateDto coordinateDto) throws ParseException {
        log.info("Creating coordinate and process");
        Coordinate coordinate = coordinateService.persist(Coordinate.fromDto(coordinateDto));
        processor.processCoordinate(coordinate);
        return coordinateDto;
    }

}
