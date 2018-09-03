package helloworld.garlasl1.bit.randomdogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtRandomDog = findViewById(R.id.textv_randomDog);
        txtRandomDog.setText(randomDogString());
    }

    public String randomDogString() {
        String randomDogString = "";
        Random random = new Random();
        int randomIndex = random.nextInt(DogBreed.values().length);
        return (DogBreed.fromInt(randomIndex)).toString();
    }


    public enum DogBreed {
        Poodle(0),
        Labrador(1),
        SharPei(2),
        Newfoundland(3);

        private int _value;

        DogBreed(int Value){
            this._value = Value;
        }

        public int getValue() {
            return _value;
        }

        public static  DogBreed fromInt(int i){
            for (DogBreed b : DogBreed .values()){
                if (b.getValue() == i) { return b; }
            }
            return null;
            }
        }
    }






