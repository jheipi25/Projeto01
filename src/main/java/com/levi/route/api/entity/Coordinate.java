package com.levi.route.api.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.levi.route.api.dto.CoordinateDto;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Table(name = "coordinate")
@Data
public class Coordinate implements GeoPoint, Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name = "latitude", nullable = false)
	private double lat;
	@Column(name = "longitude", nullable = false)
	private double lng;
	private Date instant;
	@Column(name = "vehicle_id", nullable = false)
	private Long vehicleId;
	
	public Coordinate() {
		
	}

	public static Coordinate fromDto(CoordinateDto coordinateDto) throws ParseException {
		Coordinate coordinate = new Coordinate();
		
		coordinate.setInstant(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(coordinateDto.getInstant()));
		coordinate.setLat(Double.valueOf(coordinateDto.getLat()));
		coordinate.setLng(Double.valueOf(coordinateDto.getLng()));
		coordinate.setVehicleId(Long.valueOf(coordinateDto.getVehicleId()));

		return coordinate;
	}
	
}
