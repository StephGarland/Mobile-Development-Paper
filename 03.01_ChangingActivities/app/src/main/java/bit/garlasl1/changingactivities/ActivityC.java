package bit.garlasl1.changingactivities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        TextView activityDescription = findViewById(R.id.textv_description);
        activityDescription.setText(R.string.description_activityC);

        Button changeActivityButton = findViewById(R.id.btn_changeActivity);
        changeActivityButton.setOnClickListener(new ChangeActivityButtonClickHandler());
    }

    public class ChangeActivityButtonClickHandler implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Uri goMakeThings = Uri.parse(getString(R.string.uri_makeThings));
            Intent makeThingsIntent = new Intent(Intent.ACTION_VIEW, goMakeThings);
            startActivity(makeThingsIntent);
        }
    }
}
