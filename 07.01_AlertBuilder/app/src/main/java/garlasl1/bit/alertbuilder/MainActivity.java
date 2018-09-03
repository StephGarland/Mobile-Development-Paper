package garlasl1.bit.alertbuilder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    protected ImageView main_image;
    protected Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The image view taking up majority of main activity:
        main_image = findViewById(R.id.imageV_pizza);
        //The 'order pizza' button and its on click handler:
        Button btn_orderPizza = findViewById(R.id.btn_orderPizza);
        btn_orderPizza.setOnClickListener(new OrderPizza_Handler());
    }

    public class OrderPizza_Handler implements View.OnClickListener{
        @Override
        public void onClick(View view)
        {
            AlertDialog.Builder alertDialogBuilder = initialiseAlertBuilder();
            // Create alert dialog from the template:
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }

        protected AlertDialog.Builder initialiseAlertBuilder(){
            //Set template for alert dialog:
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            //Set title and icon. Note: icon only appears if there is a title.
            alertDialogBuilder.setTitle(getString(R.string.confirmation_pizza));
            alertDialogBuilder.setIcon(getDrawable(R.drawable.pizza_icon));

            alertDialogBuilder.setCancelable(false);

            //Adds a positive confirmation button with custom positive string:
            String positiveString = getString(R.string.confirmation_positive);
            alertDialogBuilder.setPositiveButton(positiveString, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    //On positive click confirmation, set image to positive, dismiss dialog
                    main_image.setImageDrawable(getDrawable(R.drawable.postive_confirmation));
                    dialog.cancel();
                }
            });
            //Adds a negative confirmation button with custom positive string:
            String negativeString = getString(R.string.confirmation_negative);
            alertDialogBuilder.setNegativeButton(negativeString, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    //On negative click confirmation, set image to negative, dismiss dialog
                    main_image.setImageDrawable(getDrawable(R.drawable.negative_confirmation));
                    dialog.cancel();
                }
            });

            return alertDialogBuilder;
        }


    }
}
