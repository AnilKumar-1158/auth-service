package com.realmart.authservice.dto;

import com.realmart.authservice.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private User user;
    public CustomUserDetails(User user){
       this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       String[] roles = user.getRole().split(",");
      return  Arrays.stream(roles).map( role ->
      new SimpleGrantedAuthority(String.format("%s_%s", "ROLE", role))).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.getAccountStatus().equals("ACTIVE");
    }
}
