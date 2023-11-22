// Openly inspired from:
// https://medium.com/code-with-farhan/spring-security-jwt-authentication-authorization-a2c6860be3cf

package fr.diegoimbert.cvman.lib.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import fr.diegoimbert.cvman.lib.model.User;

@Component
public class JwtUtil {
  private final String secretKey;

  private long accessTokenValidity = 60 * 60 * 1000;

  private final JwtParser jwtParser;

  private final String TOKEN_HEADER = "Authorization";
  private final String TOKEN_PREFIX = "Bearer ";

  public JwtUtil(@Value("${application.jwt.secretKey}") String secretKey) {
    this.secretKey = secretKey;
    this.jwtParser = Jwts.parser()
        .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .build();
  }

  public String createToken(User user) {
    Claims claims = Jwts.claims()
        .subject(user.getEmail())
        .add("id", user.getId())
        .build();
    Date tokenCreateTime = new Date();
    Date tokenValidity = new Date(tokenCreateTime.getTime() +
        TimeUnit.MINUTES.toMillis(accessTokenValidity));
    return Jwts.builder()
        .claims(claims)
        .expiration(tokenValidity)
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
        .compact();
  }

  private Claims parseJwtClaims(String token) {
    return jwtParser.parseSignedClaims(token).getPayload();
  }

  public Claims resolveClaims(HttpServletRequest req) {
    try {
      String token = resolveToken(req);
      return token != null ? parseJwtClaims(token) : null;
    } catch (ExpiredJwtException e) {
      req.setAttribute("expired", e.getMessage());
      throw e;
    } catch (Exception e) {
      req.setAttribute("invalid", e.getMessage());
      throw e;
    }
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(TOKEN_HEADER);
    if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
      return bearerToken.substring(TOKEN_PREFIX.length());
    }
    return null;
  }

  public boolean validateClaims(Claims claims) throws AuthenticationException {
    try {
      return claims.getExpiration().after(new Date());
    } catch (Exception e) {
      throw e;
    }
  }

  public String getEmail(Claims claims) {
    return claims.getSubject();
  }
}
