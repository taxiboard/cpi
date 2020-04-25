package com.taxiboard.payment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.taxiboard.payment.databinding.MainActivityBinding;
import com.taxiboard.payment.sdk.ErrorType;
import com.taxiboard.payment.sdk.PaymentHandler;
import com.taxiboard.payment.sdk.PaymentListener;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    private PaymentHandler paymentHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();

        paymentHandler = new PaymentHandler(new PaymentListener() {
            @Override
            public void onSuccess(String url) {
                Snackbar.make(binding.getRoot(), "Payment info registered",
                        Snackbar.LENGTH_SHORT).show();

                openURL(url);
            }

            @Override
            public void onError(ErrorType errorType, String message) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.buttonRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard();

                if (binding.editTextPhoneNumber.getText() == null) {
                    Snackbar.make(binding.getRoot(),
                            R.string.phone_number_is_empty, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                String phoneNumber = binding.editTextPhoneNumber.getText().toString();

                boolean isPhoneNumber = PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber);

                if (!isPhoneNumber) {
                    Snackbar.make(binding.getRoot(),
                            R.string.phone_number_is_invalid, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                paymentHandler.registered(phoneNumber);
            }
        });
    }

    private void openURL(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
