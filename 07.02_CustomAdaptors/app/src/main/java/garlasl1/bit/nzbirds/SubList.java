package garlasl1.bit.nzbirds;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SubList extends AppCompatActivity {

    protected Bird[] bird_db;
    protected Bird[] nzBirds;
    protected Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bird_db = initialiseBirds();

        Intent fetchCategory = getIntent();
        String categorySelection = fetchCategory.getStringExtra("ConservationStatus");
        ConservationStatus selected = stringToStatus(categorySelection);
        birdList(selected);
    }

    private Bird[] filterByStatus(ConservationStatus status){
        List<Bird> selection = new ArrayList<Bird>();
        for(int i=0; i < bird_db.length; i++) {
            if (bird_db[i].Status == status)
                selection.add(bird_db[i]);
        }

        Bird[] selectionArray = new Bird[selection.size()];
        return (selection.toArray(selectionArray));
    }

    private void birdList(ConservationStatus status){
        Bird[] birdArray = filterByStatus(status);
        //Custom adapter for a listview item:
        BirdArrayAdapter birdAdapter = new BirdArrayAdapter(context, R.layout.custom_listview_item, birdArray);

        //Bind list view to the custom adapter:
        ListView listView = findViewById(R.id.listV_birds);
        listView.setAdapter(birdAdapter);
        listView.setOnItemClickListener(new ListViewClickHandler());
    }


    //Returns a test array of birds
    private Bird[] initialiseBirds()
    {
        nzBirds = new Bird[8];
        nzBirds[0] = new Bird("Kea", ConservationStatus.N_ENDANGERED, getDrawable(R.drawable.kea));
        nzBirds[1] = new Bird("Kākāpō", ConservationStatus.N_CRITICAL, getDrawable(R.drawable.kakapo));
        nzBirds[2] = new Bird("Kākā", ConservationStatus.RECOVERING, getDrawable(R.drawable.kaka));
        nzBirds[3] = new Bird("Hoiho", ConservationStatus.N_ENDANGERED, getDrawable(R.drawable.hoiho));
        nzBirds[4] = new Bird("King Shag", ConservationStatus.N_ENDANGERED, getDrawable(R.drawable.kingshag));
        nzBirds[5] = new Bird("Pīwauwau", ConservationStatus.N_ENDANGERED, getDrawable(R.drawable.rockwren));
        nzBirds[6] = new Bird("Southern Brown Kiwi", ConservationStatus.N_ENDANGERED, getDrawable(R.drawable.southernbrownkiwi));
        nzBirds[7] = new Bird("Masked Booby", ConservationStatus.N_ENDANGERED, getDrawable(R.drawable.maskedbooby));
        return nzBirds;
    }

    private ConservationStatus stringToStatus(String stringValue){
        for (ConservationStatus status : ConservationStatus.values()) {
            if (status.toString() == stringValue)
                return status;
        }
        return ConservationStatus.N_ENDANGERED;
    }

    class ListViewClickHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bird clickedBird = (Bird)parent.getItemAtPosition(position);
            Toast.makeText(SubList.this, clickedBird.Name, Toast.LENGTH_LONG).show();

            ImageView birdImage = findViewById(R.id.dialog_imageview);
            birdImage.setImageDrawable(clickedBird.BirdImage);


            AlertDialog.Builder alertadd = new AlertDialog.Builder(SubList.this);
            LayoutInflater factory = LayoutInflater.from(SubList.this);
            View v = factory.inflate(R.layout.bird_selection, null);

            alertadd.setView(v);
            alertadd.show();
        }
    }
    //Creates a custom adapter for use in a list view
    public class BirdArrayAdapter extends ArrayAdapter<Bird> {

        public BirdArrayAdapter(Context context, int resource, Bird[] birds){
            super(context, resource, birds);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container){

            LayoutInflater inflater = LayoutInflater.from(context);

            View customView = inflater.inflate(R.layout.custom_listview_item,container,false);

            ImageView itemImageView = customView.findViewById(R.id.ivItemImage);
            TextView itemTextView = customView.findViewById(R.id.tvItemWords);

            Bird currentItem = getItem(position);

            itemImageView.setImageDrawable(currentItem.BirdImage);

            itemTextView.setText(currentItem.toString());
            return customView;
        }
    }

}
