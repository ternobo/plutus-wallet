package com.ternobo.wallet.payment.zarinpal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("zarinpal")
public class ZarinpalPaymentConfig {
    private String mode;
    private String sandboxRequestPaymentUrl;
    private String sandboxVerifyPaymentUrl;
    private String sandboxStartPaymentUrl;

    private String requestPaymentUrl;
    private String verifyPaymentUrl;
    private String startPaymentUrl;

    private String merchantID;

    public String isMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRequestPaymentUrl() {
        return this.mode.equals("sandbox") ? this.sandboxRequestPaymentUrl : this.requestPaymentUrl;
    }

    public void setRequestPaymentUrl(String requestPaymentUrl) {
        this.requestPaymentUrl = requestPaymentUrl;
    }

    public String getVerifyPaymentUrl() {
        return this.mode.equals("sandbox") ? this.sandboxVerifyPaymentUrl : this.verifyPaymentUrl;
    }

    public void setVerifyPaymentUrl(String verifyPaymentUrl) {
        this.verifyPaymentUrl = verifyPaymentUrl;
    }

    public String getStartPaymentUrl() {
        return this.mode.equals("sandbox") ? this.sandboxStartPaymentUrl : this.startPaymentUrl;
    }

    public void setStartPaymentUrl(String startPaymentUrl) {
        this.startPaymentUrl = startPaymentUrl;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setSandboxRequestPaymentUrl(String sandboxRequestPaymentUrl) {
        this.sandboxRequestPaymentUrl = sandboxRequestPaymentUrl;
    }

    public void setSandboxVerifyPaymentUrl(String sandboxVerifyPaymentUrl) {
        this.sandboxVerifyPaymentUrl = sandboxVerifyPaymentUrl;
    }

    public void setSandboxStartPaymentUrl(String sandboxStartPaymentUrl) {
        this.sandboxStartPaymentUrl = sandboxStartPaymentUrl;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }
}
