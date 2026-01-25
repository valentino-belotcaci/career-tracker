package VaLocaProject.Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.UserRepository;

@Service
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // CONSIDER IMPLEMENTING HERE CACHE, AS ALL THE AUTH PROCESSES ARE GOING TO CALL THIS METHOD
        return userRepository.findByEmail(email)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                ))
                // If not found in users, check companies table 
                .or(() -> companyRepository.findByEmail(email)
                        .map(company -> new org.springframework.security.core.userdetails.User(
                                company.getEmail(),
                                company.getPassword(),
                                List.of(new SimpleGrantedAuthority("ROLE_COMPANY"))
                        )))
                // If neither exists
                .orElseThrow(() -> new UsernameNotFoundException("Account not found: " + email));
    }
}
