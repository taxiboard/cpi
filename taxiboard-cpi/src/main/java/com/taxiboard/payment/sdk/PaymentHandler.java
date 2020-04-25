package com.taxiboard.payment.sdk;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class PaymentHandler {

    private Gson gson;
    private OkHttpClient client;
    private String phoneNumber;
    private PaymentListener listener;

    public PaymentHandler(@NonNull PaymentListener listener) {
        this.listener = listener;
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public void registered(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        postRegisterInfo();
    }

    private void postRegisterInfo() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                RegisterInfo registerInfo = new RegisterInfo(phoneNumber);
                String postBody = gson.toJson(registerInfo);

                RequestBody body = RequestBody.create(PaymentConfig.JSON, postBody);

                Request request = new Request.Builder()
                        .url(PaymentConfig.SERVER_API)
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(Response response) throws IOException {

                        String body = response.body().string();

                        if (response.isSuccessful()) {
                            ResponseData data = gson.fromJson(body, ResponseData.class);
                            String url = String.format("%s?phoneNumber=%s", data.getData(), phoneNumber);
                            listener.onSuccess(url);
                        } else {
                            // TODO: error type has not implemented yet.
                            listener.onError(ErrorType.UNKNOWN, body);
                        }
                    }

                    @Override
                    public void onFailure(Request request, IOException e) {
                        listener.onError(ErrorType.UNKNOWN, e.getMessage());
                    }
                });
            }
        });
    }
}
