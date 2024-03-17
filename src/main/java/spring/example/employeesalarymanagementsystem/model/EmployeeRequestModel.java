package spring.example.employeesalarymanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeRequestModel {
    private Integer employeeId;
    private String employeeName;
    private Integer grade;
    private String address;
    private Integer mobileNumber;
    private Double bankAccount;
}
