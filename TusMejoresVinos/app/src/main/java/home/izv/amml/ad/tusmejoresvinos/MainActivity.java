package home.izv.amml.ad.tusmejoresvinos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import home.izv.amml.ad.tusmejoresvinos.data.Vino;
import home.izv.amml.ad.tusmejoresvinos.util.Csv;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName() + "xyzyx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    public void initialize(){
        Vino v = new Vino(0,"Betis", "Man que pierda", "Verde", "Benito Villamarín", 15.2, 2015);
        String csv = Csv.getCsv(v);
        Log.v(TAG, csv);
        Vino v2 = Csv.getVino(csv);
        Log.v(TAG, v2.toString());
    }

    public static String getTAG() {
        return TAG;
    }
}