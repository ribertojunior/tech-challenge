package com.example.techchallenge.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

  private static final String SECRET = "secret";
  private static final String PREFIX = "Bearer ";

  public static boolean validaToken(String cnpj, String token) {
    return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token.replace(PREFIX, "")).getBody().getSubject().equals(cnpj);
  }

  public static String getJWTToken(String cnpj) {
    String secret = "secret";
    List<GrantedAuthority> grantedAuthorities =
        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

    String token =
        Jwts.builder()
            .setId("pixeonJWT")
            .setSubject(cnpj)
            .claim(
                "authorities",
                grantedAuthorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 600000))
            .signWith(SignatureAlgorithm.HS512, secret.getBytes())
            .compact();

    return "Bearer " + token;
  }
}
