package spring.example.employeesalarymanagementsystem.service;

import org.springframework.http.ResponseEntity;
import spring.example.employeesalarymanagementsystem.model.AccountRequestModel;

public interface AccountService {
    ResponseEntity<Object>createBankAccount(AccountRequestModel accountRequestModel);
    ResponseEntity<Object> getAllAccount();
    void deleteAccount(Integer accountId);
    ResponseEntity<Object> accountDetails(Integer accountId);
    ResponseEntity<Object> getAccountDetails(String branchName);
    ResponseEntity<Object> updateAccountInfo(Integer id,AccountRequestModel updateAccountModel);
}
