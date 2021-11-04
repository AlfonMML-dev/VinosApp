package home.izv.amml.ad.tusmejoresvinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import home.izv.amml.ad.tusmejoresvinos.data.Vino;
import home.izv.amml.ad.tusmejoresvinos.util.Csv;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName() + "xyzyx";

    private Button bt_Agregar;
    private static ArrayList<Vino> vinos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    public void initialize(){

        bt_Agregar = findViewById(R.id.bt_Agregar_Main);
        bt_Agregar.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, AgregarActivity.class);
            startActivity(intent);
        });
    }

    public static String getTAG() {
        return TAG;
    }

    public static void borrarVino(Vino vino){
        for (int i = 0; i < vinos.size(); i++) {
            if(vinos.get(i).getId() == vino.getId()){
                vinos.remove(i);
            }
        }
    }

    public static void insertarVino(Vino vino){
        boolean distinto = false;
        for (int i = 0; i < vinos.size(); i++) {
            if(vinos.get(i).getId() == vino.getId()){
                distinto = true;
            }
        }
        if (!distinto){
            vinos.add(vino);
        }
    }

    public static ArrayList<Vino> getVinos() {
        return vinos;
    }
}