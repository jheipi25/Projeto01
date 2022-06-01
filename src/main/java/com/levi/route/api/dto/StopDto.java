package com.levi.route.api.dto;

import javax.validation.constraints.NotNull;

import com.levi.route.api.enun.StopStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StopDto {

	private Long id;

	@NotNull
	private String lat;

	@NotNull
	private String lng;

	private String description;

	@NotNull
	private String deliveryRadius;

	@NotNull
	private String routeId;

	private StopStatus status;
	
}
