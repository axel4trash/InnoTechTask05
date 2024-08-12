package edu.innotech.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name="agreement")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private TppProduct product_id;

    @Size(max = 50)
    private String general_agreement_id;

    @Size(max = 50)
    private String supplementary_agreement_id;

    @Size(max = 50)
    private String arrangement_type;

    private Long scheduler_job_id;

    @Size(max = 50)
    private String number;

    private Timestamp opening_date;
    private Timestamp closing_date;
    private Timestamp cancel_date;

    private Long validity_duration;

    @Size(max = 100)
    private String cancellation_reason;

    @Size(max = 50)
    private String status;

    private Timestamp interest_calculation_date;

    private Double interest_rate;
    private Double coefficient;

    @Size(max = 50)
    private String coefficient_action;

    private Double minimum_interest_rate;
    private Double minimum_interest_rate_coefficient;

    @Size(max = 50)
    private String minimum_interest_rate_coefficient_action;

    private Double maximal_interest_rate;
    private Double maximal_interest_rate_coefficient;

    @Size(max = 50)
    private String maximal_interest_rate_coefficient_action;

}
