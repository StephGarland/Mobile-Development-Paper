package garlasl1.bit.dunedinevents;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String assetFileName = "dunedin_events_2017.json";
        AssetManager am = getAssets();

        try {
            //Read JSON string from file
            InputStream inputStream = am.open(assetFileName);
            int fileSizeInBytes = inputStream.available();
            byte[] JSONBuffer = new byte[fileSizeInBytes];
            inputStream.read(JSONBuffer);
            inputStream.close();
            String JSONInput = new String(JSONBuffer);

            try{
                //convert string input to json object:
                JSONObject allData = new JSONObject(JSONInput);
                JSONObject eventData = allData.getJSONObject("events");
                JSONArray eventArray = eventData.getJSONArray("event");

                //get value of "data" key (in this case, it's an array:
                int nEvents = eventArray.length();
                String[] eventTitles = new String[nEvents];
                for(int i=0; i<nEvents; i++){
                    JSONObject currentEvent = eventArray.getJSONObject(i);
                    String title = currentEvent.getString("title");
                    eventTitles[i] = title;
                }

                ArrayAdapter<String> eventAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventTitles);
                ListView listView = findViewById(R.id.listV_event);
                listView.setAdapter(eventAdaptor);
            }
            catch (org.json.JSONException e){
                Toast.makeText(this, "JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        catch (IOException e){
            Toast.makeText(this, "IO: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
}
