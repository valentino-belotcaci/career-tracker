package VaLocaProject.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.UserRepository;
import VaLocaProject.Security.JWTService;
import VaLocaProject.Security.RedisService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


class AccountControllerTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JWTService jwtService;
    
    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAccounts() {
        // Arrange
        User user = new User(1L);
        Company company = new Company(2L);

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(companyRepository.findAll()).thenReturn(List.of(company));

        // Act
        List<Account> result = accountService.getAllAccounts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(userRepository, times(1)).findAll();
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllUsers() {

        User user = new User(1L);

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = accountService.getAllUsers(); 

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllCompanies() {

        Company company = new Company(1L);

        when(companyRepository.findAll()).thenReturn(List.of(company));

        List<Company> result = accountService.getAllCompanies(); 

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteAllAccounts(){
        //act
        accountService.deleteAllAccounts();

        // Assert
        verify(userRepository, times(1)).deleteAll();
        verify(companyRepository, times(1)).deleteAll();
    }

    @Test
    public void testGetAccountByEmail(){
        //arrange

        String email = "vale@tino.com";
        User user = new User(1L);
        user.setEmail(email);
        Company company = new Company(1L);
        company.setEmail("loca@belo.com");


        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(companyRepository.findByEmail(email)).thenReturn(Optional.of(company));


        //act
        Account result = accountService.getAccountByEmail(email);

        //assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
        verify(companyRepository, times(0)).findByEmail(email);
    }

    @Test
    public void testGetAccountById(){
        Long id = 1L;
        User user = new User(id);
        Company company = new Company(2L);


        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(companyRepository.findById(id)).thenReturn(Optional.of(company));


        //act
        Account result = accountService.getAccountById(id);

        //assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(userRepository, times(1)).findById(id);
        verify(companyRepository, times(0)).findById(id);
    }

    @Test
    public void testInsertAccount() {
        // Arrange
        String email = "vale@tino.com";
        String password = "1111";
        String encodedPassword = "encoded123"; // fake encoded password
        String type = "USER";

        User savedUser = new User(1L); // what the repo should return
        savedUser.setEmail(email);
        savedUser.setPassword(encodedPassword);

        // Mock passwordEncoder
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // Mock repository save to return the saved user
        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class))).thenReturn(savedUser);

        // Act
        Account result = accountService.insertAccount(email, password, type);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(encodedPassword, result.getPassword());
        assertEquals("USER", result.getType());

        // Verify repository calls
        verify(userRepository, times(1)).save(org.mockito.ArgumentMatchers.any(User.class));
        verify(companyRepository, times(0)).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    public void testAuthenticate() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String fakeToken = "fake-jwt-token";

        Authentication mockAuth = org.mockito.Mockito.mock(Authentication.class);

        // Mock the AuthenticationManager
        when(authManager.authenticate(org.mockito.ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);

        // Mock authenticated status
        when(mockAuth.isAuthenticated()).thenReturn(true);

        // Mock JWTService
        when(jwtService.generateToken(email)).thenReturn(fakeToken);

        // Act
        String token = accountService.authenticate(email, password);

        // Assert
        assertEquals(fakeToken, token);

        // Verify that AuthenticationManager was called with correct credentials
        verify(authManager, times(1)).authenticate(
            org.mockito.ArgumentMatchers.argThat(argument ->
                argument.getName().equals(email) && argument.getCredentials().equals(password)
            )
        );

        // Verify JWTService was called
        verify(jwtService, times(1)).generateToken(email);
    }



}