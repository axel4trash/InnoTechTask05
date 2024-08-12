package edu.innotech.repository;

import edu.innotech.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRepository extends JpaRepository<Agreement, Integer> {
    Agreement getFirstByNumber(String number);
}
