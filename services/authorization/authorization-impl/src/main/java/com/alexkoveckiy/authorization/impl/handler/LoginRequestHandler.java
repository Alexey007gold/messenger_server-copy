package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.LoginRequest;
import com.alexkoveckiy.authorization.api.message.LoginResponse;
import com.alexkoveckiy.authorization.api.router.AuthorizationRequestHandler;
import com.alexkoveckiy.authorization.impl.model.CommonData;
import com.alexkoveckiy.common.dao.repositories.UserRepository;
import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseStatus;
import com.alexkoveckiy.common.router.api.AbstractRequestHandler;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class LoginRequestHandler extends AbstractRequestHandler<LoginRequest, LoginResponse> implements AuthorizationRequestHandler<LoginResponse> {

    @Autowired
    private CommonData commonData;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public Response<LoginResponse> process(Request<LoginRequest> msg) {
        ActionHeader header = null;
        LoginResponse data = null;
        ResponseStatus status = null;

        try {
            String deviceToken = msg.getData().getDeviceToken();
            String phoneNumber = getPhoneNumberFromDeviceToken(deviceToken);

            if (userRepository.exists(phoneNumber)) {
                header = new ActionHeader(UUID.randomUUID().toString(),
                        msg.getHeader().getUuid(),
                        "authorization",
                        "login",
                        "HTTP/1.1");
                data = new LoginResponse(createTemporaryTokenForPhoneNumber(phoneNumber));
                status = new ResponseStatus(200, "OK");
            }
            else {
                status = new ResponseStatus(404, "Not Found");
            }
        } catch (JoseException ignored) {
            header = null;
            data = null;
            status = new ResponseStatus(400, "Bad Request");
        }
        return new Response<>(header, data, status);
    }

    private String getPhoneNumberFromDeviceToken(String deviceToken) {
        try {
            JsonWebEncryption jwe = new JsonWebEncryption();
            jwe.setKey(commonData.aesKey);
            jwe.setCompactSerialization(deviceToken);
            JwtClaims claims = JwtClaims.parse(jwe.getPayload());
            return (String) claims.getClaimsMap().get("phone_number");
        } catch (JoseException | InvalidJwtException e) {
            return "";
        }
    }

    private String createTemporaryTokenForPhoneNumber(String phoneNumber) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setClaim("phone_number", phoneNumber);
        claims.setExpirationTimeMinutesInTheFuture(999999999);
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload(claims.toJson());
        jwe.setKey(commonData.aesKey);
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        return jwe.getCompactSerialization();
    }
}
