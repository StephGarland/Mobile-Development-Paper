package bit.garlasl1.datapassing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseSettingsButton();
        initialiseUsernameStatus();
    }

    private void initialiseSettingsButton(){ //Assign custom handler to Settings button
        Button settingButton = findViewById(R.id.btn_settings);
        settingButton.setOnClickListener(new settingsButtonClickHandler());
    }

    private void initialiseUsernameStatus()
    {
        Intent fetchUsername = getIntent();
        username = fetchUsername.getStringExtra("username");

        if (username != null) {//If a username has been passed:
            //write it to the textview with username status
            TextView usernameView = findViewById(R.id.txtV_usernameStatus);
            usernameView.setText(username);
        }
    }

    public class settingsButtonClickHandler implements View.OnClickListener{
        @Override
        public void onClick(View view){ //On click, begin Settings activity
            Intent goToSettings = new Intent(MainActivity.this, Settings.class);
            startActivity(goToSettings);
        }
    }
}
