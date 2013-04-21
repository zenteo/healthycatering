package edu.hist.team3.catering.route;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

import eu.nicecode.tsp.TspService;
import eu.nicecode.tsp.impl.GTspService;
// Google API Tsp Service

public class RouteToolkit {
	private static RouteToolkit instance = new RouteToolkit();
	private TspService tspService = new GTspService();
	private Gson gson = new Gson();
	
	private RouteToolkit() {
	}
	
	public static RouteToolkit getInstance() {
		return instance;
	}
	
	public ArrayList<String> rearrange(ArrayList<String> locations, String firstAddress) {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).equals(firstAddress)) {
				for (int j = i; j < locations.size(); j++) {
					ret.add(locations.get(j));
				}
				for (int j = 0; j < i; j++) {
					ret.add(locations.get(j));
				}
				return ret;
			}
		}
		return locations;
	}
	
	public ArrayList<String> computeShortest(Collection<String> locations) {
		ArrayList<String> ret = new ArrayList<String>();
		Collection<String> route = tspService.calculateRoute(locations);
		if (route == null) {
			return null;
		}
		ret.addAll(route);
		return ret;
	}
	
	public ArrayList<String> computeShortestLoop(Collection<String> locations) {
		ArrayList<String> ret = computeShortest(locations);
		if (ret != null && ret.size() > 0) {
			ret.add(ret.get(0)); // It's a loop!
		}
		return ret;
	}
	
	public Routes fetchDirections(Collection<String> locations) {
		Routes ret = new Routes();
		Iterator<String> it = locations.iterator();
		ret.setStatus("OK");
		if (it.hasNext()) {
			String current = it.next();
			while (it.hasNext()) {
				String to = it.next();
				Routes temp = fetchDirections(current, to);
				current = to;
				if (temp != null && temp.getStatus().equals("OK")) {
					ret.getRoutes().addAll(temp.getRoutes());
				}
				else {
					ret.setStatus("ERROR");
				}
			}
		}
		return ret;
	}
	
	public Routes fetchDirections(String origin, String destination) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("http://maps.googleapis.com/maps/api/directions/json?");
		urlBuilder.append("origin=");
		try {
			urlBuilder.append(URLEncoder.encode(origin, "UTF-8"));
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		urlBuilder.append("&destination=");
		try {
			urlBuilder.append(URLEncoder.encode(destination, "UTF-8"));
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		urlBuilder.append("&sensor=false");
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setUseCaches(true);
			
			StringBuilder content = new StringBuilder();
			
			try (InputStreamReader inputReader = new InputStreamReader(connection.getInputStream())) {
				try (BufferedReader reader = new BufferedReader(inputReader)) {
					String line;
					while ((line = reader.readLine()) != null) {
						content.append(line);
						content.append('\n');
					}
				}
			}
			connection.disconnect();
			
			String json = content.toString();
			return gson.fromJson(json, Routes.class);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public BufferedImage fetchMap(Coordinate center, Integer zoom, Collection<Coordinate> markers, Collection<Polyline> paths) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("http://maps.googleapis.com/maps/api/staticmap?");
		if (center != null) { // null means auto-center
			urlBuilder.append("center=");
			urlBuilder.append(center.toString());
		}
		if (zoom != null) { // null means auto-zoom
			urlBuilder.append("&zoom=");
			urlBuilder.append(zoom);
		}
		urlBuilder.append("&size=640x640");
		urlBuilder.append("&scale=2");
		urlBuilder.append("&format=png32");
		urlBuilder.append("&maptype=hybrid");
		if (markers != null) {
			int counter = 1;
			for (Coordinate coord : markers) {
				urlBuilder.append("&markers=");
				urlBuilder.append("label:");
				urlBuilder.append(counter++);
				//urlBuilder.append("|size:small");
				urlBuilder.append('|');
				urlBuilder.append(coord.toString());
			}
		}
		if (paths != null) {
			for (Polyline poly : paths) {
				urlBuilder.append("&path=weight:3|color:blue|enc:");
				urlBuilder.append(poly.getPoints());
			}
		}
		urlBuilder.append("&sensor=false");
		try {
			URL url = new URL(urlBuilder.toString());
			return ImageIO.read(url);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public BufferedImage fetchMap(Routes routes) {
		ArrayList<Coordinate> markers = new ArrayList<Coordinate>();
		ArrayList<Polyline> paths = new ArrayList<Polyline>();
		routes.addEndLocations(markers);
		routes.addOverviews(paths);
		return fetchMap(null, null, markers, paths);
	}
}
