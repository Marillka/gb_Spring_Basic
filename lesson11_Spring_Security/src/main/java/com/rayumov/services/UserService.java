package com.rayumov.services;

import com.rayumov.entities.Role;
import com.rayumov.entities.User;
import com.rayumov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/*
Наследуемя от UserDetailsService.
Он заставляет реализовать только одну функцию это - по имени найти вашего пользователя и преобразовать его к UserDetails
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // находит пользователя в БД и преобразует его в пользователя, который понимает спринг.
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Пытаемся найти пользователя в БД. Если нашли его - то все ок. Если не нашли
        // кидаем UserNotFoundException
        // То есть если выпадает Exception - то пользователю должно прилететь, что такой логин и пароль не найдены.
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        // Юзера нашли, теперь возвращаем спрингового юзера
        // mapRolesToAuthorities - здесь от нас ждут
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    /*
    GrantedAuthority - это просто обертка над строкой.
    То есть право доступа - это обычная строка.
    Чтобы преобразовать роли пользователя в GrantedAuthority -
    мы у пользователя запрашиваем роли, и каждую роль преобразуем в SimpleGrandAuthority(создаем объект, отдавая туда имя нашей роли).
    Собрали в коллекцию и вернули
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
