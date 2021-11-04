package home.izv.amml.ad.tusmejoresvinos.util;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import home.izv.amml.ad.tusmejoresvinos.MainActivity;

import java.io.File;
import java.io.*;


//Clase que cuenta con métodos para leer de un archivo y escribir en él
public class FileIO extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName() + "xyzyx";

    /* Eliminamos una línea de nuestro archivo, pasando todos los registros menos el seleccionado
     * a un archivo temporal. Una vez hecho, eliminamos el original y asignamos al temporal
     * el nombre del original.
     */
    public static boolean deleteLine(File file, String fileName, String id){
        File f = new File(file, fileName);
        File f2 = new File(file, "temp.tmp");
        String str[];
        String tmp;
        try {
            FileWriter fw = new FileWriter(f2);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while((linea = br.readLine()) != null){
                str = linea.split(";");
                if(!id.equals(str[0])){
                    tmp = linea;
                    fw.write(tmp);
                    fw.write("\n");
                    fw.flush();
                }
            }
            fw.close();
            br.close();

            f.delete();
            f2.renameTo(f);

            return true;
        } catch (Exception e){
            return false;
        }
    }

    //Leemos todas las líneas del archivo
    public static String[] getFileLines(File file, String fileName){
        File f = new File(file, fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            String cache = "";
            while ((linea = br.readLine()) != null) {
                cache += linea + "%";
            }
            br.close();
            return cache.split("%");
        } catch (IOException e) {
            Log.v(TAG, e.toString());
        }
        return null;
    }

    //Escribimos una línea añadiéndola a nuestro archivo
    public static boolean writeLine(File file, String filename, String line){
        File f = new File(file, filename);
        FileWriter fw;
        try {
            fw = new FileWriter(f, true);
            fw.write(line);
            fw.flush();
            fw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
