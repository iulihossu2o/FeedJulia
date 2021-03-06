package com.hackrice14.feedjulia;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.hackrice14.feedjulia.brain.DecisionMaker;
import com.hackrice14.feedjulia.places.RestaurantInfo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Place extends Activity {
	ArrayList<RestaurantInfo> restaurants;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);
		
		final Intent intent = getIntent();
		restaurants = (ArrayList<RestaurantInfo>) intent.getSerializableExtra("restaurants");
		System.out.println(restaurants.size());
		final int[] index = new int[1];
		index[0] = 0;
		
		final TextView title = (TextView) findViewById(R.id.title);
		final TextView address = (TextView) findViewById(R.id.address);
		final ImageView image = (ImageView) findViewById(R.id.image);
		final MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		
		setInfo(index[0], title, image, address, map);
		index[0]++;
		
		ImageView accept = (ImageView)findViewById(R.id.acceptResult);
        accept.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
            	finish();
            	System.exit(0);
            	
            }
        });
        
        ImageView next = (ImageView)findViewById(R.id.nextResult);
        next.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
            	setInfo(index[0], title, image, address, map);
				index[0]++;
            	
            }
        });
		
	}
	
	private void setInfo(int index, TextView title, ImageView image, TextView address, MapFragment map) {
		title.setText(restaurants.get(index).getName());
		address.setText(restaurants.get(index).getAddress());
		//map.getMap().addMarker(new MarkerOptions().position(new LatLng(restaurants.get(index).getLat(),restaurants.get(index).getLong())));
		
		//LatLngBounds bounds = new LatLngBounds.Builder()
        //.include(new LatLng(restaurants.get(index).getLat(),restaurants.get(index).getLong()))
        //.include((new LatLng(29.7169,-95.4028)))
        //.build();

        //map.getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 35));
		
		URL newurl;
		try {
			newurl = new URL(restaurants.get(index).getPhoto(100));
			Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
			image.setImageBitmap(mIcon_val);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.place, menu);
		return true;
	}

}
