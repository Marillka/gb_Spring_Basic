package com.rayumov.configs;

import com.rayumov.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

/*
Создаем конфигураци безопасности и наследуемся от WebSecurityConfigurerAdapter.
@EnableWebSecurity - включаем правила безопасности
 */

@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    /*
     Здесь непосредственно конфигуриреутся HttpSecurity

     Говорим, что хотим настроить параметры авторизации запросов, и идут такие antMatchers.
     Говорим, что если кто то постучался в корень нашего приложения + /user_page/ *и дальше все что угодно*, то мы хотим, чтобы этот пользователь был аутентифицированным (роль не важна, главное чтобы это не гость заходил).
     Если кто-то постучался на user_info, то он тоже должен быть аутентифицирован.
     Если кто-то стучится на admin, то он должен иметь роль или ADMIN или SUPERADMIN (гость тут тоже не подойдет, потому что у гостя никаких ролей нет).

    .anyRequest().permitAll() - все остальные endpoints доступны абсолютно всем.


       .and()
       .formLogin() - для того чтобы пользователь смог залогиниться - предоставляем ему форму для ввода логина.

       .and()
       .httpBasic() - или можно так, означает - если пользователь не вошедший, то ему покажется стандартная форма с двумя полями (логин, пароль) и кнопкой ОК.

        .logout()
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID"); - при logout хотим удалить куки "JSESSIONID", чтобы затереть все, что пользователь  делал.

        .and()
        .sessionManagement()
        .maximumSessions(1)
        .maxSessionsPreventsLogin(true); - можем указать сколько сессий доступно для одного подключения

        Также можно выбирать достпу по ролям или доступ по по правам.
        Например ROLE_USER и AUTHORITY_READ_ALL_MESSAGES

        Еслу вы говорите hasRole("USER") - то Spring идет в List<SimpleGrandAuthority> и ищет ROLE_USER ( к вашему слову просто добавляется prefix ROLE).
        А если вы говорите hasAuthority("READ_MSGS) - то Spring в листе ваших прав напрямую ищет это слово "READ_MSGS".

     */


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Dao Authentication Provider");
        http.authorizeRequests()
                .antMatchers("/auth_page/**").authenticated()
                .antMatchers("/user_info").authenticated()
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN") // ROLE_ADMIN, ROLE_SUPERADMIN
//                .antMatchers("/..").hasAnyAuthority("READ_MSGS")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
//                .and()
//                .sessionManagement()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true);
    }

    // В зависимости от того, какой Encoder задается - такое хеширование паролей и будет.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Создаем AuthenticationProvider
    // Для его работы нужен PasswordEncoder(будет заниматься хешированием паролей и сравненим хешей от паролей).
    // И соответственно еще нужен источник данных с преобразователем - userService.
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

//    /*
//     Если пользователи живут в памяти -
//     создается UserDetailsService - в нем создаются UserDetails.
//     И создается InMemoryUserDetailsManager - он сразу является и UsersDetails и Provider (2 в одном).
//     */
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{noop]100")// noop - праоль будет хранится в чистом видке
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$hAspwMsJPpcXs0U.gn4uWemL1JDseEnXdo.DE.Gbb8.n.uz3RAfkO")// bcrypt - хеш от пароля
//                .roles("USER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    /*
    JDBC Authentication
    Здесь создается JdbcUserDetailsManager куда инжектитья DataSource.
    DataSource - это бин, который отвечает за подключенике к БД.
     */
    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }


}
