package com.levi.route.api.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.levi.route.api.RouteProjectApplication;
import com.levi.route.api.entity.Route;
import com.levi.route.api.repository.RouteRepository;
import com.levi.route.api.service.RouteService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@ActiveProfiles("test")
public class RouteServiceTest {

	@MockBean
	private RouteRepository routeRepository;

	@Autowired
	private RouteService routeService;
	
	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.routeRepository.save(Mockito.any(Route.class))).willReturn(new Route());
		BDDMockito.given(this.routeRepository.findPendingOrProgress()).willReturn(new ArrayList<Route>());
		BDDMockito.given(this.routeRepository.findStatusInDate(Mockito.any(String.class), Mockito.anyLong())).willReturn(new String());
	}
	
	@Test
	public void persistRouteTest() {
		Route route = this.routeService.persist(new Route());
		assertNotNull(route);
	}

	@Test
	public void findPendentOrProgressByVehicleIdTest() {
		List<Route> route = this.routeService.findPendingOrProgress();
		assertNotNull(route);
	}
	
	@Test
	public void findRoutesByStatusInDateTest() {
		String status = this.routeService.findStatusInDate("2011-01-01T20:00:00", 1L);
		assertNotNull(status);
	}
}
