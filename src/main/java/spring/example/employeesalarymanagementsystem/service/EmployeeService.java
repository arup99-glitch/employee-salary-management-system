package spring.example.employeesalarymanagementsystem.service;

import org.springframework.http.ResponseEntity;
import spring.example.employeesalarymanagementsystem.model.EmployeeRequestModel;

public interface EmployeeService {
    ResponseEntity<Object> createEmployee(EmployeeRequestModel employeeRequestModel);
    ResponseEntity<Object> getAllEmployeeDetails();
    void deleteEmployee(Integer employeeId);
    ResponseEntity<Object> employeeDetails(Integer employeeId);
    ResponseEntity<Object> getEmployeeName(String employeeName);
    ResponseEntity<Object> updateEmployeeInfo(Integer id,EmployeeRequestModel updateEmployee);
    ResponseEntity<Object>getAllEmployeeSalarySheet();


}
