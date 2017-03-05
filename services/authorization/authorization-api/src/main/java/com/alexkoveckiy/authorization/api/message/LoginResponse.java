package com.alexkoveckiy.authorization.api.message;

import com.alexkoveckiy.common.protocol.ResponseData;

/**
 * @author odin
 * @since 20.02.17.
 */
public class LoginResponse implements ResponseData {

	private static final long serialVersionUID = 5767279680451003633L;

	private String token;

	public LoginResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
