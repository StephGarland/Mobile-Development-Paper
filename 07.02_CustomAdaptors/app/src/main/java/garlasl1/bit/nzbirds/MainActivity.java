package garlasl1.bit.nzbirds;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conservationList();
    }

    private void conservationList() {
        int n_statuses = ConservationStatus.values().length;
        ConservationStatus[] conservationStatuses = new ConservationStatus[n_statuses];
        int i = 0;
        for (ConservationStatus status : ConservationStatus.values()) {
            conservationStatuses[i] = status;
            i++;
        }

        ArrayAdapter<ConservationStatus> conservationAdapter = new ArrayAdapter<ConservationStatus>(this, android.R.layout.simple_list_item_1, conservationStatuses);
        //Bind list view to the custom adapter:
        ListView listView = findViewById(R.id.listV_birds);
        listView.setOnItemClickListener(new ListViewClickHandler());
        listView.setAdapter(conservationAdapter);
    }

    class ListViewClickHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String clickedItemString = parent.getItemAtPosition(position).toString();

            Intent goToIntent;
            goToIntent = new Intent(MainActivity.this, SubList.class);
            goToIntent.putExtra("ConservationStatus", clickedItemString);
            if(goToIntent != null)
                startActivity(goToIntent);

        }

    }



}
