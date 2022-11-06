package com.rayumov.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    /*
    Для генерации токена нужен какой-нибудь ключ, который знали бы только вы.
    И второе - нужно определить сколько времени будет жить данный токен.
    Для этого добавили два параметра: secret и jwtLifetime.
    @Value("${jwt.secret}") - это означает, что вы достанете это из какого то файла.
     */

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    /*
    На вход - userDetails
    В начале создаем мапу, которая под строкой может хранить какой то объект (туда можно накидать все что угодно, email, возраст, список ролей и т.д.).

    Далее из юзера получаем список строк (список ролей по сути). Далее кладем их в Claims.

    Далее нам бы хотелось знать когда был создан токен и через сколько время жизни токена истечет.

    Создаем билдер, в Claims кладем Claims, которые достали из юзера.
    В Subject кладем имя пользователя.
    Указываем дату генерации токена, и время его истечения.
    И делаем подпись с помощью алгоритма и ключа.
    В конце собираем токет.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    //Достаем Subject из токена (там хранится username).
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Достаем список ролей из токена
    public List<String> getRoles(String token) {
        // берет Claims, вызывает у них get и просит поле которое называется "roles"
        return getClaimFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("roles", List.class));
    }

    // получение Email из токена
    public String getEmail(String token) {
        // берет Claims, вызывает у них get и просит поле которое называется "roles"
        return getClaimFromToken(token, claims -> claims.get("email", String.class));
    }


    /*
    Сюда отдаем токен и функцию, которая из все хтих Claims (ключ-значение) вытащит какой то кусочек данных, который нас интересует.
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        /*
        Сначала получаем все Claims из токена.
        Потом вытаскиваем кусок данных с помощью функции, которая сюда придет.
         */
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /*
    Когда к нам приходит токен, то мы можем достать из него все полезные данные.
    Создаем парсер, пришиваем к нему ключ, и отдаем токен.
    Claims - по сути список ключей - значений.
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}
