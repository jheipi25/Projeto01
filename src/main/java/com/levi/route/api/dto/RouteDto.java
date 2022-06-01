package com.levi.route.api.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.levi.route.api.entity.Stop;
import com.levi.route.api.enun.RouteStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RouteDto {

	private Long id;

	private String routePlan;

	@NotNull
	private String assignedVehicle;

	private RouteStatus status;

	private List<Stop> plannedStops;
	
	public RouteDto(Long id, String routePlan, String assignedVehicle, RouteStatus status) {
		super();
		this.id = id;
		this.routePlan = routePlan;
		this.assignedVehicle = assignedVehicle;
		this.status = status;
	}
	
	public RouteDto() {
		
	}
	
}
