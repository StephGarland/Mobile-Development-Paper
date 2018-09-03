package bit.garlasl1.colourchangingtext;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("colourResource", colorOfText());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private int colorOfText()
    {
        TextView settingsText = findViewById(R.id.txtV_settingsText);
        return settingsText.getCurrentTextColor();
    }
}
