package bit.garlasl1.datapassing;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    int MIN_LENGTH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialiseReturnButton();
    }

    private void initialiseReturnButton() { //Assign custom handler to Return to Main button
        Button returnButton = findViewById(R.id.btn_return);
        returnButton.setOnClickListener(new returnButtonClickHandler());
    }

    private boolean isValidUsername(String username){//Returns true if username is longer than min specified length
        if(username.length() >= MIN_LENGTH)
            return true;
        else
            return false;
    }

    public class returnButtonClickHandler implements View.OnClickListener{ //Custom handler for Return To Main button
        @Override
        public void onClick(View view){
            Intent returnToMain = new Intent(Settings.this, MainActivity.class);

            //Assigns contents of input view to String:
            TextInputEditText inputView = findViewById(R.id.input_username);
            String username = inputView.getText().toString();
            //If input string is valid:
            if(isValidUsername(username)) {
                returnToMain.putExtra("username",username); //Pass username as extra
                startActivity(returnToMain); //Go to main activity, (passing along username)
            }
            else { //If username was not valid, prevent them returning. Annoying, but oh well
                Toast.makeText(Settings.this, getText(R.string.invalid_username), Toast.LENGTH_LONG).show();
            }
        }
    }
}
