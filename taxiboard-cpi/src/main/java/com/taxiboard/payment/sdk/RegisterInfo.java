package com.taxiboard.payment.sdk;

public class RegisterInfo {

    private String phoneNumber;
    private String packageName;

    public RegisterInfo(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.packageName = PaymentConfig.PACKAGE_NAME;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPackageName() {
        return packageName;
    }

}
