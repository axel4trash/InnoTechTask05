package edu.innotech.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tpp_ref_account_type")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TppRefAccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internal_id;

    @Column(unique = true)
    @Size(max = 100)
    private String value; // -->> TppRefProductRegisterType.account_type
}
