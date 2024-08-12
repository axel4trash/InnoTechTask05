package edu.innotech.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long instanceId;        //Идентификатор экземпляра продукта, к которому привязывается продуктовый регистр - обязательный
    private String registryTypeCode;//Тип регистра
    private String accountType;     //Тип счета = Клиентский или внутрибанковский
    private String currencyCode;    //Код валюты
    private String branchCode;      //Код филиала
    private String priorityCode;    //Код срочности = Всегда «00» для ПП РО ЮЛ
    private String mdmCode;         //Id клиента
    private String clientCode;      //Код клиента = Только для ВИП (РЖД, ФПК)
    private String trainRegion;     //Регион принадлежности железной дороги = Только для ВИП (РЖД, ФПК)
    private String counter;         //Счетчик = Только для ВИП (РЖД, ФПК)
    private String salesCode;       //Код точки продаж
}
