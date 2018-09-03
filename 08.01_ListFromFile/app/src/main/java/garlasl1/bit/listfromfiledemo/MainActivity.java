package garlasl1.bit.listfromfiledemo;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String birdlist = "birdlist.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseList();
    }

    private void initialiseList()
    {
        Resources res = getResources();
        ArrayAdapter<String> birdAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, LoadStringArray(birdlist));
        ListView listView = findViewById(R.id.listV_birdlist);
        listView.setAdapter(birdAdapter);
    }

    public ArrayList<String> LoadStringArray(String assetFileName){
        ArrayList<String> stringHolder = new ArrayList<String>();
        AssetManager manager = getAssets();

        try{
            InputStream inputStream = manager.open(assetFileName);
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            String currentString;
            while ((currentString = bufferedReader.readLine()) != null){
                stringHolder.add(currentString);
            }
            bufferedReader.close();
        }
        catch (IOException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return stringHolder;
    }
}
