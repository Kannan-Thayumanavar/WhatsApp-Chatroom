package com.example.kannan.whatsappchatroomtest;

import android.os.Bundle;
import android.app.Activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends Activity  {
    Button b1;
    EditText ed1,ed2;
    String urlBegin = "https://wa.me/";
    String url = null;
    private WebView wv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.button);
        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);

        wv1=(WebView)findViewById(R.id.webView);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    url = encodeURL(ed2.getText().toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.loadUrl(url);
            }

            public String encodeURL(String message) throws UnsupportedEncodingException {
                String encodedUrl = null;
                if(message != null){
                    encodedUrl = urlBegin + ed1.getText().toString() + "?text=" + URLEncoder.encode(message, "UTF-8");
                }
                else{
                    encodedUrl = urlBegin + ed1.getText().toString();
                }
                return encodedUrl;
            }
        });
    }
}