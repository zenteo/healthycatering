package edu.hist.team3.catering.route;

public class Step {
	private TextValue distance;
	private TextValue duration;
	private Coordinate end_location;
	private String html_instructions;
	private Polyline polyline;
	private Coordinate start_location;
	private String travel_mode;
	
	public Step() {
		
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

	public String getHtmlInstructions() {
		return html_instructions;
	}

	public void setHtmInstructions(String htmlInstructions) {
		this.html_instructions = htmlInstructions;
	}

	public Polyline getPolyline() {
		return polyline;
	}

	public void setPolyline(Polyline polyline) {
		this.polyline = polyline;
	}

	public Coordinate getStartLocation() {
		return start_location;
	}

	public void setStartLocation(Coordinate startLocation) {
		this.start_location = startLocation;
	}

	public String getTravelMode() {
		return travel_mode;
	}

	public void setTravelMode(String travelMode) {
		this.travel_mode = travelMode;
	}

	public Coordinate getEndLocation() {
		return end_location;
	}

	public void setEndLocation(Coordinate endLocation) {
		this.end_location = endLocation;
	}
}