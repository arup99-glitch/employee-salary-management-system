package spring.example.employeesalarymanagementsystem.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring.example.employeesalarymanagementsystem.entity.EmployeeEntity;
import spring.example.employeesalarymanagementsystem.model.EmployeeRequestModel;
import spring.example.employeesalarymanagementsystem.model.EmployeeResponseModel;
import spring.example.employeesalarymanagementsystem.repository.EmployeeRepository;
import spring.example.employeesalarymanagementsystem.service.EmployeeService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Override
    public ResponseEntity<Object> createEmployee(EmployeeRequestModel employeeRequestModel) {
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .employeeName(employeeRequestModel.getEmployeeName())
                .grade(employeeRequestModel.getGrade())
                .address(employeeRequestModel.getAddress())
                .mobileNumber(employeeRequestModel.getMobileNumber())
                .bankAccount(employeeRequestModel.getBankAccount())
                .build();
        EmployeeEntity savedEmployeeDetails = employeeRepository.save(employeeEntity);
        EmployeeResponseModel employeeResponseModel =EmployeeResponseModel.builder()
                .employeeId(savedEmployeeDetails.getEmployeeId())
                .employeeName(savedEmployeeDetails.getEmployeeName())
                .grade(savedEmployeeDetails.getGrade())
                .address(savedEmployeeDetails.getAddress())
                .mobileNumber(savedEmployeeDetails.getMobileNumber())
                .bankAccount(savedEmployeeDetails.getBankAccount())
                .build();
        return new ResponseEntity<>(employeeResponseModel, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> getAllEmployeeDetails() {
        List<EmployeeEntity> EmployeeList = employeeRepository.findAll();
        List<EmployeeResponseModel> responseList = new ArrayList<>();
        for (EmployeeEntity employee : EmployeeList){
            EmployeeResponseModel employeeResponseModel = EmployeeResponseModel.builder()
                    .employeeId(employee.getEmployeeId())
                    .employeeName(employee.getEmployeeName())
                    .grade(employee.getGrade())
                    .address(employee.getAddress())
                    .mobileNumber(employee.getMobileNumber())
                    .bankAccount(employee.getBankAccount())
                    .build();
            responseList.add(employeeResponseModel);
        }
        return new ResponseEntity<>(responseList,HttpStatus.OK);
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId);
        if(employeeEntity != null){
            employeeRepository.delete(employeeEntity);
        }

    }

    @Override
    public ResponseEntity<Object> employeeDetails(Integer employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId);
        if(employeeEntity != null){
            EmployeeResponseModel employeeResponseModel = EmployeeResponseModel.builder()
                    .employeeId(employeeEntity.getEmployeeId())
                    .employeeName(employeeEntity.getEmployeeName())
                    .grade(employeeEntity.getGrade())
                    .address(employeeEntity.getAddress())
                    .mobileNumber(employeeEntity.getMobileNumber())
                    .bankAccount(employeeEntity.getBankAccount())
                    .build();
            return new ResponseEntity<>(employeeResponseModel,HttpStatus.OK);
        }
        return new ResponseEntity<>(new RuntimeException("Nothing Found"),HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> getEmployeeName(String employeeName) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeName(employeeName);
        if(employeeEntity != null){
            EmployeeResponseModel employeeResponseModel = EmployeeResponseModel.builder()
                    .employeeId(employeeEntity.getEmployeeId())
                    .employeeName(employeeEntity.getEmployeeName())
                    .grade(employeeEntity.getGrade())
                    .address(employeeEntity.getAddress())
                    .mobileNumber(employeeEntity.getMobileNumber())
                    .bankAccount(employeeEntity.getBankAccount())
                    .build();
            return new ResponseEntity<>(employeeResponseModel,HttpStatus.OK);
        }
        return new ResponseEntity<>(new RuntimeException("Nothing Found"),HttpStatus.NOT_FOUND);
        }


    @Override
    public ResponseEntity<Object> updateEmployeeInfo(Integer id, EmployeeRequestModel updateEmployee) {
       EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(id);
       if(employeeEntity != null){
           employeeEntity.setEmployeeName(updateEmployee.getEmployeeName());
           employeeEntity.setGrade(updateEmployee.getGrade());
           employeeEntity.setAddress(updateEmployee.getAddress());
           employeeEntity.setMobileNumber(updateEmployee.getMobileNumber());
           employeeEntity.setBankAccount(updateEmployee.getBankAccount());
           employeeRepository.save(employeeEntity);
           return new ResponseEntity<>(employeeEntity,HttpStatus.OK);
       }

        return new ResponseEntity<>(new RuntimeException("Doesn't match employee id"),HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> getAllEmployeeSalarySheet() {
        List<EmployeeEntity> employeeList = employeeRepository.findAll();
        List<EmployeeResponseModel> responseList = new ArrayList<>();

        for(EmployeeEntity employee : employeeList){
           EmployeeResponseModel employeeResponseModel = EmployeeResponseModel.builder()
                   .employeeId(employee.getEmployeeId())
                   .employeeName(employee.getEmployeeName())
                   .bankAccount(employee.getBankAccount())
                   .build();
           responseList.add(employeeResponseModel);
        }
        responseList.sort(Comparator.comparingDouble(EmployeeResponseModel::getBankAccount).reversed());
        double previousBalance = Double.MAX_VALUE;
        int rank = 1;
        for(EmployeeResponseModel employee : responseList){
            if(employee.getBankAccount() < previousBalance){
                previousBalance = employee.getBankAccount();
                employee.setRank(rank++);
            }
        }
        return new ResponseEntity<>(responseList,HttpStatus.OK);
    }
}
