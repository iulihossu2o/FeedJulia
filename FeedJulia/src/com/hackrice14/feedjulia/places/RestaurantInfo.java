package com.hackrice14.feedjulia.places;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient JSONObject base = null;
	private String baseStr;
	
	private String key;
	
	public RestaurantInfo(JSONObject base, String key) {
		this.base = base;
		this.baseStr = base.toString();
		
		this.key = key;
	}
	
	public String getName() {
		try {
			if(base == null)
				base = new JSONObject(baseStr);
			
			return base.getString("name");
		} catch (JSONException e) {
			return "[NO NAME]";
		}
	}
	
	public String getAddress() {
		try {
			if(base == null)
				base = new JSONObject(baseStr);
			
			return base.getString("vicinity");
		} catch (JSONException e) {
			return "[NO ADDRESS FOUND]";
		}
	}
	
	public String getPhoto(int maxWidth) {
		try {
			if(base == null)
				base = new JSONObject(baseStr);
			
			JSONArray photoList = base.getJSONArray("photos");
			JSONObject photoInfo = photoList.getJSONObject(0);
			
			String reference = photoInfo.getString("photo_reference");
			
			//https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CoQBegAAAFg5U0y-iQEtUVMfqw4KpXYe60QwJC-wl59NZlcaxSQZNgAhGrjmUKD2NkXatfQF1QRap-PQCx3kMfsKQCcxtkZqQ&sensor=true&key=AddYourOwnKeyHere
			String baseURL  = "https://maps.googleapis.com/maps/api/place/photo?";
			
			String photoURL = baseURL;
			photoURL += "maxwidth=" + maxWidth;
			photoURL += "&photoreference=" + reference;
			photoURL += "&sensor=true";
			photoURL += "&key=" + key;
			return photoURL;
		} catch(JSONException e) {
			return null;
		}
	}
}
