package edu.innotech.controller;

import edu.innotech.dto.InstanceDto;
import edu.innotech.dto.RespInstanceDto;
import edu.innotech.service.InstanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/corporate-settlement-instance")
@Validated
public class InstanceController {
    @Autowired
    InstanceService instanceService;

    @PostMapping("/create")
    ResponseEntity<RespInstanceDto> createInstance(@Valid @RequestBody InstanceDto instanceDto) {
        RespInstanceDto respInstanceDto = instanceService.process(instanceDto);
        return new ResponseEntity<>(respInstanceDto, HttpStatus.CREATED);
    }
}
