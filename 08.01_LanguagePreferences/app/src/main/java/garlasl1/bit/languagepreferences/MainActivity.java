package garlasl1.bit.languagepreferences;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("demoPrefs", MODE_PRIVATE);
        prefsEditor = prefs.edit();

        loadPreferences();

        Button changePrefs = findViewById(R.id.btn_changePrefs);
        changePrefs.setOnClickListener(new LanguageClickHandler());
    }

    private void loadPreferences(){
        TextView textview = findViewById(R.id.textv_Greeting);
        String languagePreference = prefs.getString("language", null);
        if(languagePreference != null) {
            String greetingInChosenLanguage = getGreeting(languagePreference);
            textview.setText(greetingInChosenLanguage);
        }

        String colourPreference = prefs.getString("colour", null);
        if(colourPreference != null){
            int chosenColour = getColour(colourPreference);
            textview.setTextColor(chosenColour);
        }
    }


    private String getGreeting(String language){
        String greeting = "";
        switch(language){
            case "German":
                greeting = "Hallo Welt";
                break;
            case "French":
                greeting = "Bonjour Le Monde";
                break;
            case "Spanish":
                greeting = "Hola Mundo";
                break;
        }
        return  greeting;
    }

    private int getColour(String colour){
        int selection;
        switch(colour){
            case "Blue":
                selection = Color.BLUE;
                break;
            case "Green":
                selection = Color.GREEN;
                break;
            case "Pink":
                selection = Color.MAGENTA;
                break;
            default:
                selection = Color.BLACK;
        }
        return selection;
    }

    public class LanguageClickHandler implements View.OnClickListener{
        @Override
        public void onClick(View view){
            //Get language selection
            RadioGroup language_rg = findViewById(R.id.rg_language);
            int language_checkedID = language_rg.getCheckedRadioButtonId();
            RadioButton language_checkedButton = findViewById(language_checkedID);
            String checkedLanguage = language_checkedButton.getText().toString();
            prefsEditor.putString("language", checkedLanguage);

            //Get colour selection
            RadioGroup colour_rg = findViewById(R.id.rg_colour);
            int colour_checkedID = colour_rg.getCheckedRadioButtonId();
            RadioButton colour_checkedButton = findViewById(colour_checkedID);
            String checkedColor = colour_checkedButton.getText().toString();
            prefsEditor.putString("colour", checkedColor);

            prefsEditor.commit();

            loadPreferences();
        }

    }
}
