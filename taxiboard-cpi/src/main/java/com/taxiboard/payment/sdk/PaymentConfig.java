package com.taxiboard.payment.sdk;

import android.app.Application;

import androidx.annotation.NonNull;

import com.squareup.okhttp.MediaType;

public class PaymentConfig {

    final static String SERVER_API = "http://www.google.com";

    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    static String PACKAGE_NAME = null;

    public static void init(@NonNull Application application) {
        PACKAGE_NAME = application.getPackageName();
    }
}
