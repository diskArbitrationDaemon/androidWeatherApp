package com.example.weatherapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.weatherapp.R;
import com.google.gson.Gson;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    private static final String WEATHER_API = "http://api.openweathermap.org/data/2.1/find/city?";
    private static final int RADIUS_IN_KM = 10;

	private String DEFAULT_LOADING_MESSAGE = "Loading weather data...";
	private String LOCATION_PROMPT = "Unable to determine your location. \n Please ensure your Location Services is enabled.";
    
    
    protected LocationManager locationManager;
    private LocationListener locationListener;
    protected Button retrieveLocationButton;
    private WeatherOutputAdapter weatherOutputAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
        List<Result> results = new ArrayList<Result>();
        Result empty = new Result();
        empty.setWarning(DEFAULT_LOADING_MESSAGE);
        results.add(empty);

        // Displays the loading screen before fetching any data
		weatherOutputAdapter = new WeatherOutputAdapter(MainActivity.this, R.layout.row	, results);
		setListAdapter(weatherOutputAdapter);

        downloadAndDisplayWeather();
    } 
    
    private void refreshWeatherData(){
    	downloadAndDisplayWeather();
    }
    
    private void downloadAndDisplayWeather(){
    	
    	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        /*
         * GPS provider used because the emulator does not have a network
         * provider.
         */
        
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 
                MINIMUM_TIME_BETWEEN_UPDATES, 
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                locationListener
        );
        
        
        
        //Mainly to satisfy the emulator where the network provider doesn't exist.
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null){
        	location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        
        //check that the last location is valid...
        if (location != null) {
            
            String apiAddress = constructAPI(WEATHER_API, location.getLongitude(), location.getLatitude(), RADIUS_IN_KM);
            try {
            	//connect to API
            	URL apiUrl = new URL(apiAddress);
	            GetWeatherTask getWeatherTask = new GetWeatherTask();
	            getWeatherTask.execute(apiUrl);
            } catch (Exception e){
            	e.printStackTrace();
            }
            
            
        } else {
        	
        	//no last known location was found. Prompt user to 
        	//enable location services.
        	
        	List<Result> results = new ArrayList<Result>();
            Result gpsPrompt = new Result();
            gpsPrompt.setWarning(LOCATION_PROMPT);
            results.add(gpsPrompt);
            weatherOutputAdapter.clear();
			weatherOutputAdapter.addAll(results);
			weatherOutputAdapter.notifyDataSetChanged();
    		setListAdapter(weatherOutputAdapter);
        }
        
        locationManager.removeUpdates(locationListener);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()){
    	case R.id.menu_refresh:
    		refreshWeatherData(); 
    		break;
    	}
		return false;
    	
    }
    
    private class GetWeatherTask extends AsyncTask<URL, Integer, List<Result>>{
		protected List<Result> doInBackground(URL... urls) {
			List <Result> results = new ArrayList<Result>();
			String weather = "";
			try {
				//connect to the API
				HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
	            try {
	            	InputStream in = new BufferedInputStream (urlConnection.getInputStream());
	            	weather = readStream(in);
	            	Gson gson = new Gson();
	            	//weatherResponse contains the json after deserialization
	            	OpenWeatherMapApiResponse weatherResponse = gson.fromJson(weather, OpenWeatherMapApiResponse.class);
	            	//results should contain all the weather data.
	            	results = weatherResponse.getResults();
	            } catch (Exception e){
	            	e.printStackTrace();
	            } finally {
	            	urlConnection.disconnect();
	            }
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return results;
		}
		
		protected void onPostExecute (List<Result> results){
			
			weatherOutputAdapter.clear();
			weatherOutputAdapter.addAll(results);
			weatherOutputAdapter.notifyDataSetChanged();
			
			setListAdapter(weatherOutputAdapter);
		}
    	
    }
   
    
    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
        	/*
        	 * Although it makes sense to refresh weather data here
        	 * but it's unlikely that the user will have the app
        	 * open for so long that the weather data displayed would
        	 * be irrelevant.
        	 */
        }

        public void onStatusChanged(String s, int i, Bundle b) {
        }

        public void onProviderDisabled(String s) {
        }

        public void onProviderEnabled(String s) {

        }

    }
    
    private String readStream(InputStream in) {
    	String retVal = "";
    	BufferedReader reader = null;
        try {
    	    reader = new BufferedReader(new InputStreamReader(in));
    	    String line = "";
    	    while ((line = reader.readLine()) != null) {
    	        retVal = retVal + line;
    	    }
    	} catch (IOException e) {
    	    e.printStackTrace();
        } finally {
    	    if (reader != null) {
    	        try {
    	            reader.close();
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	    }
    	}
        
        return retVal;
    } 

    
    private String constructAPI(String ApiAddr, double longitude, double latitude, int radius){
    	String finalApi;
    	
    	finalApi = ApiAddr + "lat=" + Double.toString(latitude) + "&lon=" + Double.toString(longitude) + "&cnt=1&radius=" + Integer.toString(radius);
    	return finalApi;
    }
    
}
