package garlasl1.bit.databasedemo;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase veganFoodDb;
    Spinner filterSpinner;
    Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        veganFoodDb = openOrCreateDatabase("veganFoodDB", MODE_PRIVATE, null);
        createRestaurantTable();
        //populateRestaurantTable();

        initialiseButton();
        initialiseSpinner();

        showCurrentSelection();
    }

    private void initialiseButton(){
        filterButton = findViewById(R.id.btn_go);
        filterButton.setOnClickListener(new FilterButtonHandler());
    }
    private void initialiseSpinner(){
        String[] filters = new String[]{"All","Breakfast","Lunch","Dinner"};
        filterSpinner = findViewById(R.id.spin_filterby);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, filters);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);
    }

    private void createRestaurantTable()
    {
        String createQuery = "CREATE TABLE IF NOT EXISTS tblRestaurant(" +
                "restaurantID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "restaurantName TEXT NOT NULL, " +
                "mealTime TEXT NOT NULL);";
        veganFoodDb.execSQL(createQuery);
    }
    private void populateRestaurantTable(){
        veganFoodDb.execSQL("INSERT INTO tblRestaurant VALUES(null, 'Watsons Eatery', 'Breakfast')");
        veganFoodDb.execSQL("INSERT INTO tblRestaurant VALUES(null, 'Watsons Eatery', 'Lunch')");
        veganFoodDb.execSQL("INSERT INTO tblRestaurant VALUES(null, 'Let them Eat Vegan', 'Lunch')");
        veganFoodDb.execSQL("INSERT INTO tblRestaurant VALUES(null, 'Potpourri', 'Breakfast')");
        veganFoodDb.execSQL("INSERT INTO tblRestaurant VALUES(null, 'Potpourri', 'Lunch')");
        veganFoodDb.execSQL("INSERT INTO tblRestaurant VALUES(null, 'Dog With Two Tails', 'Breakfast')");
        veganFoodDb.execSQL("INSERT INTO tblRestaurant VALUES(null, 'Dog With Two Tails', 'Lunch')");
        veganFoodDb.execSQL("INSERT INTO tblRestaurant VALUES(null, 'Dog With Two Tails', 'Dinner')");
        veganFoodDb.execSQL("INSERT INTO tblRestaurant VALUES(null, 'Formosa Delight', 'Lunch')");
        veganFoodDb.execSQL("INSERT INTO tblRestaurant VALUES(null, 'Formosa Delight', 'Dinner')");
        veganFoodDb.execSQL("INSERT INTO tblRestaurant VALUES(null, 'Hell Pizza', 'Dinner')");
    }

    private String[] arrayFromCursor(Cursor recordSet){
        int recordCount = recordSet.getCount();
        String[] displayStringArray = new String[recordCount];

        int restaurantNameIndex = recordSet.getColumnIndex("restaurantName");
        int mealIndex = recordSet.getColumnIndex("mealTime");

        recordSet.moveToFirst();

        for(int i=0; i<recordCount; i++){
            String restaurantName = recordSet.getString(restaurantNameIndex);
            String mealName = recordSet.getString(mealIndex);
            displayStringArray[i] = restaurantName + ", " + mealName;

            recordSet.moveToNext();
        }
        return displayStringArray;
    }

    private void showSelection(String selectQuery){
        Cursor recordSet = veganFoodDb.rawQuery(selectQuery,null);
        Resources res = getResources();
        ArrayAdapter<String> birdAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayFromCursor(recordSet));
        ListView listView = findViewById(R.id.listV_records);
        listView.setAdapter(birdAdapter);
    }

    protected String filterOn(String selectedFilter){
        String selectQuery = "";
        if(selectedFilter == "All")
            selectQuery = "SELECT * FROM tblRestaurant";
        else
            selectQuery = "SELECT * FROM tblRestaurant WHERE mealTime = '" + selectedFilter + "'";

        return selectQuery;
    }

    protected void showCurrentSelection(){
        String selectedFilter = filterSpinner.getSelectedItem().toString();
        showSelection(filterOn(selectedFilter));
    }
    public class FilterButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            showCurrentSelection();
        }
    }
}
