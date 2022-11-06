package com.rayumov.configs;

import com.rayumov.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
// Наследуемся от OncePerRequestFilter, то есть на запрос реагируем один раз.
public class JwtRequestFilter extends OncePerRequestFilter {

    // 2 вариант
//    private final UserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    /*
    Фильтры работают с чистыми запросами и с чистыми ответами.
    Предполагаем, что когда запрос летит, в этом запросе будут данные с токеном. В соответствии с протоколом http мы знаем, что все токены подпишваются в определенный заголовок. Этот заголовок называется 'Authorization'.

    Мы ищем этот заголовок. Если не находим, то пропускаем запрос дальше. Рано или поздно он сподкнется об то, что требуется какая то идентификация клиента.

    Если хедер есть. То по документации мы знаем что этот токен должен начинаться со слова 'Bearer' со значком пробела и потом сам jwt token.
    Соответственно мы ждем, что значение этого хедера начинается с этого слова и пробела.
    Теперь если хедер есть и он начинается с нужного слова - мы отпиливаем первые 7 символов ('Bearer '), и у нас остается только jwt.
    Дальше из токета пытаемся достать имя пользователя. И вот когда вы взаимодействуете с токеном через итилиту (пытаетесь из него что то достать, хоть поля, хоть одно поле), в этом случае мы сразу проводите его валидацию как по времени жизни, так и по подписи.

    Если username вытащили - ок, значит токен валидный, все данные о клиенте есть.
    Дальше проверяем, что в контексте никого нет (что пользователь никаким другим способом не пытался зайти и т.д).
    Если это так, то мы вручную формируем служебный объект UsernamePasswordAuthenticationToken.
    Зашиваем туда имя пользователя, чистим credentials, и потом из токена достаем список ролей и преобразуем их в SimpleGrantedAuthority.

    Получается, что мы взяли jason web token, вытащили из него имя пользователя и роли и сформировали стандарный спринговый объект и положили его в контекст.



     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                // Если тут кто-то что-то подделал, то сразу это узнается.
                username = jwtTokenUtil.getUsernameFromToken(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("The token is expired");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            /*
             2 вариант. Инжектив в класс UserDetailsService.
             И когда токен приходит, мы достаем только имя пользователя.
             После чего по имени пользователя находим его в базе.
             Формируем токен.
             Подшиваем детали.
             И закидываем в контекст.
             В этом случае при каждом запросе будет обращение в БД, но у вас будет самая актуальная информацияч о пользователе.

             Может быть такое, что пользователь токен получил и живет этот токен 1 день.
             Потом этот пользователь сделал что то нехорошее и вы говорите я отзываю у тебя права что либо делать. Пока у него токен есть, вы будете его пускать. Пока у его токена не закончится срок действия - он будет активен.
             */
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(token);


            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtTokenUtil.getRoles(jwt).stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        // Запрос летит по цепочке фильтров. Мы находимся где то в начале конвейнера.
        // И мы эту цепочку прерывать не должны - поэтому отправляем дальше по фильтрам.
        filterChain.doFilter(request, response);
    }


}
