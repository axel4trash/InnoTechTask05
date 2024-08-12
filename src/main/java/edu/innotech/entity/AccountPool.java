package edu.innotech.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name="account_pool")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "account_pool_id")
    private List<Account> accounts; //список счетов пула

    @Size(max = 50)
    private String branch_code; //Код филиала

    @Size(max = 30)
    private String currency_code; //Код валюты

    @Size(max = 50)
    private String mdm_code; //Id клиента

    @Size(max = 30)
    private String priority_code; //Код срочности

    @Size(max = 50)
    private String registry_type_code; //Тип регистра

    @Override
    public String toString() {
        return "AccountPool{" +
                "id=" + id +
                ", branch_code='" + branch_code + '\'' +
                ", currency_code='" + currency_code + '\'' +
                ", mdm_code='" + mdm_code + '\'' +
                ", priority_code='" + priority_code + '\'' +
                ", registry_type_code='" + registry_type_code + '\'' +
                '}';
    }
}
