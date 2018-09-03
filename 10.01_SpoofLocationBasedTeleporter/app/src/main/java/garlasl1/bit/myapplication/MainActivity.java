package garlasl1.bit.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    static final int locationPermissionsRequestCode = 42;
    private Context context = this;
    private AsyncAPIShowJSON APIThread;

    private LocationManager locationManager;
    private String providerName;

    private TextView txtV_latitude;
    private TextView txtV_longitude;
    private TextView txtV_nearestCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtV_latitude = findViewById(R.id.textV_latitude);
        txtV_longitude = findViewById(R.id.textV_longitude);
        txtV_nearestCity = findViewById(R.id.textV_nearestCity);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria defaultCriteria = new Criteria();
        //set criteria specs here:
        providerName = locationManager.getBestProvider(defaultCriteria, false);

        boolean locationPermissionsOk = checkLocationPermission();
        if(locationPermissionsOk){
            providerName = locationManager.getBestProvider(defaultCriteria, false);
            locationManager.requestLocationUpdates(providerName, 500, 1, new CustomLocationListener());
        }
        else {
            requestLocationPermission();
        }
    }

    public boolean checkLocationPermission(){
        int fineLocationOk = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationOk = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if((fineLocationOk != PackageManager.PERMISSION_GRANTED) || (coarseLocationOk != PackageManager.PERMISSION_GRANTED))
            return false;
        else
            return true;
    }

    public void requestLocationPermission(){
        String[] permissionsIWant = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this, permissionsIWant, locationPermissionsRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(checkLocationPermission()){
                providerName = locationManager.getBestProvider(new Criteria(), false);
                locationManager.requestLocationUpdates(providerName, 500, 1, new CustomLocationListener());
            }
        }
    }

    private void updateDisplay(float lat, float lng){
        DecimalFormat df = new DecimalFormat("###.###");
        txtV_latitude.setText(df.format(lat));
        txtV_longitude.setText(df.format(lng));
    }

    private class CustomLocationListener implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            float lat = (float)location.getLatitude();
            float lng = (float)location.getLongitude();
            updateDisplay(lat, lng);
            Float[] coordinates = new Float[]{lat,lng};
            APIThread = new AsyncAPIShowJSON();
            APIThread.execute(coordinates);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras){};
        @Override
        public void onProviderEnabled(String provider){};
        @Override
        public void onProviderDisabled(String provider){};
    }

    private String extractCityNameFromJSON(String JSONString){
       try{
            Object json = new JSONTokener(JSONString).nextValue();
            if (json instanceof JSONObject){
                JSONObject allCityData = new JSONObject(JSONString);
                return allCityData.getString("geoplugin_place");
            }
            else if (json instanceof JSONArray){
                return "No nearby city...";
            }
            else
                return "ERROR: Unknown return type";
        }
        catch (org.json.JSONException e){
            Toast.makeText(this, "CATCH: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return "ERROR: " + e.getMessage();
        }
    }

    public class AsyncAPIShowJSON extends AsyncTask<Float, Void, String> {
        @Override
        protected String doInBackground(Float... coordinates) {
            float latitude = coordinates[0];
            float longitude = coordinates[1];
            String JSONString = null;

            try{
                String urlString = "http://www.geoplugin.net/extras/location.gp?lat=" +
                        latitude + "&long=" + longitude + "&format=json";
                //Convert URL String to URLObject
                URL URLObject = new URL(urlString);
                //Create HTTPURL Connection object via openConnection command of URLObject
                HttpURLConnection connection = (HttpURLConnection)URLObject.openConnection();
                //send the URL
                connection.connect();
                //if this doesn't return 200, you don't have data:
                int responseCode = connection.getResponseCode();
                //Get an Input Stream from the HTTPURLConnection object and set if a BufferedReader
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                //Read the input. May be only one line.
                String responseString;
                StringBuilder stringBuilder = new StringBuilder();
                while((responseString = bufferedReader.readLine()) != null){
                    stringBuilder = stringBuilder.append(responseString);
                }
                JSONString = stringBuilder.toString();
            }
            catch(Exception e){e.printStackTrace();}
            return JSONString;
        }

        @Override
        protected void onPostExecute(String s) {
            txtV_nearestCity.setText(extractCityNameFromJSON(s));
        }
    }
}
