package br.com.techlead.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SecurityUtil {
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static String getTokenSecret(){

        AppProperties appProperties = new AppProperties() ;

        return appProperties.getTokenSecret();
    }

    public static String extractUserInfo(String token) {
        token = token.replace(TOKEN_PREFIX, "");

        return Jwts.parser()
                .setSigningKey(getTokenSecret())
                .parseClaimsJws( token )
                .getBody()
                .getSubject();
    }

    public static Claims extractAuthorities(String token) {
        token = token.replace(TOKEN_PREFIX, "");

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(getTokenSecret())
                .parseClaimsJws(token);

        return claims.getBody();
    }

    public static String getUsernameFromToken(String token) {
        token = token.replace(TOKEN_PREFIX, "");

        Claims claims = Jwts.parser()
                .setSigningKey(getTokenSecret())
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public static boolean hasTokenExpired(String token) {
        token = token.replace(TOKEN_PREFIX, "");

        Claims claims = Jwts.parser()
                .setSigningKey(getTokenSecret())
                .parseClaimsJws(token).getBody();

        Date tokenExpirationDate = claims.getExpiration();
        Date today = new Date();

        return tokenExpirationDate.before(today);
    }
}
