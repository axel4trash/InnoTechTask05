package edu.innotech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalPropertiesVipDto {
    List<AddPropertyDto> data;
}
