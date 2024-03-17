package spring.example.employeesalarymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.example.employeesalarymanagementsystem.entity.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
        EmployeeEntity findByEmployeeId(Integer employeeId);
        EmployeeEntity findByEmployeeName(String employeeName);
}
