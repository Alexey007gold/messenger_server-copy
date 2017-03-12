package com.alexkoveckiy.common.token.api;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;

/**
 * Created by alex on 11.03.17.
 */
public interface TokenHandler {
    String createDeviceToken(String phoneNumber, String deviceId, String locale) throws JoseException, MalformedClaimException;
    String createTemporaryToken(String phoneNumber) throws JoseException;
    String getPhoneNumberFromDeviceToken(String token) throws JoseException, InvalidJwtException, MalformedClaimException;
    String getPhoneNumberFromTemporaryToken(String token) throws JoseException, InvalidJwtException, MalformedClaimException;
}
