package bit.garlasl1.colourchangingtext;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseButton();
    }

    private void initialiseButton(){
        Button changeColourButton = findViewById(R.id.btn_changeColour);
        changeColourButton.setOnClickListener(new ChangeColourButtonHandler());
    }

    public class ChangeColourButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent changeActivityIntent = new Intent(MainActivity.this, Settings.class);
            startActivityForResult(changeActivityIntent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if((requestCode == 0) && (resultCode == Activity.RESULT_OK)) {
            int result = data.getIntExtra("colourResource", 0);

            TextView text = findViewById(R.id.txtV_main);
            text.setTextColor(result);
        }
    }

}
