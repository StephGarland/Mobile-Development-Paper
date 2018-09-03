package garlasl1.bit.nzbirds;


import android.graphics.drawable.Drawable;

/**
 * Created by GARLA on 21/05/2018.
 */

public class Bird {
    String Name;
    ConservationStatus Status;
    Drawable BirdImage;

    public Bird(String name, ConservationStatus status, Drawable birdImage){
        Name = name;
        Status = status;
        BirdImage = birdImage;
    }

    @Override
    public String toString() {
        return (Name + ",\n" + Status);
    }
}
