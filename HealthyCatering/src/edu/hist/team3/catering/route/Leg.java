package edu.hist.team3.catering.route;

import java.util.ArrayList;
import java.util.Collection;

public class Leg {
	private TextValue distance;
	private TextValue duration;
	private String end_address;
	private Coordinate end_location;
	private String start_address;
	private Coordinate start_location;
	private ArrayList<Step> steps = new ArrayList<Step>();
	private ArrayList<Coordinate> via_waypoint = new ArrayList<Coordinate>();
	
	public Leg() {
		
	}
	
	public void addPolylines(Collection<Polyline> into) {
		for (Step step : steps) {
			into.add(step.getPolyline());
		}
	}
	
	public TextValue getDistance() {
		return distance;
	}
	
	public void setDistance(TextValue distance) {
		this.distance = distance;
	}
	
	public TextValue getDuration() {
		return duration;
	}
	
	public void setDuration(TextValue duration) {
		this.duration = duration;
	}
	
	public Coordinate getStartLocation() {
		return start_location;
	}

	public void setStartLocation(Coordinate startLocation) {
		this.start_location = startLocation;
	}
	
	public Coordinate getEndLocation() {
		return end_location;
	}

	public void setEndLocation(Coordinate endLocation) {
		this.end_location = endLocation;
	}

	public String getEndAddress() {
		return end_address;
	}

	public void setEndAddress(String endAddress) {
		this.end_address = endAddress;
	}

	public String getStartAddress() {
		return start_address;
	}

	public void setStartAddress(String startAddress) {
		this.start_address = startAddress;
	}

	public ArrayList<Step> getSteps() {
		return steps;
	}

	public void setSteps(ArrayList<Step> steps) {
		this.steps = steps;
	}

	public ArrayList<Coordinate> getViaWaypoint() {
		return via_waypoint;
	}

	public void setViaWaypoint(ArrayList<Coordinate> viaWaypoint) {
		this.via_waypoint = viaWaypoint;
	}
}