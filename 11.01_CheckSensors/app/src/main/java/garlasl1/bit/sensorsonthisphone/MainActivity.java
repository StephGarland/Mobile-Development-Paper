package garlasl1.bit.sensorsonthisphone;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        int nSensors = sensorList.size();
        String[] sensorNames = new String[nSensors];
        for(int i = 0; i < nSensors; i++){
            sensorNames[i] = sensorList.get(i).getName();
        }

        ListView listView = findViewById(R.id.listV_sensors);
        ArrayAdapter<String> sensorAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sensorNames);
        listView.setAdapter(sensorAdapter);
    }
}
