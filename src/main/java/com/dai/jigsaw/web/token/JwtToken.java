package com.dai.jigsaw.web.token;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.Claim;
import com.dai.jigsaw.core.exceptions.TokenException;

/**
 * Jwt生成token
 * 
 * @author daidai
 *
 */
public class JwtToken extends Token {
	public static String HMAC256_SALT = "DAIDAI";
	private Algorithm algorithm;
	private JWTVerifier verifier;
	private String salt;

	public JwtToken() {
		this(HMAC256_SALT);
	}

	public JwtToken(String salt) {
		this.salt = salt;
	}

	public Algorithm getAlgorithm() {
		if (algorithm == null) {
			synchronized (JwtToken.class) {
				if (algorithm == null) {
					try {
						algorithm = Algorithm.HMAC256(salt);
					} catch (IllegalArgumentException | UnsupportedEncodingException e) {
						e.printStackTrace();
						throw new TokenException("加密类生成失败！");
					}
				}
			}
		}
		return algorithm;
	}

	public JWTVerifier getVerifier() {
		if (verifier == null) {
			verifier = JWT.require(algorithm).build();
		}
		return verifier;
	}

	@Override
	public String create(Map<String, String> info, long time) {
		if (time <= 0) {
			throw new TokenException("token有效时长必须大于0！");
		}
		if (info == null) {
			throw new TokenException("info不能为空！");
		}
		Builder jwtBuilder = JWT.create().withIssuedAt(new Date())
				.withExpiresAt(new Date((System.currentTimeMillis() + time * 1000)));
		for(String key : info.keySet()){
			jwtBuilder.withClaim(key, info.get(key));
		}
		return jwtBuilder.sign(getAlgorithm());
	}

	@Override
	public boolean validate(String token) {
		try {
			getVerifier().verify(token);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Map<String, String> getInfo(String token) {
		if (validate(token)) {
			Map<String, String> result = new HashMap<String, String>();
			Map<String, Claim> claims = JWT.decode(token).getClaims();
			for (String key : claims.keySet()) {
				if (PublicClaims.ISSUED_AT.equals(key) || PublicClaims.EXPIRES_AT.equals(key)) {
					continue;
				}
				result.put(key, claims.get(key).asString());
			}
			return result;
		}
		return null;
	}

}
