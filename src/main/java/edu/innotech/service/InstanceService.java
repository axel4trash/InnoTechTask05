package edu.innotech.service;

import edu.innotech.dto.InstanceDto;
import edu.innotech.dto.RespInstanceDto;

public interface InstanceService {
    RespInstanceDto process(InstanceDto instanceDto);
}
