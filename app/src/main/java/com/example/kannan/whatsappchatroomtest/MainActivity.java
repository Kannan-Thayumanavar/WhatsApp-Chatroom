package com.example.kannan.whatsappchatroomtest;

import android.os.Bundle;
import android.app.Activity;

import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends Activity {
    Button button;
    EditText phoneEdit, msgEdit;
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        phoneEdit = findViewById(R.id.editText);
        msgEdit = findViewById(R.id.editText2);
        web = findViewById(R.id.webView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = null;
                String phoneNo = phoneEdit.getText().toString();
                String message = msgEdit.getText().toString();
                phoneNo = phoneNo.replace("+", "");
                if (phoneNo.matches("")) {
                    Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                    phoneEdit.startAnimation(shake);
                } else {
                    try {
                        url = Utils.encodeURL(phoneNo, message);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    web.getSettings().setLoadsImagesAutomatically(true);
                    web.getSettings().setJavaScriptEnabled(true);
                    web.loadUrl(url);
                }
            }
        });
    }
}