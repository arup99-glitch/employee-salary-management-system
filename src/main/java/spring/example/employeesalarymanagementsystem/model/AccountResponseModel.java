package spring.example.employeesalarymanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseModel {
    private Integer accountId;
    private String accountName;
    private Integer accountNumber;
    private double savings;
    private Double accountBalance;
    private String bankName;
    private String branchName;
}
