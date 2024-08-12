package edu.innotech.service;

import edu.innotech.dto.AccountDto;
import edu.innotech.dto.GetAccountDto;
import edu.innotech.dto.RespAccountDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface AccountService {

    RespAccountDto createProductRegister(AccountDto account);

    //Get
    GetAccountDto getAccountById(Long id);
    GetAccountDto getAccountByNumber(String account_number);
    List<GetAccountDto> getAllAccounts();
}
