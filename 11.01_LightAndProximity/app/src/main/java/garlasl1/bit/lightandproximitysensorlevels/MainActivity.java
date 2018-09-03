package garlasl1.bit.lightandproximitysensorlevels;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView proximityDisplay;
    TextView illuminationDisplay;
    TextView accelerometerDisplay;

    SensorManager sensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        illuminationDisplay = findViewById(R.id.output_illumination);
        proximityDisplay = findViewById(R.id.output_proximity);
        accelerometerDisplay = findViewById(R.id.output_tilts);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        proximitySensorSettings();
        lightSensorSettings();
        accelerometerSensorSettings();
    }

    private void proximitySensorSettings(){
        Sensor ProximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(ProximitySensor != null){
            proximityDisplay.setText("Proximity sensor unavailable");
            sensorManager.registerListener(new ProximitySensorListener(), ProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            proximityDisplay.setText("Proximity sensor unavailable");
        }
    }

    private void lightSensorSettings(){
        Sensor LightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(LightSensor != null){
            illuminationDisplay.setText("Light sensor available");
            sensorManager.registerListener(new LightSensorListener(), LightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            illuminationDisplay.setText("Light sensor unavailable");
        }
    }

    private void accelerometerSensorSettings(){
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(accelerometerSensor != null){
            illuminationDisplay.setText("Accelerometer available");
            sensorManager.registerListener(new AccelerometerSensorListener(), accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            accelerometerDisplay.setText("Accelerometer unavailable");
        }
    }


    private class ProximitySensorListener implements SensorEventListener{
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_PROXIMITY) {
                if(event.values[0] < 5)
                    proximityDisplay.setText("NEAR");
                else
                    proximityDisplay.setText("FAR");
            }
        }
    }

    private class LightSensorListener implements SensorEventListener{
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                illuminationDisplay.setText("" + event.values[0]);
            }
        }
    }

    private class AccelerometerSensorListener implements SensorEventListener{
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                DecimalFormat df = new DecimalFormat("###.##");
                accelerometerDisplay.setText("X: " + df.format(event.values[0])
                                            + "\nY: " + df.format(event.values[1])
                                            + "\nZ: " + df.format(event.values[2]));
            }
        }
    }
}
