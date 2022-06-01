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
import com.levi.route.api.entity.Stop;
import com.levi.route.api.repository.StopRepository;
import com.levi.route.api.service.StopService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteProjectApplication.class)
@ActiveProfiles("test")
public class StopServiceTest {

	@MockBean
	private StopRepository stopRepository;

	@Autowired
	private StopService stopService;
	
	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.stopRepository.save(Mockito.any(Stop.class))).willReturn(new Stop());
		BDDMockito.given(this.stopRepository.findFinishedStopsByRoute(Mockito.any(String.class), Mockito.anyLong())).willReturn(new ArrayList<Stop>());
		BDDMockito.given(this.stopRepository.findLongerStopsByRoute(Mockito.anyLong())).willReturn(new ArrayList<Stop>());
	}
	
	@Test
	public void persistStopTest() {
		Stop stop = this.stopService.persist(new Stop());
		assertNotNull(stop);
	}

	@Test
	public void findFinishedStopByRouteTest() {
		List<Stop> user = this.stopService.findFinishedStopsByRoute("2011-01-01T20:00:00", 1L);
		assertNotNull(user);
	}
	
	@Test
	public void findLongerStopByRouteTest() {
		List<Stop> stop = this.stopService.findLongerStopsByRoute(1L);
		assertNotNull(stop);
	}
}
