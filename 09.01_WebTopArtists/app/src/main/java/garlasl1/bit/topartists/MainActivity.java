package garlasl1.bit.topartists;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncAPIShowJSON APIThread = new AsyncAPIShowJSON();
        APIThread.execute();
    }

    private void initialiseListView(String[] contents){
        ListView list = findViewById(R.id.listV_topArtists);
        ArrayAdapter<String> artistAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, contents);
        list.setAdapter(artistAdapter);
    }

    private String[] JSONStringToArray(String JSONString){
        String[] artists = null;
        try{
            //convert string input to json object:
            JSONObject allData = new JSONObject(JSONString);
            JSONObject artistData = allData.getJSONObject("artists");
            JSONArray artistArray = artistData.getJSONArray("artist");

            //get value of "data" key (in this case, it's an array:
            int nArtists = artistArray.length();
            artists = new String[nArtists];
            for(int i=0; i<nArtists; i++){
                JSONObject currentArtist = artistArray.getJSONObject(i);
                String name = currentArtist.getString("name");
                String listenerCount = currentArtist.getString("listeners");
                //String artist = String.format("%1$-10s %2$10d", name, listenerCount);
                artists[i] = name + ": " + listenerCount;
            }
            return artists;
        }
        catch (org.json.JSONException e){
            Toast.makeText(this, "CATCH: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public class AsyncAPIShowJSON extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... voids) {
            String JSONString = null;
            try{
                String urlString = "http://ws.audioscrobbler.com/2.0/?method=chart.getTopArtists&api_key=58384a2141a4b9737eacb9d0989b8a8c&limit=20&format=json";
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
            String[] array = JSONStringToArray(s);
            initialiseListView(array);
        }
    }
}
