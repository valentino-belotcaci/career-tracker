package VaLocaProject.Controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import VaLocaProject.DTO.UpdateAccountDTO;
import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Services.AccountService;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAccounts() {
        // Arrange (setup part)
        User user = new User(1L);

        Company company = new Company(2L);

        List<Account> accounts = Arrays.asList(user, company);

        //mocking the service method
        //when accountService.getAllAccounts() is called, then return the accounts list
        when(accountService.getAllAccounts()).thenReturn(accounts);

        // Act
        ResponseEntity<List<Account>> response = accountController.getAllAccounts();

        // Assert
        // Verify that the response status is OK
        assertEquals(200, response.getStatusCode().value());
        // Verify that the response body contains the expected accounts
        assertEquals(2, response.getBody().size());

        // Verify that the IDs match
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals(2L, response.getBody().get(1).getId());

        // Verify that the service method was called once
        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    public void testInsertAccount() {

        // Arrange
        Map<String, String> body = new HashMap<>();
        body.put("email", "vale@gmail.com");
        body.put("password", "1234");
        body.put("type", "USER");

        User account = new User(body.get("email"), body.get("password"));

        when(accountService.insertAccount(
            body.get("email"), 
            body.get("password"), 
            body.get("type")
        )).thenReturn(account);

        // Act
        ResponseEntity<Account> response = accountController.insertAccount(body);

        // Assert
        assertEquals(200, response.getStatusCode().value());

        assertEquals(account, response.getBody());

        verify(accountService, times(1)).insertAccount(
            body.get("email"), 
            body.get("password"), 
            body.get("type")
        );

    }

    @Test
    public void testDeleteAllAccounts(){

        //act
        ResponseEntity<String> response = accountController.deleteAllAccounts();

        //assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("All accounts deleted", response.getBody());

        verify(accountService, times(1)).deleteAllAccounts();

    }

    @Test
    public void testGetAccountByEmail() { 
        //arrange
        String email = "vale@email.com";
        User u = new User(1L);
        u.setEmail(email);

        when(accountService.getAccountByEmail(email)).thenReturn(u);

        //act
        ResponseEntity<Account> response = accountController.getAccountByEmail(email);

        //assert
         assertEquals(200, response.getStatusCode().value());
        assertEquals(u, response.getBody());
        verify(accountService, times(1)).getAccountByEmail(email);
    }



    @Test
    public void testGetAccountById() { 
        //arrange
        Long id = 1L;
        User u = new User(id);
        u.setId(id);

        when(accountService.getAccountById(id)).thenReturn(u);

        //act
        ResponseEntity<Account> response = accountController.getAccountById(id);

        //assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(u, response.getBody());
        verify(accountService, times(1)).getAccountById(id);
    }

    @Test
    public void testAuthenticateAccount() {

        // Arrange
        Map<String, String> body = new HashMap<>();
        body.put("email", "vale@capo.com");
        body.put("password", "1234");

        String fakeToken = "fake-jwt-token-123";

        User account = new User(body.get("email"), body.get("password"));
        account.setId(1L);

        when(accountService.authenticate(body.get("email"), body.get("password")))
                .thenReturn(fakeToken);

        when(accountService.getAccountByEmail(body.get("email")))
                .thenReturn(account);

        // Act
        ResponseEntity<?> response = accountController.authenticateAccount(body);

        // Assert
        assertEquals(200, response.getStatusCode().value());

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("USER", responseBody.get("type"));
        assertEquals(1L, responseBody.get("id"));

        verify(accountService, times(1)).authenticate(body.get("email"), body.get("password"));
        verify(accountService, times(1)).getAccountByEmail(body.get("email"));
    }


    @Test
    public void testLogout() {
        ResponseEntity<Map<String, String>> response = accountController.logout();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Logged out", response.getBody().get("message"));

        // Check that the Set-Cookie header exists
        assertTrue(response.getHeaders().containsHeader(HttpHeaders.SET_COOKIE));

        // Check that the cookie is expired
        String cookieValue = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        assertTrue(cookieValue.contains("Max-Age=0"));
    }

    @Test
    public void testUpdateAccount() {
        //arrange

        UpdateAccountDTO updateDTO = new UpdateAccountDTO();
        updateDTO.setEmail("updated@email.com");
        updateDTO.setPassword("updatedPassword");

        Long id = 1L;
        User updatedUser = new User(id);
        updatedUser.setId(id);


        when(accountService.updateAccount(id, updateDTO)).thenReturn(updatedUser);

        //act
        ResponseEntity<Account> response = accountController.updateAccount(id, updateDTO);
        //assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(updatedUser, response.getBody());  
        verify(accountService, times(1)).updateAccount(id, updateDTO);
    }
}
