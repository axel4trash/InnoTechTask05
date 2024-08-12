package edu.innotech.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tpp_ref_product_class")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TppRefProductClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internal_id;

    @Column(unique = true)
    @Size(max = 100)
    private String value; // -> TppRefProductRegisterType.product_class_code

    @Size(max = 50)
    private String gbi_code;

    @Size(max = 100)
    private String gbi_name;

    @Size(max = 50)
    private String product_row_code;

    @Size(max = 100)
    private String product_row_name;

    @Size(max = 50)
    private String subclass_code;

    @Size(max = 100)
    private String subclass_name;
}
