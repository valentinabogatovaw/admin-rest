package ru.vitasoft.adminrest.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.vitasoft.adminrest.entity.Role;
import ru.vitasoft.adminrest.entity.User;
import ru.vitasoft.adminrest.exception.UserNotFoundException;
import ru.vitasoft.adminrest.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);

        if(Objects.isNull(user)) {
            throw new UserNotFoundException("user not found");
        }

        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getAuthority());
        }).collect(Collectors.toList());

        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities);
        return userDetails;

    }
}