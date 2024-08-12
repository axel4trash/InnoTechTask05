package edu.innotech.repository;

import edu.innotech.entity.TppRefProductRegisterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TppRefProductRegisterTypeRepository extends JpaRepository<TppRefProductRegisterType, Long> {
    TppRefProductRegisterType getByValue(String registryTypeCode);
    List<TppRefProductRegisterType> findFirstByValue(String value);
}
