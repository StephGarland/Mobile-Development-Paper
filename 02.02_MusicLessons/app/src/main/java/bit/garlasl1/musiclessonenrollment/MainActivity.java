package bit.garlasl1.musiclessonenrollment;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected RadioGroup instrumentGroup;
    protected Spinner monthSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseRadioGroup();
        initialiseMonthSpinner();
    }

    private void initialiseRadioGroup() {
        //find radiogroup used to hold possible instrument selections:
        instrumentGroup = findViewById(R.id.radiog_instruments);
        //set custom listener for enrolment button:
        Button enrolmentButton = findViewById(R.id.btn_enrol);
        enrolmentButton.setOnClickListener(new enrolmentButtonListener());
    }

    private void initialiseMonthSpinner() {
        monthSpinner = findViewById(R.id.monthSpinner);
        int layoutID = android.R.layout.simple_spinner_item;
        Resources res = getResources();
        String[] months = res.getStringArray(R.array.string_array_months);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, layoutID, months);
        monthSpinner.setAdapter(monthAdapter);
    }

    protected String textOfCurrentRadioSelection() {
        //Find text of currently selected radiobutton option:
        int checkedId = instrumentGroup.getCheckedRadioButtonId();
        RadioButton checkedButton = findViewById(checkedId);
        return checkedButton.getText().toString();
    }

    protected String textOfCurrentSpinnerSelection(){
        return monthSpinner.getSelectedItem().toString();
    }

    protected void processEnrolment()
    {
        //Enrolment feedback:
        String feedbackString = getText(R.string.enrolment_feedback1) + " "
                + textOfCurrentRadioSelection() + " "
                + getText(R.string.enrolment_feedback2) + " "
                + textOfCurrentSpinnerSelection();
        TextView textViewFeedback = findViewById(R.id.txtv_feedback);
        textViewFeedback.setText(feedbackString);
    }

    public class enrolmentButtonListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            processEnrolment();
        }
    }
}
