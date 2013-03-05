package edu.hist.team3.catering.route;

import java.util.ArrayList;
import java.util.Collection;

public class Route {
	private Bounds bounds;
	private String copyrights;
	private ArrayList<Leg> legs = new ArrayList<Leg>(); 
	private Polyline overview_polyline;
	private String summary;
	private ArrayList<String> warnings = new ArrayList<String>(); 
	private ArrayList<Integer> waypoint_order = new ArrayList<Integer>();
	
	public Route() {
		
	}
	
	public void addEndLocations(Collection<Coordinate> into) {
		for (Leg leg : legs) {
			into.add(leg.getEndLocation());
		}
	}
	
	public void addPolylines(Collection<Polyline> into) {
		for (Leg leg : legs) {
			leg.addPolylines(into);
		}
	}
	
	public Coordinate computeCenter() {
		double lat = 0;
		double lng = 0;
		for (int i = 0; i < legs.size(); i++) {
			lat += legs.get(i).getStartLocation().getLat();
			lng += legs.get(i).getStartLocation().getLng();
			lat += legs.get(i).getEndLocation().getLat();
			lng += legs.get(i).getEndLocation().getLng();
		}
		lat /= 2.0 * legs.size();
		lng /= 2.0 * legs.size();
		return new Coordinate(lat, lng);
	}
	
	public Bounds getBounds() {
		return bounds;
	}
	
	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

	public String getCopyrights() {
		return copyrights;
	}

	public void setCopyrights(String copyrights) {
		this.copyrights = copyrights;
	}

	public Polyline getOverview() {
		return overview_polyline;
	}

	public void setOverview(Polyline overview) {
		this.overview_polyline = overview;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public ArrayList<Integer> getWaypointOrder() {
		return waypoint_order;
	}

	public void setWaypointOrder(ArrayList<Integer> waypointOrder) {
		this.waypoint_order = waypointOrder;
	}

	public ArrayList<Leg> getLegs() {
		return legs;
	}

	public void setLegs(ArrayList<Leg> legs) {
		this.legs = legs;
	}

	public ArrayList<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(ArrayList<String> warnings) {
		this.warnings = warnings;
	}
}
