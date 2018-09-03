package fragmentdemo.garlasl1.bit.fragmentdemo;


import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowListFragment extends Fragment {


    public ShowListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_show_list, container, false);
        ListView birdList = fragmentView.findViewById(R.id.listView_birds);
        Resources res = getResources();
        String[] birdNames = res.getStringArray(R.array.bird_names);
        ArrayAdapter<String> birdAdaptor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, birdNames);
        birdList.setAdapter(birdAdaptor);

        return fragmentView;
    }

}
