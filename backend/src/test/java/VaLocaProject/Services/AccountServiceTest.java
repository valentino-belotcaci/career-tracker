package VaLocaProject.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
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


    public void testGetAllUsers() {

        User user = new User(1L);

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = accountService.getAllUsers(); 

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    public void testGetAllCompanies() {

        Company company = new Company(1L);

        when(companyRepository.findAll()).thenReturn(List.of(company));

        List<Company> result = accountService.getAllCompanies(); 

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(companyRepository, times(1)).findAll();
    }

}