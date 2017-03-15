package com.alexkoveckiy.common.token.api;

import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.common.token.exception.InvalidTokenException;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;

/**
 * Created by alex on 11.03.17.
 */
public interface TokenHandler {
    String createDeviceToken(String userId, String deviceId, String locale) throws InvalidTokenException;
    String createTemporaryToken(String userId, String deviceId) throws InvalidTokenException;
    String getUserIdFromDeviceToken(String token) throws InvalidTokenException;
    String getPhoneNumberFromTemporaryToken(String token) throws InvalidTokenException;
    RoutingData getRoutingData(String token) throws InvalidTokenException;
}
