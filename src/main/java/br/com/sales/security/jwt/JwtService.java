package br.com.sales.security.jwt;

import br.com.sales.SalesApplication;
import br.com.sales.domain.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.signature}")
    private String signature;

    public String generateToken(User user){
        Long expirationString = Long.valueOf(expiration);
        LocalDateTime dateTimeExpiration = LocalDateTime.now().plusMinutes(expirationString);
        Date date = Date.from(dateTimeExpiration.atZone(ZoneId.systemDefault()).toInstant());

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", "teste@gmail.com");
        claims.put("roles", "ADMIN");

        return Jwts
            .builder()
            .setSubject(user.getLogin())
            .setExpiration(date)
            .addClaims(claims)
            .signWith(SignatureAlgorithm.HS512, signature)
            .compact();
    }

    public Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
            .parser()
            .setSigningKey(signature)
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean isValidToken(String token){
        try {
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime localDateTime = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return LocalDateTime.now().isBefore(localDateTime);
        } catch(Exception ex){
            return false;
        }
    }

    public String getUserLogin(String token) throws ExpiredJwtException {
        return getClaims(token).getSubject();
    }

    /**
     * Método para teste da função 'generateToken()'
     * @param args Argumentos
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SalesApplication.class, args);
        JwtService jwtService = context.getBean(JwtService.class);
        User user = User.builder().login("teste").build();
        String token = jwtService.generateToken(user);
        System.out.println(token);

        System.out.println(jwtService.isValidToken(token));
        System.out.println(jwtService.getUserLogin(token));

    }

}
