package com.levi.route.api.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class CoordinateDto {

	@NotNull
	private String lat;

	@NotNull
	private String lng;

	@NotNull
	private String instant;

	@NotNull
	private String vehicleId;
	
}
