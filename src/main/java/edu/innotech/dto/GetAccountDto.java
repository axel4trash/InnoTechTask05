package edu.innotech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAccountDto {
    private Long id;
    private Long account_pool_id;
    private String account_number;
}
