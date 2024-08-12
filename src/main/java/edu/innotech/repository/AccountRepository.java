package edu.innotech.repository;

import edu.innotech.entity.Account;
import edu.innotech.entity.AccountPool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(String number);
    List<Account> findFirstByAccountPoolId(AccountPool pool);
}
