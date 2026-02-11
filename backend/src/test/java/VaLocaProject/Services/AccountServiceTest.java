package VaLocaProject.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import VaLocaProject.DTO.UpdateAccountDTO;
import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.UserRepository;
import VaLocaProject.Security.JWTService;


class AccountServiceTest {

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
    private CacheManager cacheManager;
    
    @Mock
    private Cache cache;
    
    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // ensure cache lookups inside AccountService don't NPE in unit tests
        when(cacheManager.getCache("accounts")).thenReturn(cache);
    }

    @Test
    void testGetAllAccounts() {
        // Arrange
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID companyId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        User user = new User(id1);
        Company company = new Company(companyId);

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

        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        User user = new User(id1);

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = accountService.getAllUsers(); 

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllCompanies() {

        UUID companyId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        Company company = new Company(companyId);

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
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        String email = "vale@tino.com";
        User user = new User(id1);
        user.setEmail(email);
        Company company = new Company(id1);
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
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID companyId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        User user = new User(id1);
        Company company = new Company(companyId);


        when(userRepository.findById(id1)).thenReturn(Optional.of(user));
        when(companyRepository.findById(id1)).thenReturn(Optional.of(company));


        //act
        Account result = accountService.getAccountById(id1);

        //assert
        assertNotNull(result);
        assertEquals(id1, result.getId());
        verify(userRepository, times(1)).findById(id1);
        verify(companyRepository, times(0)).findById(id1);
    }

    @Test
    public void testInsertAccount() {
        // Arrange
        String email = "vale@tino.com";
        String password = "1111";
        String encodedPassword = "encoded123";
        String type = "USER";
        String expectedToken = "mocked-jwt-token";


        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(jwtService.generateToken(email)).thenReturn(expectedToken);

        // Act
        String resultToken = accountService.insertAccount(email, password, type);

        // Assert
        assertNotNull(resultToken);
        assertEquals(expectedToken, resultToken);

        // Verifica che l'utente sia stato effettivamente salvato con i dati corretti
        verify(userRepository, times(1)).save(any(User.class));
        verify(companyRepository, times(0)).save(any());
        
        // Verifica che il token sia stato generato per l'email corretta
        verify(jwtService, times(1)).generateToken(email);
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
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
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