package edu.innotech.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tpp_product_register")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TppProductRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "value")
    private TppRefProductRegisterType type;

    private Long account;

    @Size(max = 30)
    private String currency_code;
    @Size(max = 50)
    private String state;
    @Size(max = 25)
    private String account_number;
}
