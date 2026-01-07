package VaLocaProject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {

    private Long accountId;
    private String email;

    public AccountDTO(Long accountId, String email){
        this.accountId = accountId;
        this.email = email;
    }
}
