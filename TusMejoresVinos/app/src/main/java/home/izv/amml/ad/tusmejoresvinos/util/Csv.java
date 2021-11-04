package home.izv.amml.ad.tusmejoresvinos.util;

import android.util.Log;

import home.izv.amml.ad.tusmejoresvinos.MainActivity;
import home.izv.amml.ad.tusmejoresvinos.data.Vino;

public class Csv {
    public static Vino getVino(String str){
        String[] atributos = str.split(";");
        Vino v = null;
        if(atributos.length <= 7) {
            v = new Vino();
            try {
                v.setId(Long.parseLong(atributos[0].trim()));
            } catch(NumberFormatException e){
                Log.v(MainActivity.getTAG(), e.toString());
                System.out.println("\n" + "Error al transformar la cadena a número");
            }

            v.setNombre(atributos[1].trim());
            v.setBodega(atributos[2].trim());
            v.setColor(atributos[3].trim());
            v.setOrigen(atributos[4].trim());
            try {
                v.setGraduacion(Double.parseDouble(atributos[5].trim()));
            } catch (NumberFormatException e) {
                Log.v(MainActivity.getTAG(), e.toString());
                System.out.println("\n" + "Error al obtener la graduación");
            }
            try {
                v.setFecha(Integer.parseInt(atributos[6].trim()));
            } catch (NumberFormatException e) {
                Log.v(MainActivity.getTAG(), e.toString());
                System.out.println("\n" + "Error al obtener la fecha");
            }
        }
        return v;
    }

    public static String getCsv(Vino v){
        return  v.getId() + "; " +
                v.getNombre() + "; " +
                v.getBodega() + "; " +
                v.getColor() + "; "  +
                v.getOrigen() + "; " +
                v.getGraduacion() + "; " +
                v.getFecha() + ";" + "\n";
    }
}
