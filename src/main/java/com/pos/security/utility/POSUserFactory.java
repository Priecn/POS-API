package com.pos.security.utility;

import com.pos.entity.security.Authority;
import com.pos.entity.security.User;
import com.pos.security.model.POSUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class POSUserFactory {

    public static POSUser create(User user) {
        return new POSUser(
                    user.getId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPassword(),
                    user.getEmail(),
                    mapToGrantedAuthority(user.getAuthorities()),
                    user.getEnabled(),
                    user.getLastPasswordResetDate()
                );
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(List<Authority> authorities) {
        return authorities.stream()
                          .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                          .collect(Collectors.toList());
    }
}
