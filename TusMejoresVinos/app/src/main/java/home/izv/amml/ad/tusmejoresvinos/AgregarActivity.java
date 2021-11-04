package home.izv.amml.ad.tusmejoresvinos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import home.izv.amml.ad.tusmejoresvinos.data.Vino;
import home.izv.amml.ad.tusmejoresvinos.util.Csv;
import home.izv.amml.ad.tusmejoresvinos.util.FileIO;

public class AgregarActivity extends AppCompatActivity {

    private Context contexto;
    private EditText eT_Id, eT_Nombre, eT_Bodega, eT_Color, eT_Origen, eT_Graduacion, eT_Fecha;
    private EditText[] campos = new EditText[7];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_activity);
        initialize();
    }

    /*
     * Se instancian las variables declaradas al principio de la clase.
     * A los botones se les pone una escucha.
     */
    private void initialize() {
        eT_Id = findViewById(R.id.eT_Id_Agregar);
        eT_Nombre = findViewById(R.id.eT_Nombre_Agregar);
        eT_Bodega = findViewById(R.id.eT_Bodega_Agregar);
        eT_Color = findViewById(R.id.eT_Color_Agregar);
        eT_Origen = findViewById(R.id.eT_Origen_Agregar);
        eT_Graduacion = findViewById(R.id.eT_Graduacion_Agregar);
        eT_Fecha = findViewById(R.id.eT_Fecha_Agregar);

        campos[0] = eT_Id;
        campos[1] = eT_Nombre;
        campos[2] = eT_Bodega;
        campos[3] = eT_Color;
        campos[4] = eT_Origen;
        campos[5] = eT_Graduacion;
        campos[6] = eT_Fecha;

        contexto = this;
        Button bt_Agregar = findViewById(R.id.bt_Agregar_Agregar);
        bt_Agregar.setOnClickListener(view -> {
            agregarVino();
        });
        Button bt_Cancelar = findViewById(R.id.bt_Cancelar_Agregar);
        bt_Cancelar.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    /*
    * Método que añade un vino al archivo csv
    */
    private void agregarVino() {
        if(camposRellenos()){
            Vino vino = obtenerVino();
            if(!existeVino(vino)){
                String lineaCSV = Csv.getCsv(vino);
                FileIO.writeLine(getExternalFilesDir(null), getString(R.string.nombreArchivo_csv), lineaCSV);
                Toast.makeText(this, "Vino insertado con éxito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else{
                Toast.makeText(contexto, "Ya existe el vino", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(contexto, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    * Comprueba que todos los EditText están rellenados
    * Devuelve false si algún campo se encuentra sin rellenar, true si se han rellenado todos.
    */
    private boolean camposRellenos(){
        boolean condicion = true;
        for (EditText campo: campos) {
            if(campo.getText().toString().isEmpty()){
                condicion = false;
            }
        }
         return condicion;
    }

    /*
    * Comprueba si el vino que se va a meter en el archivo csv ya existe, para así evitar
    * introducirlo.
    */
    private boolean existeVino(Vino vino){
        boolean condicion = false;
        for (Vino v: MainActivity.getListaVinos()) {
            if(v.getId() == vino.getId()){
                condicion = true;
            }
        }
        return condicion;
    }

    /*
    * Método que devuelve un objeto de tipo Vino, a partir de los valores puestos en los EditText
    */
    private Vino obtenerVino(){
        Vino v = new Vino();
        try {
            v.setId(Long.parseLong(campos[0].getText().toString().trim()));
        } catch(NumberFormatException e){
            Toast.makeText(contexto, "Error al transformar la cadena a número", Toast.LENGTH_SHORT).show();
        }

        v.setNombre(campos[1].getText().toString().trim());
        v.setBodega(campos[2].getText().toString().trim());
        v.setColor(campos[3].getText().toString().trim());
        v.setOrigen(campos[4].getText().toString().trim());

        try {
            v.setGraduacion(Double.parseDouble(campos[5].getText().toString()));
        } catch (NumberFormatException e) {
            Toast.makeText(contexto, "Error al obtener la graduación", Toast.LENGTH_SHORT).show();
        }
        try {
            v.setFecha(Integer.parseInt(campos[6].getText().toString()));
        } catch (NumberFormatException e) {
            Toast.makeText(contexto, "Error al obtener la fecha", Toast.LENGTH_SHORT).show();
        }

        return v;
    }
}
