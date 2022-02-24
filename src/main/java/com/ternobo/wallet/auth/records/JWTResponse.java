package com.ternobo.wallet.auth.records;

import java.util.Date;


public record JWTResponse(String token,Date expireDate, String userId) {
    // Nothing
}
