package garlasl1.bit.teleporter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Random random;
    Button btn_teleport;
    TextView longitudeDisplay;
    TextView latitudeDisplay;
    TextView nearestCityDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = new Random();
        initialiseComponents();
    }

    private void initialiseComponents(){
        longitudeDisplay = findViewById(R.id.textV_longitude);
        latitudeDisplay = findViewById(R.id.textV_latitude);
        nearestCityDisplay = findViewById(R.id.textV_nearestCity);
        initialiseTeleportButton();
    }
    private void initialiseTeleportButton(){
        btn_teleport = findViewById(R.id.btn_teleport);
        btn_teleport.setOnClickListener(new TeleportButtonListener());
    }

    public class TeleportButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            teleportToRandomLocation();
        }
    }

    private void teleportToRandomLocation(){
        float latitude = randomiseLatitude();
        float longitude = randomiseLongitude();

        AsyncAPIShowJSON APIThread = new AsyncAPIShowJSON();
        Float[] coordinates = new Float[]{latitude,longitude};
        APIThread.execute(coordinates);
        updateDisplay(latitude, longitude);
    }

    private void updateDisplay(float latitude, float longitude){
        DecimalFormat df = new DecimalFormat("###.###");
        latitudeDisplay.setText(df.format(latitude));
        longitudeDisplay.setText(df.format(longitude));
    }
    //Longitude ranges from -180 to +180
    private float randomiseLongitude(){
        return randomiseFloat(-180,180);
    }
    //Latitude ranges from -90 to +90
    private float randomiseLatitude(){
        return randomiseFloat(-90,90);
    }

    private float randomiseFloat(int min, int max){
        return min + random.nextFloat() * (max - min);
    }

    private String extractCityNameFromJSON(String JSONString){
        try{
            Object json = new JSONTokener(JSONString).nextValue();
            if (json instanceof JSONObject){
                JSONObject allCityData = new JSONObject(JSONString);
                return allCityData.getString("geoplugin_place");
            }
            else if (json instanceof JSONArray){
                teleportToRandomLocation();
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
            nearestCityDisplay.setText(extractCityNameFromJSON(s));
        }
    }
}
