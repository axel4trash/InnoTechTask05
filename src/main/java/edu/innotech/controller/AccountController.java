package edu.innotech.controller;

import edu.innotech.dto.AccountDto;
import edu.innotech.dto.GetAccountDto;
import edu.innotech.dto.RespAccountDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import edu.innotech.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/corporate-settlement-account")
@Validated
public class AccountController {
    @Autowired
    private AccountService accountService;

    //Create Product Register REST API
    @PostMapping("/create")
    public ResponseEntity<RespAccountDto> createProductRegister(@Valid @RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createProductRegister(accountDto), HttpStatus.CREATED);
    }

    //Get Account by Id REST API
    @GetMapping("/{id}")
    public ResponseEntity<GetAccountDto> getAccountById(@PathVariable Long id){
        System.out.println("AccountController.getAccountById: id = "+ id);
        GetAccountDto getAccountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(getAccountDto);
    }

    //Get Account by Number REST API
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetAccountDto> getAccountByNumber(@RequestParam(value="number") String number){
        GetAccountDto getAccountDto = accountService.getAccountByNumber(number);
        return ResponseEntity.ok(getAccountDto);
    }

    //Get All accounts REST API
    @GetMapping("/all")
    public ResponseEntity<List<GetAccountDto>> getAllAccounts(){
        System.out.println("AccountController.getAllAccounts: ");
        List<GetAccountDto> getAccountDtos = accountService.getAllAccounts();
        return ResponseEntity.ok(getAccountDtos);
    }
}
