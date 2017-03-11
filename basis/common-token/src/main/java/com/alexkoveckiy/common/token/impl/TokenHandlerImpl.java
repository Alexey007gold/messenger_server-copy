package com.alexkoveckiy.common.token.impl;

import com.alexkoveckiy.common.token.api.TokenHandler;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 11.03.17.
 */

@Component
public class TokenHandlerImpl implements TokenHandler {

    private final AesKey aesKey = new AesKey("superKeyyeKrepus".getBytes());

    @Override
    public String createDeviceToken(String phoneNumber, String deviceId, String locale) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setClaim("phone_number", phoneNumber);
        claims.setClaim("device_id", deviceId);
        claims.setClaim("locale_code", locale);

        return encrypt(claims);
    }

    public String createTemporaryToken(String phoneNumber) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setClaim("phone_number", phoneNumber);
        claims.setExpirationTimeMinutesInTheFuture(999999999);

        return encrypt(claims);
    }

    private String encrypt(JwtClaims claims) throws JoseException {
        JsonWebEncryption encryption = getEncryption();
        encryption.setPayload(claims.toJson());
        return encryption.getCompactSerialization();
    }

    private JsonWebEncryption getEncryption() {
        JsonWebEncryption encryption = new JsonWebEncryption();
        encryption.setKey(new AesKey("superKeyyeKrepus".getBytes()));
        encryption.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        encryption.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        return encryption;
    }

    public String getPhoneNumberFromDeviceToken(String token) throws JoseException, InvalidJwtException, MalformedClaimException {
        JwtClaims claims = getClaimsFromToken(token);
        return claims.getStringClaimValue("phone_number");
    }

    public String getPhoneNumberFromTemporaryToken(String token) throws JoseException, InvalidJwtException, MalformedClaimException {
        JwtClaims claims = getClaimsFromToken(token);
        if (claims.getExpirationTime().getValueInMillis() < System.currentTimeMillis())
            throw new JoseException("Token has expired");
        return claims.getStringClaimValue("phone_number");
    }

    private JwtClaims getClaimsFromToken(String token) throws JoseException, InvalidJwtException {
        JsonWebEncryption encryption = getEncryption();
        encryption.setCompactSerialization(token);
        return JwtClaims.parse(encryption.getPayload());
    }
}
