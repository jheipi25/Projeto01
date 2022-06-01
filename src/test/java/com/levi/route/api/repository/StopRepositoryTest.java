package com.levi.route.api.repository;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.RouteStatus;
import com.levi.route.api.enun.StopStatus;
import com.levi.route.api.repository.RouteRepository;
import com.levi.route.api.repository.StopRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@ActiveProfiles("test")
public class StopRepositoryTest {

	@Autowired
	private StopRepository stopRepository;
	
	@Autowired
	private RouteRepository routeRepository;
	
	private static final String UPDATE_FINISHED_DATE = "2002-01-04T08:00:00";
	private Long idRoute;
	
	@Before
	public void setUp() throws Exception {
		Route route = this.routeRepository.save(returnRouteToTest());
		idRoute = route.getId();
		this.stopRepository.save(returnStopToTest(route));
		Stop firstStopFinised = returnStopToTest(route);
		firstStopFinised.setStopStatus(StopStatus.FINISHED);
		firstStopFinised.setEndDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(("2002-01-04T01:00:00")));
		this.stopRepository.save(firstStopFinised);
		Stop secondStopFinised = returnStopToTest(route);
		secondStopFinised.setStopStatus(StopStatus.FINISHED);
		secondStopFinised.setEndDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(("2002-01-04T08:00:00")));
		this.stopRepository.save(secondStopFinised);
	}

	@After
	public void tearDown() throws Exception {
		this.stopRepository.deleteAll();
	}
	
	@Test
	public void findFinishedStopByRouteTest() throws ParseException {
		List<Stop> stops = stopRepository.findFinishedStopsByRoute("2002-01-04T06:00:00", idRoute);
		assertEquals(1, stops.size());
	}
	
	@Test
	public void findLongerStopByRouteTest() throws ParseException {
		List<Stop> stops = stopRepository.findLongerStopsByRoute(idRoute);
		assertEquals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(UPDATE_FINISHED_DATE), stops.get(1).getEndDate());
	}
	
	private Stop returnStopToTest(Route route) throws ParseException {
		Stop stop = new Stop();
		stop.setRoute(route);
		stop.setDeliveryRadius(30);
		stop.setDescription("Test Description");
		stop.setLat(Long.valueOf(20));
		stop.setLng(Long.valueOf(20));
		stop.setStopStatus(StopStatus.PROGRESS);
		stop.setStartDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2002-01-04T00:00:00"));
		return stop;
	}
	
	private Route returnRouteToTest() throws ParseException {
		Route route = new Route();
		route.setAssignedVehicle(Long.valueOf(12345));
		route.setRoutePlan("A");
		route.setStatus(RouteStatus.PENDING);
		return route;
	}

}
