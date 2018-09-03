package donttype.garlasl1.bit.donttype;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputEditText textInput = findViewById(R.id.textInput);
        textInput.setOnKeyListener((new EditTextEventHandler()));
    }

    public class EditTextEventHandler implements View.OnKeyListener{
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) { //On key down only
                if (keyCode == KeyEvent.KEYCODE_AT) { //if the key pressed is the @ symbol
                    //tell the user off:
                    CharSequence feedback = Integer.toString(count) + ": " + getText(R.string.popup_text);
                    Toast.makeText(getApplicationContext(), feedback, Toast.LENGTH_LONG).show();
                }
            }
            return false;
        }
    }
}
