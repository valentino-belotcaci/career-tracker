package VaLocaProject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {

    private Long accountId;
    private String email;
    private String type;

    public AccountDTO(Long accountId, String email, String type){
        this.accountId = accountId;
        this.email = email;
        this.type = type;
    }
}
