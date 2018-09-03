package bit.garlasl1.a0602_dialogfragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopUpFragment extends DialogFragment {
    public interface BackgroundManipulator{
        void ChangeBackground (MainActivity.BackgroundOption backgroundOption);
    }

    public PopUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.fragment_pop_up, container);

        Button keaButton = dialogView.findViewById(R.id.btn_kea);
        keaButton.setOnClickListener(new KeaButtonHandler());
        Button tuiButton = dialogView.findViewById(R.id.btn_tui);
        tuiButton.setOnClickListener(new TuiButtonHandler());
        Button kakaButton = dialogView.findViewById(R.id.btn_kaka);
        kakaButton.setOnClickListener(new KakaButtonHandler());
        Button kakapoButton = dialogView.findViewById(R.id.btn_kakapo);
        kakapoButton.setOnClickListener(new KakapoButtonHandler());
        Button kereruButton = dialogView.findViewById(R.id.btn_kereru);
        kereruButton.setOnClickListener(new KereruButtonHandler());

        // Inflate the layout for this fragment
        return dialogView;
    }

    public class KakaButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View view){
            BackgroundManipulator main = (BackgroundManipulator)getActivity();
            main.ChangeBackground(MainActivity.BackgroundOption.Kākā);
        }
    }
    public class KakapoButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View view){
            BackgroundManipulator main = (BackgroundManipulator)getActivity();
            main.ChangeBackground(MainActivity.BackgroundOption.Kākāpō);
        }
    }
    public class KeaButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View view){
            BackgroundManipulator main = (BackgroundManipulator)getActivity();
            main.ChangeBackground(MainActivity.BackgroundOption.Kea);
        }
    }
    public class TuiButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View view){
            BackgroundManipulator main = (BackgroundManipulator)getActivity();
            main.ChangeBackground(MainActivity.BackgroundOption.Tūī);
        }
    }
    public class KereruButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View view){
            BackgroundManipulator main = (BackgroundManipulator)getActivity();
            main.ChangeBackground(MainActivity.BackgroundOption.Kererū);
        }
    }



}
