package com.cruxtrack.backend.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

public class RoutesRequest {

	@JsonAlias("routeName")
	private String routename;

	private String grade;

	private String holdColor;

	private boolean isActive;

	@JsonAlias("climbType")
	private String routeType;

	@JsonAlias("startType")
	private String starttype;

	@JsonAlias("routeSetter")
	private String routesetter;

	@JsonAlias("dateSet")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate usageDate;

	public RoutesRequest() {
	}

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

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getStarttype() {
		return starttype;
	}

	public void setStarttype(String starttype) {
		this.starttype = starttype;
	}

	public String getRoutesetter() {
		return routesetter;
	}

	public void setRoutesetter(String routesetter) {
		this.routesetter = routesetter;
	}

	public LocalDate getUsageDate() {
		return usageDate;
	}

	public void setUsageDate(LocalDate usageDate) {
		this.usageDate = usageDate;
	}
}
