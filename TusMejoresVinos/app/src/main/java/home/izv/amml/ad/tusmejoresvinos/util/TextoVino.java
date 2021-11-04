package home.izv.amml.ad.tusmejoresvinos.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import home.izv.amml.ad.tusmejoresvinos.EditarActivity;
import home.izv.amml.ad.tusmejoresvinos.data.Vino;

//Con esta clase a la hora de mostar los registros de vinos en pantalla,
// puedo tratarlos de una mejor manera
public class TextoVino extends androidx.appcompat.widget.AppCompatTextView implements View.OnClickListener {
    Vino vino;

    public TextoVino(@NonNull Context context, Vino vino) {
        super(context);
        this.vino = vino;
        this.setText(vino.getId() + ", "+ vino.getNombre() + ", " + vino.getBodega() + ", " + vino.getColor() + vino.getFecha());
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) { EditarVino(); }

    private void EditarVino(){
        Intent intent = new Intent(this.getContext(), EditarActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("vino", this.vino);
        intent.putExtras(bundle);
        this.getContext().startActivity(intent);
    }
}

