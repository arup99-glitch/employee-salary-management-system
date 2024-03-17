package spring.example.employeesalarymanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.example.employeesalarymanagementsystem.model.EmployeeRequestModel;
import spring.example.employeesalarymanagementsystem.service.EmployeeService;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<Object> employeeCreate(@RequestBody EmployeeRequestModel employeeRequestModel){
        return employeeService.createEmployee(employeeRequestModel);
    }

    @GetMapping("/all")
    public ResponseEntity<Object>getAllEmployee(){
        return employeeService.getAllEmployeeDetails();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable Integer id){
        employeeService.deleteEmployee(id);
    }
    @GetMapping("/id/{employeeId}")
    public ResponseEntity<Object>employeeDetails(@PathVariable Integer employeeId){
        return employeeService.employeeDetails(employeeId);
    }
    @GetMapping("/employeeName/{employeeName}")
    public ResponseEntity<Object>getEmployeeName(@PathVariable String employeeName){
        return employeeService.getEmployeeName(employeeName);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<Object>updateEmployeeInfo(@PathVariable Integer employeeId, @RequestBody EmployeeRequestModel updateEmployeeInfo, ModelAndView model){
        return employeeService.updateEmployeeInfo(employeeId,updateEmployeeInfo);
    }
    @GetMapping("/salarySheet")
    public ResponseEntity<Object>getAllEmployeeSalarySheet(){
        return employeeService.getAllEmployeeSalarySheet();
    }
}
