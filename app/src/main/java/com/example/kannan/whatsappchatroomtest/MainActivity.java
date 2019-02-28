package com.example.kannan.whatsappchatroomtest;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;

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
        ColorStateList oldBgTint = phoneEdit.getBackgroundTintList();
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
                    phoneEdit.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    phoneEdit.setError("required");
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

        phoneEdit.addTextChangedListener(phoneEditListener(oldBgTint));
    }

    private TextWatcher phoneEditListener(final ColorStateList oldBgTint) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    phoneEdit.setBackgroundTintList(oldBgTint);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }
}