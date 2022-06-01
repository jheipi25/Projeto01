package com.levi.route.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import com.levi.route.api.dto.StopDto;
import com.levi.route.api.enun.StopStatus;

@Entity
@Table(name = "stop")
@Data
public class Stop implements GeoPoint {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "latitude", nullable = false)
	private double lat;

	@Column(name = "longitude", nullable = false)
	private double lng;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "delivery_radius", nullable = false)
	private double deliveryRadius;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_route")
	private Route route;

	@Enumerated(EnumType.STRING)
	@Column(name = "stop_status", nullable = true)
	private StopStatus stopStatus;

	@Column(name = "start_date", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date startDate;

	@Column(name = "end_date", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date endDate;
	
	public Stop() {
		
	}

	public static StopDto toDto(Stop stop) {
		StopDto stopDto = new StopDto();
		
		stopDto.setId(stop.getId());
		stopDto.setLat(String.valueOf(stop.getLat()));
		stopDto.setLng(String.valueOf(stop.getLng()));
		stopDto.setDescription(stop.getDescription());
		stopDto.setDeliveryRadius(String.valueOf(stop.getDeliveryRadius()));
		stopDto.setRouteId(String.valueOf(stop.getRoute().getId()));
		stopDto.setStatus(stop.getStopStatus());
		
		return stopDto;
	}
	
	public static Stop fromDto(StopDto stopDto) {
		Stop stop = new Stop();
		Route route = new Route();
		
		stop.setRoute(route);
		stop.getRoute().setId(Long.valueOf(stopDto.getRouteId()));
		stop.setLat(Double.valueOf(stopDto.getLat()));
		stop.setLng(Double.valueOf(stopDto.getLng()));
		stop.setDescription(stopDto.getDescription());
		stop.setDeliveryRadius(Double.valueOf(stopDto.getDeliveryRadius()));
		stop.setStopStatus(stopDto.getStatus());
		
		return stop;
	}

	public static boolean isInProgress(Stop stop) {
		return StopStatus.PROGRESS.equals(stop.getStopStatus());
	}

	public static boolean isPending(Stop stop) {
		return StopStatus.PENDING.equals(stop.getStopStatus());
	}

	public static boolean isFinished(Stop stop) {
		return StopStatus.FINISHED.equals(stop.getStopStatus());
	}
	
}
