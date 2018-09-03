package garlasl1.bit.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncAPIShowJSON APIThread = new AsyncAPIShowJSON();
        APIThread.execute();
        setImage();
    }

    private void setImage(){
        ImageView image = findViewById(R.id.imageV_main);
    }

    private String ExtractArtistImageURL(String JSONString){
        String imageURL = null;
        try{
            //convert string input to json object:
            JSONObject allData = new JSONObject(JSONString);
            JSONObject artistData = allData.getJSONObject("artists");
            JSONArray artistArray = artistData.getJSONArray("artist");
            JSONObject topArtist = artistArray.getJSONObject(0);
            JSONArray imageArray = topArtist.getJSONArray("image");
            JSONObject largestImage = imageArray.getJSONObject(imageArray.length()-1);

            return largestImage.getString("#text");
        }
        catch (org.json.JSONException e){
            Toast.makeText(this, "CATCH: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private class AsyncAPIShowJSON extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //Find the current topArtist:
            String urlString_topArtist = "http://ws.audioscrobbler.com/2.0/?method=chart.getTopArtists&api_key=58384a2141a4b9737eacb9d0989b8a8c&limit=1&format=json";
            return FetchJSONFromString(urlString_topArtist);
        }
        @Override
        protected void onPostExecute(String s) {
            String imageURL = ExtractArtistImageURL(s);
            new DownloadImageTask((ImageView) findViewById(R.id.imageV_main)).execute(imageURL);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private String FetchJSONFromString(String urlString){
        String JSONString = null;
        try{
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
}
