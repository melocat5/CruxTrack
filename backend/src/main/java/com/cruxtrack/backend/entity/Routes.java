package com.cruxtrack.backend.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "routes")
public class Routes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long routeId;

	@Column(nullable = false)
	@JsonAlias("routeName")
	private String routename;

	@Column(nullable = false)
	private String grade;

	@Column(nullable = false)
	private String holdColor;

	@Column(nullable = false)
	private boolean isActive;

	@Column(nullable = false)
	@JsonAlias("climbType")
	private String routeType;

	@Column(nullable = false)
	@JsonAlias("startType")
	private String starttype;

	@Column(nullable = false)
	@JsonAlias("routeSetter")
	private String routesetter;

	@Column
	@JsonAlias("dateSet")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate usageDate;

	public Routes() {
	}

	public Routes(String routename, String grade, String holdColor, boolean isActive, String routeType,
			String starttype, String routesetter, LocalDate usageDate) {
		this.routename = routename;
		this.grade = grade;
		this.holdColor = holdColor;
		this.isActive = isActive;
		this.routeType = routeType;
		this.starttype = starttype;
		this.routesetter = routesetter;
		this.usageDate = usageDate;
	}

	@JsonProperty("id")
	public long getRouteId() {
		return routeId;
	}

	@JsonProperty("id")
	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}

	@JsonProperty("routeName")
	public String getRoutename() {
		return routename;
	}

	public void setRoutename(String routename) {
		this.routename = routename;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getHoldColor() {
		return holdColor;
	}

	public void setHoldColor(String holdColor) {
		this.holdColor = holdColor;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	@JsonProperty("climbType")
	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	@JsonProperty("startType")
	public String getStarttype() {
		return starttype;
	}

	public void setStarttype(String starttype) {
		this.starttype = starttype;
	}

	@JsonProperty("routeSetter")
	public String getRoutesetter() {
		return routesetter;
	}

	public void setRoutesetter(String routesetter) {
		this.routesetter = routesetter;
	}

	@JsonProperty("dateSet")
	public LocalDate getUsageDate() {
		return usageDate;
	}

	public void setUsageDate(LocalDate usageDate) {
		this.usageDate = usageDate;
	}
}
