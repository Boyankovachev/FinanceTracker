package com.diplomna.configuration;

import com.diplomna.database.DatabaseConnection;
import com.diplomna.users.sub.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String inputUsername = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();

        DatabaseConnection databaseConnection = new DatabaseConnection();
        User user = databaseConnection.read().readUsers().getUserByName(inputUsername);

        if(user==null){
            return null;
        }
        else {
            if(user.checkPassword(inputPassword)) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (user.getIs2FactorAuthenticationRequired()) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_VERIFY"));
                } else {
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }
                return new UsernamePasswordAuthenticationToken(inputUsername, inputPassword, authorities);
            }
            else {
                return null;
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
