package edu.innotech.mapper;

import edu.innotech.dto.GetAccountDto;
import edu.innotech.entity.Account;

public class AccountMapper {

    public static GetAccountDto MapToGetAccountDto(Account account){
        GetAccountDto getAccountDto = new GetAccountDto(
                account.getId(),
                account.getAccountPoolId().getId(),
                account.getAccountNumber()
        );
        return getAccountDto;
    }
}
