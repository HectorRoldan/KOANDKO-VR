package mx.com.koandko.koandko_vr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class Splash extends Activity {

    private WebView webView3D;
    private ProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        webView3D = (WebView) findViewById(R.id.webView3D);
        WebSettings webSettings = webView3D.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView3D.loadUrl("http://192.168.100.107/hector/three.js-master/examples/webgl_materials_cubemap_balls_reflection.html");

        myProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);
        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finalizar();


            }
        }).start();
    }

    private void doWork() {
        for (int progress = 0; progress < 100; progress += 10) {
            try {
                Thread.sleep(500);
                myProgressBar.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
                //Timber.e(e.getMessage());
            }
        }
    }

    private void startApp() {
        Intent intent = new Intent(Splash.this, ClassExample.class);
        startActivity(intent);

    }

    private void finalizar(){
        Splash.this.finish();
    }


        /*Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    //Intent intent = new Intent(ReadQr.class);
                    Intent intent = new Intent(Splash.this,ReadQr.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();*/

        /*ActionBar actionBar = getActionBar(); //OR getSupportActionBar();
        actionBar.hide();*/
}

   /* @Override
    protected void onPause() {

        super.onPause();
        finish();
    }*/

