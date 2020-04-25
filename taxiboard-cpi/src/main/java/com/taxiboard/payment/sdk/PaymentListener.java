package com.taxiboard.payment.sdk;

public interface PaymentListener {
    void onSuccess(String url);

    void onError(ErrorType errorType, String message);
}
