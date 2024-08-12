package edu.innotech.repository;

import edu.innotech.entity.TppProductRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TppProductRegisterRepository extends JpaRepository<TppProductRegister, Long> {
    TppProductRegister getByProductId(Long product_id);
}
