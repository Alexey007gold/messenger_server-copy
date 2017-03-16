package com.alexkoveckiy.common.token.impl;

import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.common.token.api.TokenHandler;
import com.alexkoveckiy.common.token.exception.InvalidTokenException;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 11.03.17.
 */

@Component
public class TokenHandlerImpl implements TokenHandler {

    private final AesKey aesKey = new AesKey("superKeyyeKrepus".getBytes());

    private final String DEVICE_TOKEN = "deviceToken";
    private final String TEMPORARY_TOKEN = "temporaryToken";

    @Override
    public String createDeviceToken(String userId, String deviceId, String locale) throws InvalidTokenException {
        try {
            JwtClaims claims = new JwtClaims();
            claims.setIssuedAtToNow();
            claims.setClaim("user_id", userId);
            claims.setClaim("device_id", deviceId);
            claims.setClaim("type", DEVICE_TOKEN);

            return encrypt(claims);
        } catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }

    @Override
    public String createTemporaryToken(String userId, String deviceId) throws InvalidTokenException {
        JwtClaims claims = new JwtClaims();
        claims.setIssuedAtToNow();
        claims.setClaim("user_id", userId);
        claims.setClaim("device_id", deviceId);
        claims.setClaim("type", TEMPORARY_TOKEN);
        claims.setExpirationTimeMinutesInTheFuture(999999999);

        return encrypt(claims);
    }

    private String encrypt(JwtClaims claims) throws InvalidTokenException {
        try {
            JsonWebEncryption encryption = getEncryption();
            encryption.setPayload(claims.toJson());
            return encryption.getCompactSerialization();
        } catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }

    }

    private JsonWebEncryption getEncryption() {
        JsonWebEncryption encryption = new JsonWebEncryption();
        encryption.setKey(aesKey);
        encryption.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        encryption.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        return encryption;
    }

    @Override
    public String getUserIdFromDeviceToken(String token) throws InvalidTokenException {
        try {
            JwtClaims claims = getClaimsFromToken(token);
            if (!claims.getStringClaimValue("type").equals(DEVICE_TOKEN))
                throw new InvalidTokenException("This is not Device Token!");
            return claims.getStringClaimValue("user_id");
        } catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }

    @Override
    public RoutingData getRoutingDataFromTemporaryToken(String token) throws InvalidTokenException {
        try {
            JwtClaims claims = getClaimsFromToken(token);
            if (!claims.getStringClaimValue("type").equals(TEMPORARY_TOKEN))
                throw new InvalidTokenException("This is not Temporary Token!");
            if (claims.getExpirationTime().getValueInMillis() < System.currentTimeMillis())
                throw new InvalidTokenException("Token has expired");
            return new RoutingData(claims.getStringClaimValue("user_id"),
                    claims.getStringClaimValue("device_id"));
        } catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }

    private JwtClaims getClaimsFromToken(String token) throws InvalidTokenException {
        try {
            JsonWebEncryption encryption = getEncryption();
            encryption.setCompactSerialization(token);
            return JwtClaims.parse(encryption.getPayload());
        } catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }
}
