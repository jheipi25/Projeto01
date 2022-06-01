package com.levi.route.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.levi.route.api.RouteProjectApplication;
import com.levi.route.api.dto.CoordinateDto;
import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.service.CoordinateService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CoordinateControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CoordinateService coordinateService;
	
	private static final String URL_BASE = "/routeProcessor/coordinate/";
	private static final String INSTANT = "2018-08-08T23:49:15+00:00";
	private static final double LATITUDE = 20.0;
	private static final double LONGITUDE = 20.0;
	private static final Long COORDINATE_ID = 1L;
	private static final Long VEHICLE_ID = 1L;
	
	@Test
	@WithMockUser
	public void persistTest() throws JsonProcessingException, Exception{
		Coordinate coordinate = getData();
		BDDMockito.given(this.coordinateService.persist(Mockito.any(Coordinate.class))).willReturn(coordinate);
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.getJson())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.routePlan").doesNotExist())
				.andExpect(jsonPath("$.data.assignedVehicle").doesNotExist())
				.andExpect(jsonPath("$.data.status").doesNotExist())
				.andReturn().getResponse().getContentAsString();
	}
	
	private String getJson() throws JsonProcessingException {
		CoordinateDto coordinateDto = new CoordinateDto();
		coordinateDto.setInstant(String.valueOf(INSTANT));
		coordinateDto.setLat(String.valueOf(LATITUDE));
		coordinateDto.setLng(String.valueOf(LONGITUDE));
		coordinateDto.setVehicleId(String.valueOf(VEHICLE_ID));
		
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(coordinateDto);
	}
	
	private Coordinate getData() throws ParseException {
		Coordinate coordinate = new Coordinate();
		coordinate.setId(COORDINATE_ID);
		coordinate.setInstant(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(INSTANT));
		coordinate.setLat(LATITUDE);
		coordinate.setLng(LONGITUDE);
		coordinate.setVehicleId(VEHICLE_ID);
		
		return coordinate;
	}
}
