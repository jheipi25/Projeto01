package com.levi.route.api.service;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.levi.route.api.RouteProjectApplication;
import com.levi.route.api.entity.Coordinate;
import com.levi.route.api.repository.CoordinateRepository;
import com.levi.route.api.service.CoordinateService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@ActiveProfiles("test")
public class CoordinateServiceTest {

	@MockBean
	private CoordinateRepository coordinateRepository;

	@Autowired
	private CoordinateService coordinateService;
	
	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.coordinateRepository.save(Mockito.any(Coordinate.class))).willReturn(new Coordinate());
		BDDMockito.given(this.coordinateRepository.findPreviousCoordinate(Mockito.anyLong(), Mockito.any(Date.class))).willReturn(Optional.of(new Coordinate()));
	}
	

	@Test
	public void findTop2ByVehicleIdTest() {
		Optional<Coordinate> coordinates = this.coordinateService.findPreviousCoordinate(1L, new Date());
		assertNotNull(coordinates);
	}
	
}
