package edu.innotech.repository;

import edu.innotech.entity.TppProduct;
import edu.innotech.entity.TppProductRegister;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TppProductRepository extends JpaRepository<TppProduct, Long> {
    TppProduct getFirstByNumber(String productNumber);
}
