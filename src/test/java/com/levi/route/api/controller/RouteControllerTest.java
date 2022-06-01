package com.levi.route.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.levi.route.api.RouteProjectApplication;
import com.levi.route.api.dto.RouteDto;
import com.levi.route.api.entity.Route;
import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.RouteStatus;
import com.levi.route.api.service.RouteService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RouteControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private RouteService routeService;
	
	private static final String URL_BASE = "/routeProcessor/route/";
	private static final Long ASSIGNED_VEHICLE = 1L;
	private static final String ROUTE_PLAN = "A";
	private static final RouteStatus STATUS = RouteStatus.FINISHED;
	private static final Long ROUTE_ID = 1L;
	
	@Test(expected = NestedServletException.class)
	@WithMockUser
	public void persistTest() throws JsonProcessingException, Exception{
		Route route = getData();
		BDDMockito.given(this.routeService.persist(Mockito.any(Route.class))).willReturn(route);
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.getJson())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.vehicleId").doesNotExist())
				.andExpect(jsonPath("$.data.instant").doesNotExist())
				.andExpect(jsonPath("$.data.lat").doesNotExist())
				.andExpect(jsonPath("$.data.lng").doesNotExist())
				.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	@WithMockUser
	public void removeTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ROUTE_ID)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
	}
	
	private String getJson() throws JsonProcessingException {
		RouteDto routeDto = new RouteDto();
		
		routeDto.setAssignedVehicle(String.valueOf(ASSIGNED_VEHICLE));
		routeDto.setRoutePlan(ROUTE_PLAN);
		routeDto.setStatus(STATUS);
		
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(routeDto);
	}
	
	private Route getData() {
		Route route = new Route();
		List<Stop> stops = new ArrayList<>();
		Stop stop = new Stop();
		stops.add(stop);
		
		route.setPlannedStops(stops);
		route.setAssignedVehicle(ASSIGNED_VEHICLE);
		route.setId(ROUTE_ID);
		route.setRoutePlan(ROUTE_PLAN);
		route.setStatus(STATUS);
		route.setEndDate(new Date());
		
		return route;
	}
}
