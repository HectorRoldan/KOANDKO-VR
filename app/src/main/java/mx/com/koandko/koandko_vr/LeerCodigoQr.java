package mx.com.koandko.koandko_vr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

//import static android.widget.Toast.makeText;

/**
 * Created by hgutierrez on 8/11/17.
 */

public class LeerCodigoQr extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private ZXingScannerView scannerView;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        //Llamamos el metodo para acceder a la camara
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
                scannerQR();
            }
        } else {
            //Si la API es menor a 23 - procedemos a la actividad
            scannerQR();
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
                scannerQR();
            } else {
                //Si niega
                mostrarExplicacion();
            }
        }
    }

    /*public void grabar() {
        makeText(getApplicationContext(), "Yeah!!!!!!", Toast.LENGTH_SHORT).show();
    }*/

    //Accedemos a la camara y escaneamos
    public void scannerQR() {
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();

    }


    //Resultado del escaneo
    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado del scanner ");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //result.toString()
        if (result.getText().equals("http://192.168.100.107/hector/three/gyroscope/resources/buyLaptop.MP4")) {
            Intent intent = new Intent(this, MySpeech.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, Main3D.class);
            startActivity(intent);
        }
        scannerView.stopCamera();
        scannerView.startCamera();
        scannerView.resumeCameraPreview(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
