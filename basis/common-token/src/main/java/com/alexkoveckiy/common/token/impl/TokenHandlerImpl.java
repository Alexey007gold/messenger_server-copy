package com.alexkoveckiy.common.token.impl;

import com.alexkoveckiy.common.exceptions.InvalidTokenException;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.common.token.api.TokenHandler;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.springframework.stereotype.Component;

import static com.alexkoveckiy.common.token.impl.TokenHandlerImpl.Claim.DEVICE_ID;
import static com.alexkoveckiy.common.token.impl.TokenHandlerImpl.Claim.PROFILE_ID;


/**
 * Created by alex on 11.03.17.
 */

@Component
public class TokenHandlerImpl implements TokenHandler {

    private final AesKey aesKey = new AesKey("superKeyyeKrepus".getBytes());

    private final String DEVICE_TOKEN = "deviceToken";
    private final String TEMPORARY_TOKEN = "temporaryToken";

    @Override
    public String createDeviceToken(String profileId, String deviceId) throws InvalidTokenException {
        try {
            JwtClaims claims = new JwtClaims();
            claims.setIssuedAtToNow();
            claims.setClaim(PROFILE_ID, profileId);
            claims.setClaim(DEVICE_ID, deviceId);
            claims.setClaim("type", DEVICE_TOKEN);

            return encrypt(claims);
        } catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }

    @Override
    public String createTemporaryToken(String profileId, String deviceId) throws InvalidTokenException {
        JwtClaims claims = new JwtClaims();
        claims.setIssuedAtToNow();
        claims.setClaim(PROFILE_ID, profileId);
        claims.setClaim(DEVICE_ID, deviceId);
        claims.setClaim("type", TEMPORARY_TOKEN);
        claims.setExpirationTimeMinutesInTheFuture(5);

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
    public String getProfileIdFromDeviceToken(String token) throws InvalidTokenException {
        try {
            JwtClaims claims = getClaimsFromToken(token);
            if (!claims.getStringClaimValue("type").equals(DEVICE_TOKEN))
                throw new InvalidTokenException("This is not Device Token!");
            return claims.getStringClaimValue(PROFILE_ID);
        } catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }

    @Override
    public JwtClaims getClaimsFromDeviceToken(String token) throws InvalidTokenException {
        try {
            JwtClaims claims = getClaimsFromToken(token);
            if (!claims.getStringClaimValue("type").equals(DEVICE_TOKEN))
                throw new InvalidTokenException("This is not Device Token!");
            return claims;
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
            return new RoutingData(claims.getStringClaimValue(PROFILE_ID),
                    claims.getStringClaimValue(DEVICE_ID));
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

    public static class Claim {
        public static final String DEVICE_ID = "device_id";
        public static final String PROFILE_ID = "profile_id";
    }
}
