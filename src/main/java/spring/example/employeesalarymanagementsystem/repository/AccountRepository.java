package spring.example.employeesalarymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.example.employeesalarymanagementsystem.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Integer> {
    AccountEntity findByAccountId(Integer accountId);
    AccountEntity findByBranchName(String branchName);
}
