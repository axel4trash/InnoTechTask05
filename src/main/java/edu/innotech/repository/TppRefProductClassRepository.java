package edu.innotech.repository;

import edu.innotech.entity.TppRefProductClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TppRefProductClassRepository extends JpaRepository<TppRefProductClass, String> {
    @Query(value = "select c.* from tpp_ref_product_class c, tpp_ref_product_register_type t where c.value = t.product_class_code and c.value = :productCode and t.account_type = :accountType", nativeQuery = true)
    List<TppRefProductClass> getListProductCatalog(@Param("productCode") String productCode,
                                                   @Param("accountType") String accountType);
}
