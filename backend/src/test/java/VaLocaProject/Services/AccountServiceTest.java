package VaLocaProject.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import VaLocaProject.Models.Account;
import VaLocaProject.Models.Company;
import VaLocaProject.Models.User;
import VaLocaProject.Repositories.CompanyRepository;
import VaLocaProject.Repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


class AccountControllerTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAccounts() {
        // Arrange 
        User user = new User(1L);
        Company company = new Company(2L);
        List<Account> accounts = Arrays.asList(user, company);

        // Mocking the repository methods
        when(accountService.getAllAccounts()).thenReturn(accounts);
        List<Account> result = accountService.getAllAccounts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(accountService, times(1)).getAllAccounts();
            
    }

}