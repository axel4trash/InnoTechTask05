package edu.innotech.service.impl;

import edu.innotech.dto.AccountDto;
import edu.innotech.dto.GetAccountDto;
import edu.innotech.dto.RespAccountDto;
import edu.innotech.dto.RespBodyAccountDto;
import edu.innotech.entity.Account;

import edu.innotech.entity.AccountPool;
import edu.innotech.entity.TppProductRegister;
import edu.innotech.entity.TppRefProductRegisterType;
import edu.innotech.enums.EnumProductRegisterStatus;
import edu.innotech.exceptions.BadRequestException;
import edu.innotech.exceptions.NoDataFoundException;
import edu.innotech.mapper.AccountMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.innotech.repository.AccountPoolRepository;
import edu.innotech.repository.AccountRepository;
import edu.innotech.repository.TppProductRegisterRepository;
import edu.innotech.repository.TppRefProductRegisterTypeRepository;
import edu.innotech.service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountPoolRepository accountPoolRepository;
    @Autowired
    TppProductRegisterRepository tppProductRegisterRepository;
    @Autowired
    TppRefProductRegisterTypeRepository tppRefProductRegisterTypeRepository;

    @Override
    @Transactional
    public RespAccountDto createProductRegister (AccountDto accountDto){
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(".createProductRegister:");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(accountDto);
        //TODO: переделать на Checker
        //1) Проверка на обязательность
        if (accountDto.getInstanceId() == null) throw new BadRequestException("instanceId не заполнено");

        //2) tpp_product_register - проверка на дубли
        // tpp_product_register.product_id == Request.Body.InstanceID
        // и tpp_product_register.type == Request.Body.registryTypeCode
        System.out.println(" Поиск ПР по ID="+accountDto.getInstanceId());
        TppProductRegister tppProductRegister = tppProductRegisterRepository.getByProductId(accountDto.getInstanceId());
        System.out.println("tppProductRegister="+tppProductRegister);

        if (tppProductRegister != null && tppProductRegister.getType().getValue().equals(accountDto.getRegistryTypeCode()))
            throw new BadRequestException("Параметр registryTypeCode тип регистра " + accountDto.getRegistryTypeCode()
                                            +" уже существует для ЭП с ИД  " + accountDto.getInstanceId()+".");

        //3) Проверка registryTypeCode по каталогу продуктов (tpp_ref_product_register_type)
        if (tppRefProductRegisterTypeRepository.getByValue(accountDto.getRegistryTypeCode()) == null)
            throw new NoDataFoundException("Код Продукта "+accountDto.getRegistryTypeCode()
                                            +" не найдено в Каталоге продуктов для данного типа Регистра");

        //4) Поиск счета (account) в таблице Пулов счетов (account_pool) по параметрам:
        //  branchCode, currencyCode, mdmCode, priorityCode, registryTypeCode
        // найдем пул счетов
        List<AccountPool> accountPools = accountPoolRepository.getPools( accountDto.getBranchCode(),
                                                                        accountDto.getCurrencyCode(),
                                                                        accountDto.getMdmCode(),
                                                                        accountDto.getPriorityCode(),
                                                                        accountDto.getRegistryTypeCode()
                                                                    );
        System.out.println("accountPools="+accountPools);
        if (accountPools.isEmpty()) {
            throw new NoDataFoundException("Не найден пул!");
        }
        //из пула возьмем счет
        List<Account> accList = accountRepository.findFirstByAccountPoolId(accountPools.get(0));
        Account newAccount = accList.get(0);
        //accountRepository.save(account);

        //5) Сформировать новый продуктовый регистр
        System.out.println("newAccount="+newAccount);
        List<TppRefProductRegisterType> lt = tppRefProductRegisterTypeRepository.findFirstByValue(accountDto.getRegistryTypeCode());

        tppProductRegister = TppProductRegister.builder()
                        .productId(accountDto.getInstanceId())
                        .type(lt.get(0))
                        .account(newAccount.getId())
                        .account_number(newAccount.getAccountNumber())
                        .currency_code(accountDto.getCurrencyCode())
                        .state(String.valueOf(EnumProductRegisterStatus.OPEN))
                        .build()
        ;
        System.out.println("tppProductRegister="+tppProductRegister);
        //Сохранить в БД с генерацией id
        tppProductRegisterRepository.save(tppProductRegister);
        System.out.println("after SAVE: tppProductRegister="+tppProductRegister);
        //-------------------------------
        RespAccountDto respAccountDto = new RespAccountDto();
        respAccountDto.setData(new RespBodyAccountDto(tppProductRegister.getId().toString()));
        return respAccountDto;
    }

    @Override
    public GetAccountDto getAccountById(Long id) {
        System.out.println("AccountServiceImpl.getAccountById: id="+id);
        Account account = accountRepository.findById(id)
                .orElseThrow(()->new NoDataFoundException("Account doesn't exists!"))
                ;
        return AccountMapper.MapToGetAccountDto(account);
    }

    @Override
    public GetAccountDto getAccountByNumber(String account_number) {
        System.out.println(".getAccountByNumber: account_number="+account_number);
        Account account = accountRepository.findByAccountNumber(account_number);
        System.out.println(".getAccountByNumber: account="+account);

        return AccountMapper.MapToGetAccountDto(account);
    }

    @Override
    public List<GetAccountDto> getAllAccounts() {
        System.out.println("AccountServiceImpl.getAllAccounts: ");
        List<Account> accountList = accountRepository.findAll();
        System.out.println(".getAllAccounts: accountList="+accountList);
        return accountList.stream()
                .map((account)->AccountMapper.MapToGetAccountDto(account))
                .collect(Collectors.toList());
    }

}
