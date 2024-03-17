package spring.example.employeesalarymanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestModel {
    private Integer accountId;
    private String accountName;
    private double savings;
    private Integer accountNumber;
    private Double accountBalance;
    private String bankName;
    private String branchName;
}
