package edu.innotech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstanceDto {
    private Long instanceId;
    @NotBlank
    private String productType;
    @NotBlank
    private String productCode;
    @NotBlank
    private String registerType;
    @NotBlank
    private String mdmCode;
    @NotBlank
    private String contractNumber; //tpp_product.number == Request.Body.ContractNumber

    @NotNull
    private Date contractDate;

    @NotNull
    private Integer priority;

    private Double interestRatePenalty;
    private Double minimalBalance;
    private Double thresholdAmount;
    private String accountingDetails;
    private String rateType;
    private Double taxPercentageRate;
    private Double technicalOverdraftLimitAmount;
    @NotNull
    private Integer contractId;
    @NotBlank
    private String branchCode;
    @NotBlank
    private String isoCurrencyCode;
    @NotBlank
    private String urgencyCode;
    private Integer referenceCode;
    AdditionalPropertiesVipDto additionalPropertiesVip;
    List<AgreementDto> instanceArrangement;

}
