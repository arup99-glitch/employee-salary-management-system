package spring.example.employeesalarymanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.example.employeesalarymanagementsystem.model.AccountRequestModel;
import spring.example.employeesalarymanagementsystem.service.AccountService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Object>createAccount(@RequestBody AccountRequestModel accountRequestModel){
        return accountService.createBankAccount(accountRequestModel);
    }

    @GetMapping("/all")
    public ResponseEntity<Object>getAllAccount(){

        return accountService.getAllAccount();
    }
    @DeleteMapping("/deleteAccount/{accountId}")
    public void deleteAccount(@PathVariable Integer accountId){
        accountService.deleteAccount(accountId);
    }
    @GetMapping("/id/{accountId}")
    public ResponseEntity<Object>accountDetails(@PathVariable Integer accountId){
        return accountService.accountDetails(accountId);
    }
    @GetMapping("/branchName/{branchName}")
    public ResponseEntity<Object>getAccountDetails(@PathVariable String branchName){
        return accountService.getAccountDetails(branchName);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<Object>updateAccountInfo(@PathVariable Integer id, @RequestBody AccountRequestModel accountRequestModel, ModelAndView model){
        return accountService.updateAccountInfo(id,accountRequestModel);
    }

}
