package com.levi.route.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.levi.route.api.dto.StopDto;
import com.levi.route.api.entity.Stop;
import com.levi.route.api.service.StopService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StopControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private StopService stopService;
	
	private static final String URL_BASE = "/routeProcessor/stop/";
	private static final double DELIVERY_RADIUS = 20.0;
	private static final String DESCRIPTION = "description";
	private static final double LATITUDE = 20.0;
	private static final double LONGITUDE = 20.0;
	private static final Long STOP_ID = 1L;
	
	@Test
	@WithMockUser
	public void persistTest() throws JsonProcessingException, Exception{
		Stop stop = getData();
		BDDMockito.given(this.stopService.persist(Mockito.any(Stop.class))).willReturn(stop);
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.getJson())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.deliveryRadius").doesNotExist())
				.andExpect(jsonPath("$.data.description").doesNotExist())
				.andExpect(jsonPath("$.data.lat").doesNotExist())
				.andExpect(jsonPath("$.data.lng").doesNotExist())
				.andExpect(jsonPath("$.data.routeId").doesNotExist())
				.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	@WithMockUser
	public void removeTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + STOP_ID)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isOk());
	}
	
	private String getJson() throws JsonProcessingException {
		StopDto stopDto = new StopDto();
		stopDto.setDeliveryRadius(String.valueOf(DELIVERY_RADIUS));
		stopDto.setDescription(DESCRIPTION);
		stopDto.setLat(String.valueOf(LATITUDE));
		stopDto.setLng(String.valueOf(LONGITUDE));
		stopDto.setId(null);
		stopDto.setRouteId(String.valueOf(1L));
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(stopDto);
	}
	
	private Stop getData() {
		Stop stop = new Stop();
		stop.setDeliveryRadius(DELIVERY_RADIUS);
		stop.setDescription(DESCRIPTION);
		stop.setLat(LATITUDE);
		stop.setLng(LONGITUDE);
		stop.setId(STOP_ID);
		
		return stop;
	}
}
