package edu.innotech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RespBodyInstanceDto {
    String       instanceId;                    //Идентификатор экземпляра продукта
    List<String> registerId;                    // Идентификатор продуктового регистра, массив
    List<String> supplementaryAgreementId;      //ID доп.соглашения, массив
}
