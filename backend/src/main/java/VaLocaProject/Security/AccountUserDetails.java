package VaLocaProject.Security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import VaLocaProject.Models.Account;

/**
 * Adapter that exposes your domain Account as Spring Security's UserDetails.
 */
public class AccountUserDetails implements UserDetails {

    private final Account account;

    public AccountUserDetails(Account account) {
        this.account = Objects.requireNonNull(account);
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // map your domain account type into a single role (adjust as needed)
        String type = account.getType() != null ? account.getType() : "UNKNOWN";
        String role = "ROLE_UNKNOWN";
        if ("USER".equalsIgnoreCase(type)) role = "ROLE_USER";
        if ("COMPANY".equalsIgnoreCase(type)) role = "ROLE_COMPANY";
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        // your Account uses email as username
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // change if you track expiry
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // change if you track lock state
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // change if you track credential expiry
    }

    @Override
    public boolean isEnabled() {
        return true; // change if you track enabled/disabled
    }
}
