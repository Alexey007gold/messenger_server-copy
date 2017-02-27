package com.softgroup.authorization.api.message;

import com.softgroup.common.protocol.ResponseData;

/**
 * @author odin
 * @since 20.02.17.
 */

public class RegisterResponse implements ResponseData {
	private static final long serialVersionUID = -5146888202653750948L;

	private String registrationRequestUuid;

	private Integer registrationTimeoutSec;

	private Integer authCode;

	public RegisterResponse() {
	}

	public RegisterResponse(String registrationRequestUuid, Integer registrationTimeoutSec, Integer authCode) {
		this.registrationRequestUuid = registrationRequestUuid;
		this.registrationTimeoutSec = registrationTimeoutSec;
		this.authCode = authCode;
	}

	public String getRegistrationRequestUuid() {
		return registrationRequestUuid;
	}

	public void setRegistrationRequestUuid(String registrationRequestUuid) {
		this.registrationRequestUuid = registrationRequestUuid;
	}

	public int getRegistrationTimeoutSec() {
		return registrationTimeoutSec;
	}

	public void setRegistrationTimeoutSec(int registrationTimeoutSec) {
		this.registrationTimeoutSec = registrationTimeoutSec;
	}

	public int getAuthCode() {
		return authCode;
	}

	public void setAuthCode(int authCode) {
		this.authCode = authCode;
	}
}
