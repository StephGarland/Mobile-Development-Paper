package username.garlasl1.bit.enterusername;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int USERNAME_LENGTH = 8; //Target length for username
    TextInputEditText textInput; //View of username input

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInput = findViewById(R.id.textInput);
        textInput.setOnKeyListener((new EditTextEventHandler())); //set username input's custom on key listener
    }

    /// Returns true if given string is the same length as given length
    private boolean validLength(String inputString, int length)
    {
        if (inputString.length() == length)
            return true;
        else
            return false;
    }

    //Provides feedback on username input
    protected void usernameFeedback()
    {
        String usernameInput = textInput.getText().toString();
        String feedback;

        if(validLength(usernameInput, USERNAME_LENGTH))
            feedback = getString(R.string.feedback_success);
        else
            feedback = getString(R.string.feedback_failure);
        feedback += " " + usernameInput;

        Toast.makeText(getApplicationContext(), feedback, Toast.LENGTH_LONG).show();
    }

    public class EditTextEventHandler implements View.OnKeyListener{

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) { //On key down only
                if (keyCode == KeyEvent.KEYCODE_ENTER) { //when enter key is pressed
                    usernameFeedback(); //provide feedback on username
                    return true;
                }
            }
            return false;
        }
    }
}
