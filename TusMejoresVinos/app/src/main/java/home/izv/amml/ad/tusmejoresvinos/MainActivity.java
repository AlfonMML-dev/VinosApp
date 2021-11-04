package home.izv.amml.ad.tusmejoresvinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import home.izv.amml.ad.tusmejoresvinos.data.Vino;
import home.izv.amml.ad.tusmejoresvinos.util.Csv;
import home.izv.amml.ad.tusmejoresvinos.util.FileIO;
import home.izv.amml.ad.tusmejoresvinos.util.TextoVino;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName() + "xyzyx";

    private Button bt_Agregar;
    private Button bt_Editar;

    private LinearLayout linearL_vinos;
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
        bt_Editar = findViewById(R.id.bt_Editar_Main);
        bt_Editar.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, EditarActivity.class);
            startActivity(intent);
        });
        linearL_vinos = findViewById(R.id.linearL_vinos);
    }

    public static String getTAG() {
        return TAG;
    }

    private void writeVinos(){
        String[] vinos = FileIO.getFileLines(getExternalFilesDir(null), getString(R.string.nombreArchivo_csv));
        if (vinos != null){
            for (String linea : vinos) {
                Vino vino = Csv.getVino(linea);
                TextoVino vt = new TextoVino(this, vino);
                linearL_vinos.addView(vt);
            }
        }
    }
}