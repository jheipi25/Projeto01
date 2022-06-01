package com.levi.route.api.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.levi.route.api.dto.RouteDto;
import com.levi.route.api.enun.RouteStatus;
import lombok.Data;

@Entity
@Table(name = "route")
@Data
public class Route {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "route_status", nullable = false)
	private RouteStatus status;

	@Column(name = "assigned_vehicle")
	private Long assignedVehicle;

	@OneToMany(mappedBy = "route", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("route")
	private List<Stop> plannedStops;

	@Column(name = "route_plan", nullable = false)
	private String routePlan;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;
	
	public Route() {
		
	}

	public static Route fromDto(RouteDto routeDto) {
		Route route = new Route();
		
		route.setAssignedVehicle(Long.valueOf(routeDto.getAssignedVehicle()));
		route.setRoutePlan(routeDto.getRoutePlan());
		route.setPlannedStops(routeDto.getPlannedStops());
		
		return route;
	}

	public static boolean isPending(Route route) {
		return RouteStatus.PENDING.equals(route.getStatus());
	}

	public static boolean isInProgress(Route route) {
		return RouteStatus.PROGRESS.equals(route.getStatus());
	}

	public static boolean isFinished(Route route) {
		return RouteStatus.FINISHED.equals(route.getStatus());
	}
	
}
