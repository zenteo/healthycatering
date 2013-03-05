package edu.hist.team3.catering.route;

import java.util.ArrayList;
import java.util.Collection;

public class Routes {
	private ArrayList<Route> routes = new ArrayList<Route>();
	private String status;
	
	public Routes() {
		
	}
	
	public void addEndLocations(Collection<Coordinate> into) {
		for (Route route : routes) {
			route.addEndLocations(into);
		}
	}
	
	public void addPolylines(Collection<Polyline> into) {
		for (Route route : routes) {
			route.addPolylines(into);
		}
	}
	
	public void addOverviews(Collection<Polyline> into) {
		for (Route route : routes) {
			into.add(route.getOverview());
		}
	}
	
	public Coordinate computeCenter() {
		double lat = 0;
		double lng = 0;
		for (int i = 0; i < routes.size(); i++) {
			Coordinate coord = routes.get(i).computeCenter();
			lat += coord.getLat();
			lng += coord.getLng();
		}
		lat /= routes.size();
		lng /= routes.size();
		return new Coordinate(lat, lng);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(ArrayList<Route> routes) {
		this.routes = routes;
	}
}
