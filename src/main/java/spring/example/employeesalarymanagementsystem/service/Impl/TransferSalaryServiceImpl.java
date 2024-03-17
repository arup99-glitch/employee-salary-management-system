package spring.example.employeesalarymanagementsystem.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring.example.employeesalarymanagementsystem.entity.AccountEntity;
import spring.example.employeesalarymanagementsystem.entity.EmployeeEntity;
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
        List<EmployeeEntity> employees = employeeRepository.findAll();
        AccountEntity accountEntity = accountRepository.findByAccountId(accountId);
        for (EmployeeEntity employee : employees) {
            double basicSalary = calculateBasicSalary(employee.getGrade(),transferMoneyRequestModel);
            double houseRent = basicSalary * 0.20;
            double medicalAllowance = basicSalary * 0.15;
            double totalSalary = basicSalary + houseRent + medicalAllowance;
            transferSalary(employee,accountEntity, totalSalary);
        }
    }

    private Double calculateBasicSalary (Integer grade,TransferMoneyRequestModel transferMoneyRequestModel){
        lowestGradeBasicSalary = transferMoneyRequestModel.getAmount();
        return lowestGradeBasicSalary + (6 - grade + 1) * 5000;
    }

    private void transferSalary (EmployeeEntity employee,AccountEntity accountEntity,double totalSalary){
        AccountEntity companyAccount = accountRepository.findByAccountId(accountEntity.getAccountId());
        if(companyAccount != null) {
            if (companyAccount.getAccountBalance() >= totalSalary) {
                boolean isSalaryDeducted = deductSalaryFromCompanyAccount(companyAccount,totalSalary);
                if(isSalaryDeducted){
                    boolean isSalaryCredited = creditSalaryToEmployeeAccount(employee,totalSalary);
                    if (isSalaryCredited) {
                        System.out.println("Salary Transferred successfully for employee :"+employee.getEmployeeId());
                    }else{
                        System.out.println("Failed to Credit salary to employee");
                    }
                }else{
                    System.out.println("Failed to deduct salary");
                }
            }else {
                System.out.println("Insufficient funds");
            }
        }
    }

    private boolean creditSalaryToEmployeeAccount(EmployeeEntity employee, double totalSalary) {
        if(employee != null && employee.getGrade()==6){
            double newBalance = employee.getBankAccount() +totalSalary;
            employee.setBankAccount(newBalance);
            employeeRepository.save(employee);
        }
        else {
            System.out.println("Employee Grade must be 6");

        }
        return false;
    }

    private boolean deductSalaryFromCompanyAccount(AccountEntity companyAccount, double totalSalary) {
        if(companyAccount.getAccountBalance() >= totalSalary){
            double newBalance = companyAccount.getAccountBalance()-totalSalary;
            companyAccount.setAccountBalance(newBalance);
            accountRepository.save(companyAccount);
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<Object> addMoneyToCompanyAccount(Integer accountId, AddMoneyRequestModel addMoneyRequestModel) {
        AccountEntity accountEntity = accountRepository.findByAccountId(accountId);
        if(accountEntity != null){
            accountEntity.setAccountBalance(accountEntity.getAccountBalance() + addMoneyRequestModel.getAddMoney());
            AccountEntity savedAddMoneyToCompanyAccount = accountRepository.save(accountEntity);
            return new ResponseEntity<>(savedAddMoneyToCompanyAccount, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @Override
//    public ResponseEntity<Object> lowestGradeBasicSalary(double lowestGradeBasicSalary) {
//        this.lowestGradeBasicSalary = lowestGradeBasicSalary;
//        return new ResponseEntity<>("Lowest grade basic salary has been updated successfully", HttpStatus.OK);
//    }

    @Override
    public Map<String,Double> totalPaidSalaryAndRemainingBalance(Integer accountId) {
        List<EmployeeEntity>employees = employeeRepository.findAll();
        double totalPaidSalary =0.0;
        for(EmployeeEntity employee : employees){
            totalPaidSalary += employee.getBankAccount();
        }
        AccountEntity companyAccount = accountRepository.findByAccountId(accountId);
        if(companyAccount == null){
           throw new IllegalArgumentException("Company account with ID"+ accountId + "not found");
//           return new RuntimeException("Company Account is Not Found",);
        }
        Map<String,Double> result = new HashMap<>();
        result.put("totalPaidSalary",totalPaidSalary);
        result.put("remainingBalance", companyAccount.getAccountBalance());
        return result;

    }

}
