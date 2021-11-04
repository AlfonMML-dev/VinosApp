package home.izv.amml.ad.tusmejoresvinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

import home.izv.amml.ad.tusmejoresvinos.data.Vino;
import home.izv.amml.ad.tusmejoresvinos.util.Csv;
import home.izv.amml.ad.tusmejoresvinos.util.FileIO;
import home.izv.amml.ad.tusmejoresvinos.util.TextoVino;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName() + "xyzyx";

    private ArrayList<Vino> listaVinos = new ArrayList<>();
    private Button bt_Agregar;
    private Button bt_Editar;
    private Context contexto;
    private EditText eT_Id;

    private LinearLayout linearL_vinos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    /*
    * Lee los vinos del archivo csv y los inserta el LinearLayout contenido en el ScrollView
    * de la interfaz activity_main.
    * Además añade los vinos a una lista de vinos del tipo ArrayList.
    */
    private void escribirVinos(){
        String[] vinos = FileIO.getFileLines(getExternalFilesDir(null), getString(R.string.nombreArchivo_csv));
        if (vinos != null){
            for (String linea : vinos) {
                Vino vino = Csv.getVino(linea);
                listaVinos.add(vino);
                TextoVino vt = new TextoVino(this, vino);
                linearL_vinos.addView(vt);
            }
        }
    }

    public static String getTAG() {
        return TAG;
    }

    /*
    * Se instancian las variables declaradas al principio de la clase.
    * A los botones se les pone una escucha y se realiza una llamada al método escribirVinos().
    */
    public void initialize(){

        bt_Agregar = findViewById(R.id.bt_Agregar_Main);
        bt_Agregar.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, AgregarActivity.class);
            startActivity(intent);
        });
        bt_Editar = findViewById(R.id.bt_Editar_Main);
        bt_Editar.setOnClickListener((View view) -> {
            seleccionarVinoPorId();
        });
        contexto = this;
        eT_Id = findViewById(R.id.eT_Id_btEditar_Main);
        linearL_vinos = findViewById(R.id.linearL_vinos);
        escribirVinos();
    }

    /*
    * Se da la opción de editar un vino, escribiendo un id y pulsando el botón bt_Editar_Main
    * de la interfaz activity_main
    */
    private void seleccionarVinoPorId(){
        for (int i = 0; i < listaVinos.size(); i++) {
            if(listaVinos.get(i).getId() == Long.parseLong(eT_Id.getText().toString())){
                TextoVino vt = new TextoVino(this, listaVinos.get(i));
                Intent intencion = vt.createIntent(contexto, EditarActivity.class);
                contexto.startActivity(intencion);
            }
        }
    }
}