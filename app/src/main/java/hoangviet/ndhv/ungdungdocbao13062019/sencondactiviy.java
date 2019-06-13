package hoangviet.ndhv.ungdungdocbao13062019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class sencondactiviy extends AppCompatActivity {
WebView webView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sencondactiviy);
        Intent intent = getIntent();
        String link = intent.getStringExtra("linkdulieu");
        webView = (WebView)findViewById(R.id.webviewlink);
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}
