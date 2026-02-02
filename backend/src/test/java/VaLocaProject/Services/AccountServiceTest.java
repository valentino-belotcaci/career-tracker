package VaLocaProject.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import VaLocaProject.DTO.UpdateAccountDTO;
import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.UserRepository;
import VaLocaProject.Security.JWTService;
import VaLocaProject.Security.RedisService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @Mock
    private RedisService redisService;
    
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
        String encodedPassword = "encoded123";
        String type = "USER";

        User savedUser = new User(1L);
        savedUser.setEmail(email);
        savedUser.setPassword(encodedPassword);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        Account result = accountService.insertAccount(email, password, type);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(encodedPassword, result.getPassword());
        assertEquals("USER", result.getType());

        verify(userRepository, times(1)).save(any(User.class));
        verify(companyRepository, times(0)).save(any());
    }

    @Test
    public void testAuthenticate() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String fakeToken = "fake-jwt-token";

        Authentication mockAuth = mock(Authentication.class);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuth);
        when(mockAuth.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(email)).thenReturn(fakeToken);

        // Act
        String token = accountService.authenticate(email, password);

        // Assert
        assertEquals(fakeToken, token);
        verify(authManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(email);
    }

    @Test
    public void testUpdateAccount_User() {
        // Arrange
        Long userId = 1L;
        User user = new User(userId);
        user.setEmail("old@email.com");
        user.setPassword("oldPassword");
        user.setFirstName("Old");
        user.setLastName("Name");

        UpdateAccountDTO updateDTO = new UpdateAccountDTO();
        updateDTO.setEmail("new@email.com");
        updateDTO.setPassword("newPassword");
        updateDTO.setFirstName("New");
        updateDTO.setLastName("User");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(companyRepository.findById(userId)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(updateDTO.getPassword())).thenReturn("encodedNewPassword");

        // Act
        Account updated = accountService.updateAccount(userId, updateDTO);

        // Assert
        assertNotNull(updated);
        assertTrue(updated instanceof User);
        User updatedUser = (User) updated;
        assertEquals("new@email.com", updatedUser.getEmail());
        assertEquals("encodedNewPassword", updatedUser.getPassword());
        assertEquals("New", updatedUser.getFirstName());
        assertEquals("User", updatedUser.getLastName());
    }

}