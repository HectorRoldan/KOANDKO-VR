package mx.com.koandko.koandko_vr;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.zxing.Result;

import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.widget.Toast.makeText;

/**
 * Created by hgutierrez on 8/11/17.
 */

public class MyClassPocket extends Activity implements ZXingScannerView.ResultHandler {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private ZXingScannerView scannerView;

    public void onCreate(Bundle savedInstance) {
        //int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);

        super.onCreate(savedInstance);

        accederCamara();
    }

    private void accederCamara() {
        //si la API 23 a mas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            //Habilitar permisos para la version de API 23 a mas

            int verificarPermisoReadContacts = ContextCompat
                    .checkSelfPermission(this, Manifest.permission.CAMERA);
            //Verificamos si el permiso no existe

            if (verificarPermisoReadContacts != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);


            } else {
                //Si el permiso ya fue concedido abrimos pocedemos a la actividad
                //abrirIntentContactos();
                //System.out.println("El permiso ya fue concedido");
                grabar();
                //abrirIntentContactos();
            }

        } else {
            //Si la API es menor a 23 - abrimos en intent de contactos

            grabar();
            //abrirIntentContactos();
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
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
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
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Si dice que si
                grabar();
            } else {
                //Si niega
                mostrarExplicacion();
            }
        }
    }

    public void grabar() {
        makeText(getApplicationContext(), "Yeah!!!!!!", Toast.LENGTH_SHORT).show();
    }

    public void scannerQR() {
        // System.out.println("H8i");

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();

    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado del scanner ");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //result.toString()
        if (result.getText().equals("http://192.168.100.107/hector/three/gyroscope/resources/buyLaptop.MP4")) {
            Intent intent = new Intent(this, MainMaterial360.class);
            startActivity(intent);
            //System.out.println("HiEquals");
        } else {
            Intent intent = new Intent(this, Main3D.class);
            startActivity(intent);
            //System.out.println("HiEqualsDos");
        }
        scannerView.stopCamera();
        scannerView.startCamera();
        scannerView.resumeCameraPreview(this);
    }
}
