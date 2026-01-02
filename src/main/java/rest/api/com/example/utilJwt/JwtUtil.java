package rest.api.com.example.utilJwt;


import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import com.auth0.jwt.algorithms.Algorithm;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final String secretKey = "secretkey";

    public String  genrateToken(String Username){
        return Jwts.builder().setSubject(Username)
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(SignatureAlgorithm.HS256,secretKey.getBytes())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public Boolean validateToken(String token,String username){
        final String userName=extractUsername(token);
        return (userName.equals(username)&&!isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    private Key getKey(){
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    private SignatureAlgorithm getSignatureAlgorithm(){return SignatureAlgorithm.HS256;}
    private Algorithm getAlgorithm(){return Algorithm.HMAC256(secretKey);}
    private Algorithm getAlgorithmHmac(){return Algorithm.HMAC256(getSecretKey());}
    private Algorithm getAlgorithmRsa(){return Algorithm.RSA256(getPublicKey(),getPrivateKey());}
    private PrivateKey getPrivateKey(){return null;}
    private PublicKey getPublicKey(){return null;}
    private KeyPair getKeyPair(){return null;}

}
