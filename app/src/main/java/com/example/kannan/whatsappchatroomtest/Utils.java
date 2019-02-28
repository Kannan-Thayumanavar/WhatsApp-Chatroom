package com.example.kannan.whatsappchatroomtest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class Utils {
    private static final String urlBegin = "https://wa.me/";

    static String encodeURL(String phoneNo, String message) throws UnsupportedEncodingException {
        String encodedUrl;
        if (message != null && !message.isEmpty()) {
            encodedUrl = urlBegin + phoneNo + "?text=" + URLEncoder.encode(message, "UTF-8");
        } else {
            encodedUrl = urlBegin + phoneNo;
        }
        return encodedUrl;
    }
}
