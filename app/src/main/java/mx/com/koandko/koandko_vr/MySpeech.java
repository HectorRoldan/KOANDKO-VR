package mx.com.koandko.koandko_vr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;

import static android.widget.Toast.makeText;

/**
 * Created by hgutierrez on 8/11/17.
 */

public class MySpeech extends AppCompatActivity implements RecognitionListener {
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 100;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private void accederCamara() {
        //si la API 23 a mas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //Habilitar permisos para la version de API 23 a mas
            int verificarPermisoReadContacts = ContextCompat
                    .checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

            //Verificamos si el permiso no existe
            if (verificarPermisoReadContacts != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

            } else {
                //Si el permiso ya fue concedido abrimos pocedemos a la actividad
                llamar();
            }
        } else {
            //Si la API es menor a 23 - procedemos a la actividad
            llamar();
        }
    }

    private void mostrarExplicacion() {
        new AlertDialog.Builder(this)
                .setTitle("Autorización")
                .setMessage("Necesito permiso para acceder a la cámara de tu dispositivo.")
                //Si afirma
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
                        }
                    }
                })
                //Si niega
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Finalizamos la actividad
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Si dice que si
                llamar();
            } else {
                //Si niega
                mostrarExplicacion();
            }
        }
    }

    public void llamar(){
        makeText(getApplicationContext(), "Yeah!!!!!!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {

    }

    @Override
    public void onResult(Hypothesis hypothesis) {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onTimeout() {

    }
}
