package bit.garlasl1.a0602_dialogfragments;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements PopUpFragment.BackgroundManipulator {

    public enum BackgroundOption {
        Kererū (R.drawable.kereru),
        Tūī (R.drawable.tui),
        Kākāpō (R.drawable.kakapo),
        Kākā (R.drawable.kaka),
        Kea (R.drawable.kea);

        private int imageID;
        BackgroundOption(int imageID){ this.imageID = imageID;}
        int getImageID(){return imageID;}
    }

    PopUpFragment popUp;
    protected ConstraintLayout rootLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button chooseBackground = findViewById(R.id.btn_chooseBackground);
        chooseBackground.setOnClickListener(new ChooseBackgroundHandler());

        rootLayout = findViewById(R.id.main_layout);
    }

    public void ChangeBackground(BackgroundOption newChoice)
    {
        Drawable d = getResources().getDrawable(newChoice.getImageID());
        rootLayout.setBackground(d);
        popUp.dismiss();
    }

    public class ChooseBackgroundHandler implements View.OnClickListener{
        @Override
        public void onClick(View view){
            FragmentManager fm = getFragmentManager();
            popUp = new PopUpFragment();
            popUp.show(fm, "popup");
        }
    }
}
