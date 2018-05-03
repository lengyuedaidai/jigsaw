package com.dai.daobuild.test.jwt;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

public class JwtTest {
	public String HMAC256_SALT = "DAIDAI";

	@Test
	public void test_JwtCreate() {
		try {
			Algorithm algorithm = Algorithm.HMAC256(HMAC256_SALT);
			String token = JWT.create().withClaim("token", "aaaaaaaa").withIssuer("daidai").sign(algorithm);
			System.out.println(token);
		} catch (UnsupportedEncodingException exception) {
			// UTF-8 encoding not supported
		} catch (JWTCreationException exception) {
			// Invalid Signing configuration / Couldn't convert Claims.
		}
	}
}
