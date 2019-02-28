package com.example.kannan.whatsappchatroomtest;

import com.crashlytics.android.Crashlytics;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class Utils {
    private static final String urlBegin = "https://wa.me/";

    static String getUrl(String phoneNo, String message) {
        String url;
        if (message != null && !message.isEmpty()) {
            try {
                url = urlBegin + phoneNo + "?text=" + URLEncoder.encode(message, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                //at least opening the whatsApp without message
                url = urlBegin + phoneNo;
                Crashlytics.setString("phone", phoneNo);
                Crashlytics.setString("message", message);
                Crashlytics.setString("url", url);
                Crashlytics.logException(e);
            }
        } else {
            url = urlBegin + phoneNo;
        }
        return url;
    }
}
