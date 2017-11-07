package mx.com.koandko.koandko_vr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by hgutierrez on 31/10/17.
 */

public class Main3D extends AppCompatActivity{

    WebView webView3D;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3d);

        webView3D = (WebView) findViewById(R.id.webView3D);
        WebSettings webSettings = webView3D.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView3D.loadUrl("http://192.168.100.107/hector/three.js-master/examples/webgl_loader_gltf2.html");

    }


}
