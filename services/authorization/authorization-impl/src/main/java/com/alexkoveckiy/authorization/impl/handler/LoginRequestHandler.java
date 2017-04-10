package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.LoginRequest;
import com.alexkoveckiy.authorization.api.message.LoginResponse;
import com.alexkoveckiy.authorization.api.handler.AuthorizationRequestHandler;
import com.alexkoveckiy.common.dao.service.DeviceService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.common.router.api.handler.AbstractRequestHandler;
import com.alexkoveckiy.common.token.api.TokenHandler;
import org.jose4j.jwt.JwtClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.alexkoveckiy.common.token.impl.TokenHandlerImpl.Claim.DEVICE_ID;
import static com.alexkoveckiy.common.token.impl.TokenHandlerImpl.Claim.PROFILE_ID;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class LoginRequestHandler extends AuthorizationRequestHandler<LoginRequest, LoginResponse> {

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private DeviceService deviceService;

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public Response<LoginResponse> process(Request<LoginRequest> msg) throws Exception {
        JwtClaims claims = tokenHandler.getClaimsFromDeviceToken(msg.getData().getDeviceToken());
        String profileId = claims.getStringClaimValue(PROFILE_ID);
        String deviceId = claims.getStringClaimValue(DEVICE_ID);
        if (claims.getIssuedAt().getValueInMillis() == deviceService.findByProfileIdAndDeviceId(profileId, deviceId).getConfirmationTime())
            return ResponseFactory.createResponse(msg, new LoginResponse(tokenHandler.createTemporaryToken(profileId, deviceId)));
        return ResponseFactory.createResponse(msg, ResponseFactory.Status.FORBIDDEN);
    }
}
