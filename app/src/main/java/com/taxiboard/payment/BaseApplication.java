package com.taxiboard.payment;

import android.app.Application;

import com.taxiboard.payment.sdk.PaymentConfig;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initTaxiBoardSDK();
    }

    private void initTaxiBoardSDK() {
        PaymentConfig.init(this);
    }
}
