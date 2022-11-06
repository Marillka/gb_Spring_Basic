package com.rayumov.services;

import com.rayumov.entities.Role;
import com.rayumov.entities.User;
import com.rayumov.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;


// задача этого сервиса, по имени пользователя предоставить самого юзера.
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // LAZY INITIALIZATION!!!!!!!!!!!!!!!!!!!!
    // Поскольку мы юзера достали, а списко ролей у нас является коллекцией, а коллекция ManyToMany не грузится в режиме EAGER (там стоит LazyLoad), то нужно все это взять в одну транзакцию.
    @Override
    @Transactional
//    @Secured("ROLE_ADMIN")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    // отдаем список ролей из юзера и запихиваем их в коллекцию GrantedAuthorities
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }


}
