package spring.example.employeesalarymanagementsystem.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring.example.employeesalarymanagementsystem.entity.AccountEntity;
import spring.example.employeesalarymanagementsystem.entity.EmployeeEntity;
import spring.example.employeesalarymanagementsystem.exception.CustomException;
import spring.example.employeesalarymanagementsystem.model.AddMoneyRequestModel;
import spring.example.employeesalarymanagementsystem.model.TransferMoneyRequestModel;
import spring.example.employeesalarymanagementsystem.repository.AccountRepository;
import spring.example.employeesalarymanagementsystem.repository.EmployeeRepository;
import spring.example.employeesalarymanagementsystem.service.TransferSalaryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransferSalaryServiceImpl implements TransferSalaryService {
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private final AccountRepository accountRepository;
    private double lowestGradeBasicSalary;
    private double transferSalary;

    @Override
    public void calculateSalary(Integer accountId, Integer employeeId, TransferMoneyRequestModel transferMoneyRequestModel) {
        try {
            List<EmployeeEntity> employees = employeeRepository.findAll();
            AccountEntity accountEntity = accountRepository.findByAccountId(accountId);
            for (EmployeeEntity employee : employees) {
                double basicSalary = calculateBasicSalary(employee.getGrade(), transferMoneyRequestModel);
                double houseRent = basicSalary * 0.20;
                double medicalAllowance = basicSalary * 0.15;
                double totalSalary = basicSalary + houseRent + medicalAllowance;
                transferSalary(employee, accountEntity, totalSalary);
            }
        }catch(CustomException ex){
            System.out.println("error occurred :" + ex.getMessage());
            throw new RuntimeException("An error occurred when calculated the totalSalary of each employee");
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("An error occurred when calculated the totalSalary of each employee");
        }
    }

    private Double calculateBasicSalary (Integer grade,TransferMoneyRequestModel transferMoneyRequestModel){
        try{
            lowestGradeBasicSalary = transferMoneyRequestModel.getAmount();
            return lowestGradeBasicSalary + (6 - grade + 1) * 5000;
        }catch(CustomException ex){
            System.out.println("error occurred :" + ex.getMessage());
            throw new RuntimeException("An error occurred when calculateBasicSalary of each employee");
        }
    }

    private void transferSalary (EmployeeEntity employee,AccountEntity accountEntity,double totalSalary){
        try {
            AccountEntity companyAccount = accountRepository.findByAccountId(accountEntity.getAccountId());
            if (companyAccount != null) {
                if (companyAccount.getAccountBalance() >= totalSalary) {
                    boolean isSalaryDeducted = deductSalaryFromCompanyAccount(companyAccount, totalSalary);
                    if (isSalaryDeducted) {
                        boolean isSalaryCredited = creditSalaryToEmployeeAccount(employee, totalSalary);
                        if (isSalaryCredited) {
                            System.out.println("Salary Transferred successfully for employee :" + employee.getEmployeeId());
                        } else {
                            System.out.println("Failed to Credit salary to employee");
                        }
                    } else {
                        System.out.println("Failed to deduct salary");
                    }
                } else {
                    System.out.println("Insufficient funds");
                }
            }
        }catch(CustomException ex){
            System.out.println("error occurred :" + ex.getMessage());
            throw new RuntimeException("An error occurred when transferSalary of each employee");
        }
    }

    private boolean creditSalaryToEmployeeAccount(EmployeeEntity employee, double totalSalary) {
        try {
            if (employee != null) {
                double newBalance = employee.getBankAccount() + totalSalary;
                employee.setBankAccount(newBalance);
                employeeRepository.save(employee);
            } else {
                System.out.println("Employee didn't found");

            }
            return false;
        } catch(CustomException ex){
            System.out.println("error occurred :" + ex.getMessage());
            throw new RuntimeException("An error occurred when creditSalaryToEmployeeAccount of each employee");
        }
    }

    private boolean deductSalaryFromCompanyAccount(AccountEntity companyAccount, double totalSalary) {
        try {
            if (companyAccount.getAccountBalance() >= totalSalary) {
                double newBalance = companyAccount.getAccountBalance() - totalSalary;
                companyAccount.setAccountBalance(newBalance);
                accountRepository.save(companyAccount);
                return true;
            }
            return false;
        }catch(CustomException ex){
            System.out.println("error occurred :" + ex.getMessage());
            throw new RuntimeException("An error occurred when deductSalaryFromCompanyAccount of each employee");
        }
    }

    @Override
    public ResponseEntity<Object> addMoneyToCompanyAccount(Integer accountId, AddMoneyRequestModel addMoneyRequestModel) {
        try {
            AccountEntity accountEntity = accountRepository.findByAccountId(accountId);
            if (accountEntity != null) {
                accountEntity.setAccountBalance(accountEntity.getAccountBalance() + addMoneyRequestModel.getAddMoney());
                AccountEntity savedAddMoneyToCompanyAccount = accountRepository.save(accountEntity);
                return new ResponseEntity<>(savedAddMoneyToCompanyAccount, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (CustomException ex){
            System.out.println("error occurred :" + ex.getMessage());
            throw new RuntimeException("An error occurred when add money to company account");
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("An error occurred when add money to company account");
        }
    }
    @Override
    public Map<String,Double> totalPaidSalaryAndRemainingBalance(Integer accountId) {
        try {
            List<EmployeeEntity> employees = employeeRepository.findAll();
            double totalPaidSalary = 0.0;
            for (EmployeeEntity employee : employees) {
                totalPaidSalary += employee.getBankAccount();
            }
            AccountEntity companyAccount = accountRepository.findByAccountId(accountId);
            if (companyAccount == null) {
                throw new CustomException("Company account with ID " + accountId + " not found");
            }
            Map<String, Double> result = new HashMap<>();
            result.put("totalPaidSalary", totalPaidSalary);
            result.put("remainingBalance", companyAccount.getAccountBalance());
            return result;
        } catch (CustomException ex) {
            System.out.println("Error occurred: " + ex.getMessage());
            throw new RuntimeException("An error occurred while calculating total paid salary and remaining balance.");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("An unexpected error occurred while calculating total paid salary and remaining balance.");
        }

    }

}
