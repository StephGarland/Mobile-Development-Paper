package clicks.garlasl1.bit.normalclickvslongclick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ButtonChangeDisplay = findViewById(R.id.btn_eventTester);
        ButtonChangeDisplay.setOnClickListener(new ButtonClickHandler());
        ButtonChangeDisplay.setOnLongClickListener(new ButtonLongClickHandler());
    }

    public class ButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v){
            CharSequence clickText = getText(R.string.text_normalClick);
            Toast.makeText(getApplicationContext(), clickText, Toast.LENGTH_LONG).show();
        }
    }

    public class ButtonLongClickHandler implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            CharSequence clickText = getText(R.string.text_longClick);
            Toast.makeText(getApplicationContext(), clickText, Toast.LENGTH_LONG).show();
            return true;
        }
    }


}
