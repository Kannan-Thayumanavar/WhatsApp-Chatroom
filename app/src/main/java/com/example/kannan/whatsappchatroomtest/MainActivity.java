package com.example.kannan.whatsappchatroomtest;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    Button button;
    EditText phoneEdit, msgEdit;
    private WebView web;
    public static final String MIXPANEL_TOKEN = "90ebef0e23469e21410873e087a44482";
    MixpanelAPI mixpanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        phoneEdit = findViewById(R.id.editText);
        msgEdit = findViewById(R.id.editText2);
        web = findViewById(R.id.webView);
        ColorStateList oldBgTint = phoneEdit.getBackgroundTintList();
        this.mixpanel = MixpanelAPI.getInstance(this, MIXPANEL_TOKEN);
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
                    url = Utils.getUrl(phoneNo, message);
                    trackMixpanel(phoneNo, message, url);
                    web.getSettings().setLoadsImagesAutomatically(true);
                    web.getSettings().setJavaScriptEnabled(true);
                    web.loadUrl(url);
                }
            }
        });

        phoneEdit.addTextChangedListener(phoneEditListener(oldBgTint));
    }

    @Override
    protected void onDestroy() {
        this.mixpanel.flush();
        super.onDestroy();
    }

    private void trackMixpanel(String phoneNo, String message, String url) {
        JSONObject props = new JSONObject();
        try {
            props.put("phone", phoneNo);
            props.put("message", message);
            props.put("url", url);
            mixpanel.track("data", props);
            Log.i("mixpanel", props.toString(4));
        } catch (JSONException e) {
            Crashlytics.setString("phone", phoneNo);
            Crashlytics.setString("message", message);
            Crashlytics.setString("source", "mixpanel_tracking");
            Crashlytics.logException(e);
        }
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