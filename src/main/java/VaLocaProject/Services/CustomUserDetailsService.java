package VaLocaProject.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Account;
import VaLocaProject.Repositories.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Account account = accountRepository.findByEmail(email);

        if (account == null) {
            throw new UsernameNotFoundException("Account not found");
        }

        return new User(
            account.getEmail(),
            account.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + account.getType()))
        );
    }
}
