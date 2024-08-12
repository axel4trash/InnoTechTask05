package edu.innotech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data//don't provide constructor
@AllArgsConstructor
public class AgreementDto { //instanceArrangement
    private Integer id;
    private TppProductDto product_id;

    private String generalAgreementId;
    private String supplementaryAgreementId;
    private String arrangementType;
    private Long schedulerJobId;

    @NotNull
    private String number;    //Номер ДС

    @NotNull
    private Timestamp openingDate;

    private Timestamp closingDate;
    private Timestamp cancelDate;
    private Long validityDuration;
    private String cancellationReason;
    private String status;
    private Timestamp interestCalculationDate;
    private Double interestRate;
    private Double coefficient;
    private String coefficientAction;
    private Double minInterestRate;
    private Double minInterestRateCoefficient;
    private String minInterestRateCoefficientAction;
    private Double maxInterestRate;
    private Double maxInterestRateCoefficient;
    private String maxInterestRateCoefficientAction;
}

