package bit.garlasl1.welcometodunedin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Outdoors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);

        TextView titleView = findViewById(R.id.txtV_subcategory);
        titleView.setText(getText(R.string.outdoor_title));
        ImageView imageView = findViewById(R.id.imgV_subcategory);
        imageView.setImageDrawable(getDrawable(R.drawable.outdoor));
    }
}
