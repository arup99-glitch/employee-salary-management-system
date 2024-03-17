package spring.example.employeesalarymanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.example.employeesalarymanagementsystem.model.AddMoneyRequestModel;
import spring.example.employeesalarymanagementsystem.model.TransferMoneyRequestModel;
import spring.example.employeesalarymanagementsystem.service.AccountService;
import spring.example.employeesalarymanagementsystem.service.TransferSalaryService;

import java.util.Map;

@RestController
@RequestMapping("/salary")
@RequiredArgsConstructor
public class TransferSalaryController {
    private final TransferSalaryService transferSalaryService;


    @PostMapping("/transfer/{accountId}/{employeeId}")
    public ResponseEntity<Object> transferSalary(@PathVariable Integer accountId, @PathVariable Integer employeeId, @RequestBody TransferMoneyRequestModel transferMoneyRequestModel) {
        try {
            transferSalaryService.calculateSalary(accountId, employeeId,transferMoneyRequestModel);
            return ResponseEntity.ok("Salary transfer completed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during salary transfer: " + e.getMessage());
        }
    }
    @PostMapping("/addMoney/{accountId}")
    public ResponseEntity<Object> addMoneyToCompanyAccount(@PathVariable Integer accountId, @RequestBody AddMoneyRequestModel addMoneyRequestModel, ModelAndView model) {
        return transferSalaryService.addMoneyToCompanyAccount(accountId,addMoneyRequestModel);
    }
//    @PostMapping("/lowestGradeBasicSalary")
//    public ResponseEntity<Object> setLowestGradeBasicSalary(@RequestBody double lowestGradeBasicSalary) {
//        return transferSalaryService.lowestGradeBasicSalary(lowestGradeBasicSalary);
//    }
    @GetMapping("/viewRemainingBalanceAndTotalPaidSalary/{accountId}")
    public Map<String,Double>totalPaidSalaryAndRemainingBalance(@PathVariable Integer accountId) {
        return transferSalaryService.totalPaidSalaryAndRemainingBalance(accountId);
    }

}
