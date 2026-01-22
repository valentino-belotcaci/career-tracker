package VaLocaProject.Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.UserRepository;


@Service
// This class is the Spring Security bridge between our Account data and Spring’s authentication system
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // check users table
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Import to specify which User class to use (not ours)
            return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }

        // check companies table
        Company company = companyRepository.findByEmail(email);
        if (company != null) {
            return new org.springframework.security.core.userdetails.User(
                company.getEmail(),
                company.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_COMPANY"))
            );
        }

        throw new UsernameNotFoundException("Account not found: " + email);
    }
}
