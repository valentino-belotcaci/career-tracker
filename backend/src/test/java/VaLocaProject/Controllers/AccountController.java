package VaLocaProject.Controllers;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import VaLocaProject.Controllers.AccountController;
import VaLocaProject.Models.Account;
import VaLocaProject.Services.AccountService;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void getAllAccounts_shouldReturnListOfAccounts() throws Exception {
        // given
        List<Account> accounts = List.of(
                new Account(1L),
                new Account(2L)
        );

        when(accountService.getAllAccounts()).thenReturn(accounts);

        // when + then
        mockMvc.perform(get("/getAllAccounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }
}
