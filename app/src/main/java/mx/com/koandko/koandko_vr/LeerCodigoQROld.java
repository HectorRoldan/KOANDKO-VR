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
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by hgutierrez on 7/11/17.
 */

public class LeerCodigoQROld extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    //Los valores no deben repetirse
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private ZXingScannerView scannerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        //Llamamos al metodo para inciar la camara
        accederToCamara();
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
            //System.out.println("Diste click en Acerca de1");
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);
        }/*else{
                //System.out.println("Diste click en Acerca de2");

            }*/

        return super.onOptionsItemSelected(item);
    }

        /*@Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.readCodeQr:
                    accederToCamara();
                    break;
            }
        }*/

    private void accederToCamara() {
        //si la API 23 a mas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            //Habilitar permisos para la version de API 23 a mas

            int verificarPermisoReadContacts = ContextCompat
                    .checkSelfPermission(this, Manifest.permission.CAMERA);
            //Verificamos si el permiso no existe

            if (verificarPermisoReadContacts != PackageManager.PERMISSION_GRANTED) {
                //verifico si el usuario a rechazado el permiso anteriormente

                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    //Si a rechazado el permiso anteriormente muestro un mensaje
                    mostrarExplicacion();
                } else {
                    //De lo contrario carga la ventana para autorizar el permiso
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }

            } else {
                //Si el permiso ya fue concedido abrimos en intent de contactos
                //abrirIntentContactos();
                //System.out.println("El permiso ya fue concedido");
                scannerQR();
                //abrirIntentContactos();
            }

        } else {//Si la API es menor a 23 - abrimos en intent de contactos
            //System.out.println("La API es menor a 23");
            //leerCodigoQr();
            scannerQR();
            //abrirIntentContactos();
        }
    }

    private void mostrarExplicacion() {
        new AlertDialog.Builder(this)
                .setTitle("Autorización")
                .setMessage("Necesito permiso para acceder a la cámara de tu dispositivo.")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                        }

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Mensaje acción cancelada
                        mensajeAccionCancelada();
                    }
                })
                .show();
    }

    public void mensajeAccionCancelada() {
        Toast.makeText(getApplicationContext(),
                "Haz rechazado la petición, por favor considere en aceptarla.",
                Toast.LENGTH_SHORT).show();
    }

    /*private void abrirIntentContactos(){
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(i, PICK_CONTACT_REQUEST);
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                //Si el permiso a sido concedido abrimos la agenda de contactos
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    accederToCamara();
                    //accederAgendaContactos();
                    //scannerQR();
                } else {
                    mensajeAccionCancelada();
                }
                break;
        }
    }

    public void scannerQR() {
        // System.out.println("H8i");

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();

    }

    /*@Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }*/
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
