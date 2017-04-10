package com.alexkoveckiy.common.token;

import com.alexkoveckiy.common.exceptions.InvalidTokenException;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.common.token.api.TokenHandler;
import com.alexkoveckiy.common.token.impl.TokenHandlerImpl;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.alexkoveckiy.common.token.impl.TokenHandlerImpl.Claim.DEVICE_ID;
import static com.alexkoveckiy.common.token.impl.TokenHandlerImpl.Claim.PROFILE_ID;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
/**
 * Created by alex on 21.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TokenHandlerImplTest {

    private TokenHandler tokenHandler = new TokenHandlerImpl();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void deviceTokenTest() throws InvalidTokenException, MalformedClaimException {
        String token = tokenHandler.createDeviceToken("user", "device");
        assertThat(tokenHandler.getProfileIdFromDeviceToken(token), is("user"));
        JwtClaims claims = tokenHandler.getClaimsFromDeviceToken(token);
        assertThat(claims.getStringClaimValue(PROFILE_ID), is("user"));
        assertThat(claims.getStringClaimValue(DEVICE_ID), is("device"));
        assertThat(claims.getExpirationTime(), is(nullValue()));
        assertThat(claims.getIssuedAt(), is(notNullValue()));

        exception.expect(InvalidTokenException.class);
        tokenHandler.getClaimsFromDeviceToken(token.substring(1));
        tokenHandler.getRoutingDataFromTemporaryToken(token);
    }

    @Test
    public void temporaryTokenTest() throws InvalidTokenException, MalformedClaimException, JoseException, InvalidJwtException {
        String token = tokenHandler.createTemporaryToken("user", "device");
        RoutingData routingData = tokenHandler.getRoutingDataFromTemporaryToken(token);
        assertThat(routingData.getProfileId(), is("user"));
        assertThat(routingData.getDeviceId(), is("device"));

        JsonWebEncryption encryption = new JsonWebEncryption();
        encryption.setKey(new AesKey("superKeyyeKrepus".getBytes()));
        encryption.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        encryption.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        encryption.setCompactSerialization(token);
        JwtClaims claims = JwtClaims.parse(encryption.getPayload());
        claims.setExpirationTimeMinutesInTheFuture(0);
        encryption.setPayload(claims.toJson());
        token = encryption.getCompactSerialization();

        exception.expect(InvalidTokenException.class);
        tokenHandler.getRoutingDataFromTemporaryToken(token);
        tokenHandler.getClaimsFromDeviceToken(token);
        tokenHandler.getProfileIdFromDeviceToken(token);
    }
}
