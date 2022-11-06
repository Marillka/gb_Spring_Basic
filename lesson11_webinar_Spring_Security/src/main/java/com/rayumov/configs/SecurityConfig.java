package com.rayumov.configs;

import com.rayumov.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

// debug = true - посмотреть цепочку фильтров
//@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(securedEnabled = true) // Означает, что мы можете защищать отдельные методы
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/authenticated/**").authenticated()
//                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
//                .antMatchers("/profile/**").hasAuthority()
                .antMatchers("/only_for_admins/**").hasRole("ADMIN")
                .antMatchers("/read_profile/**").hasAuthority("READ_PROFILE")
                .and()
                .formLogin()
//                .loginProcessingUrl("/hellologin")
                .and()
                .logout().logoutSuccessUrl("/");
//                .and()
//                .csrf().disable();
    }


    // in memory users
    // храним все в памяти
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$ChgQigdse4kF7jrSNrYxtePp1Dq04i/BeCEA1QgPREPlOEBCTUwxy")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$ChgQigdse4kF7jrSNrYxtePp1Dq04i/BeCEA1QgPREPlOEBCTUwxy")
//                .roles("ADMIN", "USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    // jdbcAuthentication
    // храним все в бд
//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
////        UserDetails user = User.builder()
////                .username("user")
////                .password("{bcrypt}$2a$12$ChgQigdse4kF7jrSNrYxtePp1Dq04i/BeCEA1QgPREPlOEBCTUwxy")
////                .roles("USER")
////                .build();
////
////        UserDetails admin = User.builder()
////                .username("admin")
////                .password("{bcrypt}$2a$12$ChgQigdse4kF7jrSNrYxtePp1Dq04i/BeCEA1QgPREPlOEBCTUwxy")
////                .roles("ADMIN", "USER")
////                .build();
//
//        // есть юзеры, и их надо как то загнать в бд
//        // для этого создаем JdbcUserDetailsManager и инжектим туда dataSource.
//
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
////        if (jdbcUserDetailsManager.userExists(user.getUsername())) {
////            jdbcUserDetailsManager.deleteUser(user.getUsername());
////        }
////        if (jdbcUserDetailsManager.userExists(admin.getUsername())) {
////            jdbcUserDetailsManager.deleteUser(admin.getUsername());
////        }
////        jdbcUserDetailsManager.createUser(user);
////        jdbcUserDetailsManager.createUser(admin);
//        return jdbcUserDetailsManager;
//        // Теперь все юзеры будут в БД.
//    }


    // так выгдядит готовая jdbc конфигурация.
    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }


    // преобразователь паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // теперь у нас есть классы - роли и юзеры.
    // мы ему отдаем логин и пароль, а его задача сказать - существует такой пользователь или нет. И если существуеют, то его нужно положить в SpringSecurityContext.
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);// сервис предоставляет провайдеру юзеров.
        return authenticationProvider;
    }



}
