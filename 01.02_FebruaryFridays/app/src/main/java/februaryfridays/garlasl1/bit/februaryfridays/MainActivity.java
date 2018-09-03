package februaryfridays.garlasl1.bit.februaryfridays;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources resourceResolver = getResources();
        int datesArray[] = resourceResolver.getIntArray(R.array.FebFridays);
        String fridayPrintable = resourceResolver.getString(R.string.febFriday_text);
        
        TextView textDisplay = findViewById(R.id.textv_febFriday);
        for (int date : datesArray) {
            fridayPrintable += (" " + date + ",");
        }
        fridayPrintable = fridayPrintable.substring(0, fridayPrintable.length() - 1); //remove last comma
        textDisplay.setText(fridayPrintable);
    }
}
