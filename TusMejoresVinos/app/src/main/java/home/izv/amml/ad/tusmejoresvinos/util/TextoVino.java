package home.izv.amml.ad.tusmejoresvinos.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import home.izv.amml.ad.tusmejoresvinos.EditarActivity;
import home.izv.amml.ad.tusmejoresvinos.R;
import home.izv.amml.ad.tusmejoresvinos.data.Vino;

/*
* Trato los atributos de la clase Vino, de una manera más amena
*/
public class TextoVino extends androidx.appcompat.widget.AppCompatTextView implements View.OnClickListener {
    Vino vino;

    /*
    * Constructor de la clase, en el que se inserta un texto y se pone una escucha.
    */
    public TextoVino(@NonNull Context context, Vino vino) {
        super(context);
        this.vino = vino;
        this.setText(vino.getId() + ", "+ vino.getNombre() + ", " + vino.getBodega() + ", " + vino.getColor() + vino.getFecha());
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ponerId_EditText_Id_Editar_Main(vino.getId());
        EditarVino(); }

    //Con este método, se evita repetir código cada vez que se cree un Intent
    private Intent createIntent(){
        Intent intent = new Intent(this.getContext(), EditarActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("vino", this.vino);
        intent.putExtras(bundle);
        return intent;
    }

    //Se lanza la actividad correspondiente al intent creado en el método createIntent
    private void EditarVino(){
        this.getContext().startActivity(createIntent());
    }

    /*
    * En el EditText perteneciente a la interfaz activity_main se establece el id relacionado
    * con el vino que se va a editar
    */
    private void ponerId_EditText_Id_Editar_Main(long id){
        EditText eT_Id = findViewById(R.id.eT_Id_btEditar_Main);
        eT_Id.setHint(eT_Id.getHint() + " " + id);
    }
}

