package edu.hist.team3.catering.route;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Coordinate {
	private double lat;
	private double lng;
	
	public Coordinate() {
		
	}
	
	public Coordinate(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.#####", new DecimalFormatSymbols(Locale.US));
		return format.format(lat) + "," + format.format(lng);
	}
}