package bit.garlasl1.welcometodunedin;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseSubcategoryView();
        ListView subcategoryView = findViewById(R.id.left_drawer);
        subcategoryView.setOnItemClickListener(new ListViewClickHandler());
    }

    private void initialiseSubcategoryView() {
        Resources res = getResources();
        ArrayAdapter<String> subcategoryAdaptor = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, res.getStringArray(R.array.subcategories));
        ListView subcategoryView = findViewById(R.id.left_drawer);

        subcategoryView.setAdapter(subcategoryAdaptor);
    }

    public class ListViewClickHandler implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String clickedItemString = parent.getItemAtPosition(position).toString();

            Intent goToIntent;
            switch(clickedItemString) {
                case "Dining":
                    goToIntent = new Intent(MainActivity.this, Dining.class);
                    break;
                case "Events":
                    goToIntent = new Intent(MainActivity.this, Events.class);
                    break;
                case "Indoors":
                    goToIntent = new Intent(MainActivity.this, Indoors.class);
                    break;
                case "Outdoors":
                    goToIntent = new Intent(MainActivity.this, Outdoors.class);
                    break;
                default:
                    goToIntent = null;
            }
            if(goToIntent != null)
                startActivity(goToIntent);
        }

    }

}
