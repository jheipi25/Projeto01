package com.levi.route.api.repository;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.levi.route.api.RouteProjectApplication;
import com.levi.route.api.entity.Route;
import com.levi.route.api.enun.RouteStatus;
import com.levi.route.api.repository.RouteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@ActiveProfiles("test")
public class RouteRepositoryTest {

	@Autowired
	private RouteRepository routeRepository;
	
	@Before
	public void setUp() throws Exception {
		this.routeRepository.save(returnRouteToTest());
		Route finishedRoute = returnRouteToTest();
		finishedRoute.setStatus(RouteStatus.FINISHED);
		finishedRoute.setAssignedVehicle(Long.valueOf(54321));
		this.routeRepository.save(finishedRoute);
	}

	@After
	public void tearDown() throws Exception {
		this.routeRepository.deleteAll();
	}
	
	@Test
	public void findPendentOrProgressByVehicleId() {
		List<Route> routes = routeRepository.findPendingOrProgress();
		assertEquals(1, routes.size());
	}
	
	private Route returnRouteToTest() throws ParseException {
		Route route = new Route();
		route.setAssignedVehicle(Long.valueOf(12345));
		route.setRoutePlan("A");
		route.setStatus(RouteStatus.PENDING);
		return route;
	}
}
