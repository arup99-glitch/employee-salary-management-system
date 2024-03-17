package spring.example.employeesalarymanagementsystem.service;

import org.springframework.http.ResponseEntity;
import spring.example.employeesalarymanagementsystem.model.AddMoneyRequestModel;
import spring.example.employeesalarymanagementsystem.model.TransferMoneyRequestModel;

import java.util.Map;

public interface TransferSalaryService {
    public void calculateSalary(Integer accountId, Integer employeeId, TransferMoneyRequestModel transferMoneyRequestModel);
    ResponseEntity<Object> addMoneyToCompanyAccount(Integer accountId, AddMoneyRequestModel addMoneyRequestModel);
//    ResponseEntity<Object>lowestGradeBasicSalary(double lowestGradeBasicSalary);
    public Map<String,Double> totalPaidSalaryAndRemainingBalance(Integer accountId);


}
