package home.izv.amml.ad.tusmejoresvinos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import home.izv.amml.ad.tusmejoresvinos.data.Vino;
import home.izv.amml.ad.tusmejoresvinos.util.Csv;
import home.izv.amml.ad.tusmejoresvinos.util.FileIO;

public class EditarActivity extends AppCompatActivity {

    private Button bt_Eliminar, bt_Cancelar, bt_Editar;
    private Context contexto;
    private EditText eT_Id, eT_Nombre, eT_Bodega, eT_Color, eT_Origen, eT_Graduacion, eT_Fecha;
    private EditText[] campos = new EditText[7];
    private Vino vino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    /*
     * Devuelve true si ha se podido borrar el vino del archivo csv, false si por el contrario no.
     */
    private boolean borrarLinea(){
        return FileIO.deleteLine(getExternalFilesDir(null), getString(R.string.nombreArchivo_csv), Long.toString(vino.getId()));
    }

    /*
    * Método que se encarga de editar un vino, llamando a otros métodos para que realicen el proceso.
    */
    private void editarRegistro(){
        if (editarVino()){
            if (sobreEscribirLinea()){
                Toast.makeText(this, "Se ha editado el vino", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No se ha podido editar el vino indicado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Erro al editar el vino. Compruebe que ha rellenado correctamente los campos", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Método que devuelve true o false, dependiendo de si consigue modificar el objeto
     * Vino (obtenido del Bundle), a partir de los valores puestos en los EditText.
     * True si lo consigue, false si por el contrario no.
     */
    private boolean editarVino(){
        boolean resultado = true;
        String[] atributos = new String[campos.length];
        for (int i = 0; i < atributos.length; i++) {
            atributos[i] = campos[i].getText().toString();
        }
        try {
            vino.setId(Long.parseLong(atributos[0].trim()));
        } catch(NumberFormatException e){
            Toast.makeText(contexto, "Error al transformar la cadena a número", Toast.LENGTH_SHORT).show();
            resultado = false;
        }

        vino.setNombre(atributos[1].trim());
        vino.setBodega(atributos[2].trim());
        vino.setColor(atributos[3].trim());
        vino.setOrigen(atributos[4].trim());

        try {
            vino.setGraduacion(Double.parseDouble(atributos[5].trim()));
        } catch (NumberFormatException e) {
            Toast.makeText(contexto, "Error al obtener la graduación", Toast.LENGTH_SHORT).show();
            resultado = false;
        }
        try {
            vino.setFecha(Integer.parseInt(atributos[6].trim()));
        } catch (NumberFormatException e) {
            Toast.makeText(contexto, "Error al obtener la fecha", Toast.LENGTH_SHORT).show();
            resultado = false;
        }

        return resultado;
    }

    /*
     * Devuelve true si ha se podido escribir el vino en el archivo csv, false si por el contrario no.
     */
    private boolean escribirLinea(){
        return FileIO.writeLine(getExternalFilesDir(null), getString(R.string.nombreArchivo_csv), Csv.getCsv(vino));
    }

    /*
     * Se instancian las variables declaradas al principio de la clase.
     * A los botones se les pone una escucha.
     */
    private void initialize() {
        Bundle bundle = getIntent().getExtras();
        vino = bundle.getParcelable("vino");

        bt_Eliminar = findViewById(R.id.bt_Eliminar_Editar);
        bt_Eliminar.setOnClickListener((View view) -> {
            if(borrarLinea()){
                Toast.makeText(contexto, "Vino borrado con éxito", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(contexto, "Error al borrar el vino", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        bt_Cancelar = findViewById(R.id.bt_Cancelar_Editar);
        bt_Cancelar.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        bt_Editar = findViewById(R.id.bt_Editar_Editar);
        bt_Editar.setOnClickListener((View view) -> {
            editarRegistro();
        });


        eT_Id = findViewById(R.id.eT_Id_Editar);
        eT_Id.setText(String.valueOf(vino.getId()));
        eT_Id.setEnabled(false);
        eT_Nombre = findViewById(R.id.eT_Nombre_Editar);
        eT_Nombre.setText(vino.getNombre());
        eT_Bodega = findViewById(R.id.eT_Bodega_Editar);
        eT_Bodega.setText(vino.getBodega());
        eT_Color = findViewById(R.id.eT_Color_Editar);
        eT_Color.setText(vino.getColor());
        eT_Origen = findViewById(R.id.eT_Origen_Editar);
        eT_Origen.setText(vino.getOrigen());
        eT_Graduacion = findViewById(R.id.eT_Graduacion_Editar);
        eT_Graduacion.setText(String.valueOf(vino.getGraduacion()));
        eT_Fecha = findViewById(R.id.eT_Fecha_Editar);
        eT_Fecha.setText(String.valueOf(vino.getFecha()));

        campos[0] = eT_Id;
        campos[1] = eT_Nombre;
        campos[2] = eT_Bodega;
        campos[3] = eT_Color;
        campos[4] = eT_Origen;
        campos[5] = eT_Graduacion;
        campos[6] = eT_Fecha;

        contexto = this;
    }

    /*
    * Método que para editar un vino del archivo csv, primero lo borra y luego lo vuelve a añadir.
    */
    private boolean sobreEscribirLinea() {
        boolean seHaBorrado = borrarLinea();
        if (seHaBorrado){
            boolean seHaEscrito = escribirLinea();
            if (seHaEscrito){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
}
