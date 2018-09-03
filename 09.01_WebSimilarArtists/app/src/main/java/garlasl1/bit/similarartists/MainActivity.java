package garlasl1.bit.similarartists;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    ListView listView;
    Button searchBtn;
    TextInputEditText textInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseComponents();
    }

    private void initialiseButtonSettings(){
        searchBtn = findViewById(R.id.btn_search);
        searchBtn.setOnClickListener(new SearchHandler());

    }
    private void initialiseComponents(){
        textInput = findViewById(R.id.txtInput);
        listView = findViewById(R.id.listV_similarArtists);
        initialiseButtonSettings();
    }

    private void initialiseListView(String[] contents){
        ArrayAdapter<String> artistAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, contents);
        listView.setAdapter(artistAdapter);
    }

    private String[] JSONStringToArray(String JSONString){
        String[] artists = null;
        try{
            //convert string input to json object:
            JSONObject allData = new JSONObject(JSONString);
            JSONObject artistData = allData.getJSONObject("similarartists");
            JSONArray artistArray = artistData.getJSONArray("artist");

            //get value of "data" key (in this case, it's an array:
            int nArtists = artistArray.length();
            artists = new String[nArtists];
            for(int i=0; i<nArtists; i++){
                JSONObject currentArtist = artistArray.getJSONObject(i);
                String name = currentArtist.getString("name");
                artists[i] = name;
            }
            return artists;
        }
        catch (org.json.JSONException e){
            Toast.makeText(this, "CATCH: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public class SearchHandler implements View.OnClickListener{
        @Override
        public void onClick(View v){
            String searchTerm = textInput.getText().toString();

            AsyncAPIShowJSON APIThread = new AsyncAPIShowJSON();
            APIThread.execute(searchTerm);
        }
    }

    public class AsyncAPIShowJSON extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... pParams) {
            String JSONString = null;
            String inputString = pParams[0];
            try {
                String urlString = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar" +
                        "&artist=" + inputString +
                        "&api_key=58384a2141a4b9737eacb9d0989b8a8c&format=json&limit=10";

                //Convert URL String to URLObject
                URL URLObject = new URL(urlString);
                //Create HTTPURL Connection object via openConnection command of URLObject
                HttpURLConnection connection = (HttpURLConnection) URLObject.openConnection();
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
                while ((responseString = bufferedReader.readLine()) != null) {
                    stringBuilder = stringBuilder.append(responseString);
                }
                JSONString = stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return JSONString;
        }

        @Override
        protected void onPostExecute(String s) {
            String[] array = JSONStringToArray(s);
            initialiseListView(array);
        }
    }

}
