package edu.hist.team3.catering.route;

public class Bounds {
	private Coordinate northeast;
	private Coordinate southwest;
	
	public Bounds() {
		
	}

	public Coordinate getNortheast() {
		return northeast;
	}

	public void setNortheast(Coordinate northeast) {
		this.northeast = northeast;
	}

	public Coordinate getSouthwest() {
		return southwest;
	}

	public void setSouthwest(Coordinate southwest) {
		this.southwest = southwest;
	}
}