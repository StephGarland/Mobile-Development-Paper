package garlasl1.bit.nzbirds;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by GARLA on 26/05/2018.
 */

public class BirdFactory {
    Bird[] bird_db;

    public BirdFactory(Context context){
        List<Bird> birdInitialisation = new ArrayList<Bird>();
        birdInitialisation.add(new Bird("Kea", ConservationStatus.N_ENDANGERED, context.getDrawable(R.drawable.kea)));
        birdInitialisation.add(new Bird("Kākāpō", ConservationStatus.N_CRITICAL, context.getDrawable(R.drawable.kakapo)));
        birdInitialisation.add(new Bird("Kākā", ConservationStatus.RECOVERING, context.getDrawable(R.drawable.kaka)));

        Bird[] initialisatonArray = new Bird[birdInitialisation.size()];
        bird_db = initialisatonArray;
    }

    public Bird[] getAll(){
        return bird_db;
    }

    public Bird[] getAllOfStatus(ConservationStatus status){
        List<Bird> selection = new ArrayList<Bird>();
        for(int i=0; i < bird_db.length; i++) {
            if (bird_db[i].Status == status)
                selection.add(bird_db[0]);
        }

        Bird[] selectionArray = new Bird[selection.size()];
        return (selection.toArray(selectionArray));
    }

}
