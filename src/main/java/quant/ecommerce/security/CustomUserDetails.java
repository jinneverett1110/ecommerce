package quant.ecommerce.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import quant.ecommerce.entity.auth.User;

import java.util.Collection;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Integer id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public static CustomUserDetails from(User user) {
        String role = user.getRole().getName();
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + role));

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
