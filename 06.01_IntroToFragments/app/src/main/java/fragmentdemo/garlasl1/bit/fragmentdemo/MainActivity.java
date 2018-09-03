package fragmentdemo.garlasl1.bit.fragmentdemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showImage = findViewById(R.id.btn_showImage);
        showImage.setOnClickListener(new ShowImageHandler());

        Button showList = findViewById(R.id.btn_showList);
        showList.setOnClickListener(new ShowListHandler());
    }

    public class ShowImageHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Fragment dynamicFragment = new ShowImageFragment();
            FragmentManager fm = getFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.imageFragment_container, dynamicFragment);
            ft.commit();
        }
    }

    public class ShowListHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Fragment dynamicFragment = new ShowListFragment();
            FragmentManager fm = getFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.listFragment_container, dynamicFragment);
            ft.commit();
        }
    }
}
