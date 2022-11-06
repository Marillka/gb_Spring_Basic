package com.rayumov.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // если у нас нет генерации фронта на беке, то csrf нам не нужен. Отключаем.
                .csrf().disable()
                // этим блоком настраиваем правила безопасности
                // Доступ к консоли даем всем. Все запросы открыты.
                // Для доступа к заказам - пользователь должен быть аутентифицирован.
                .authorizeRequests()
                .antMatchers("/api/v1/orders/**").authenticated()
                .antMatchers("/api/v1/profile").authenticated()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().permitAll()

                .and()
                // Не хотим чтобы у нас хранилось состояние сессии. И конкретно для безопасности мы сессии отключаем. Не будет теперь у контекста привязки к сессии. С каждым запросом будем себя аутентифицировать.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// Stateless - данные удаляются из контекста, как только сервер отправит ответ.
                .and()
                // Т.к. сессию отключили - для того чтобы работала h2 коносль включаем дополнительный header.
                .headers().frameOptions().disable()
                .and()
                // Обработка исключений
                // У спринга есть стандартный endPoint куда он перенаправляет пользователя, если требуется аутентификация. Например: спринг понимает что вы гость, но вы стучитесь в админку, по хорошему Spring может вас заредиректить на страницу логина и пароля, чтобы вы его ввели и он вас отправил в эту админку.
                // Если вы стучитесь на endPoint, в котором вы должны быть аутентифицированы (профиль например), то Spring тоже понимает что вы должны себя как то представить и он пустит.
                // Но наш бекенд без понятия что такое фронт. Он не знаем где у вас какие формы лежат, не знает с чего вы заходите (пк, телефон). И поэтому 'authenticationEntryPoint' (вход на аутентификацию) мы указываем как 401 ошибка.
                // То есть если пользователю нужно было войти для доступа к защищенным данным, то мы просто веренм 401 (не авторизован). И после этого фронт должен понять какую он форму должен показать пользователю (мы на себя это взять не можем).
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        /*
        UsernamePasswordAuthenticationFilter - фильтр, берет логин и пароль из входящих данных и пытается проверить токен если требуется.
        Ставим свою логику до этого фильтра.
        Почесу До? Потому что если не поставить до, то юзер попадем в контекст и уже не будет никакой логики по аутентификации.
         */
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // создаем простейший authenticationManager в виде спрингового бина, чтобы он заинжектил себе наш UserDetailsService и мог проверять логины и пароли и возвращать нам какой то результат.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
