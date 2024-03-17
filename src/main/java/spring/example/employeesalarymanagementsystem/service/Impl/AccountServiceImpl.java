package spring.example.employeesalarymanagementsystem.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring.example.employeesalarymanagementsystem.entity.AccountEntity;
import spring.example.employeesalarymanagementsystem.model.AccountRequestModel;
import spring.example.employeesalarymanagementsystem.model.AccountResponseModel;
import spring.example.employeesalarymanagementsystem.repository.AccountRepository;
import spring.example.employeesalarymanagementsystem.service.AccountService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private final AccountRepository accountRepository;

    @Override
    public ResponseEntity<Object> createBankAccount(AccountRequestModel accountRequestModel) {
        AccountEntity accountEntity = AccountEntity.
                builder()
                .accountName(accountRequestModel.getAccountName())
                .accountNumber(accountRequestModel.getAccountNumber())
                .savings(accountRequestModel.getSavings())
                .accountBalance(accountRequestModel.getAccountBalance())
                .bankName(accountRequestModel.getBankName())
                .branchName(accountRequestModel.getBranchName())
                .build();
        AccountEntity savedAccountDetails = accountRepository.save(accountEntity);
        AccountResponseModel accountResponseModel = AccountResponseModel.builder()
                .accountId(savedAccountDetails.getAccountId())
                .accountName(savedAccountDetails.getAccountName())
                .accountNumber(savedAccountDetails.getAccountNumber())
                .savings(savedAccountDetails.getSavings())
                .accountBalance(savedAccountDetails.getAccountBalance())
                .bankName(savedAccountDetails.getBankName())
                .branchName(savedAccountDetails.getBranchName())
                .build();
        return new ResponseEntity<>(accountResponseModel, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> getAllAccount() {
        List<AccountEntity> AccountList = accountRepository.findAll();
        List<AccountResponseModel> responseList = new ArrayList<>();
        for (AccountEntity account : AccountList){
            AccountResponseModel accountResponseModel = AccountResponseModel.builder()
                    .accountId(account.getAccountId())
                    .accountName(account.getAccountName())
                    .accountNumber(account.getAccountNumber())
                    .savings(account.getSavings())
                    .accountBalance(account.getAccountBalance())
                    .bankName(account.getBankName())
                    .branchName(account.getBranchName())
                    .build();
            responseList.add(accountResponseModel);
        }
        return new ResponseEntity<>(responseList,HttpStatus.OK);
    }

    @Override
    public void deleteAccount(Integer accountId) {
        AccountEntity accountEntity = accountRepository.findByAccountId(accountId);
        if(accountEntity != null){
            accountRepository.delete(accountEntity);
        }

    }

    @Override
    public ResponseEntity<Object> accountDetails(Integer accountId) {
        AccountEntity accountEntity = accountRepository.findByAccountId(accountId);
        if(accountEntity != null){
            AccountResponseModel accountResponseModel = AccountResponseModel.builder()
                    .accountId(accountEntity.getAccountId())
                    .accountName(accountEntity.getAccountName())
                    .accountNumber(accountEntity.getAccountNumber())
                    .savings(accountEntity.getSavings())
                    .accountBalance(accountEntity.getAccountBalance())
                    .bankName(accountEntity.getBankName())
                    .build();
            return new ResponseEntity<>(accountResponseModel,HttpStatus.OK);
        }
        return new ResponseEntity<>(new RuntimeException("Found Nothing"),HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> getAccountDetails(String branchName) {
        AccountEntity accountEntity = accountRepository.findByBranchName(branchName);
        if(accountEntity != null){
            AccountResponseModel accountResponseModel  = AccountResponseModel.builder()
                    .accountId(accountEntity.getAccountId())
                    .accountName(accountEntity.getAccountName())
                    .accountNumber(accountEntity.getAccountNumber())
                    .savings(accountEntity.getSavings())
                    .accountBalance(accountEntity.getAccountBalance())
                    .bankName(accountEntity.getBankName())
                    .build();
            return new ResponseEntity<>(accountResponseModel,HttpStatus.OK);
        }
        return new ResponseEntity<>(new RuntimeException("Found Nothing"),HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<Object> updateAccountInfo(Integer id, AccountRequestModel updateAccountModel) {
        AccountEntity accountEntity = accountRepository.findByAccountId(id);
        if(accountEntity != null){
            accountEntity.setAccountName(updateAccountModel.getAccountName());
            accountEntity.setAccountNumber(updateAccountModel.getAccountNumber());
            accountEntity.setBankName(updateAccountModel.getBankName());
            accountEntity.setBranchName(updateAccountModel.getBranchName());
            accountRepository.save(accountEntity);
            return new ResponseEntity<>(accountEntity,HttpStatus.OK);
        }
        return new ResponseEntity<>(new RuntimeException("Doesn't match"),HttpStatus.NOT_FOUND);
    }
}
