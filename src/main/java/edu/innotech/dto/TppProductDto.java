package edu.innotech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class TppProductDto {
    private final Integer id;
    private final Long product_code_id;
    private final Long client_id;
    private final String type;
    private final String number;
    private final Long priority;
    private final Timestamp date_of_conclusion;
    private final Timestamp start_date_time;
    private final Timestamp end_date_time;
    private final Long days;
    private final Double penalty_rate;
    private final Double nso;
    private final Double threshold_amount;
    private final String requisite_type;
    private final String interest_rate_type;
    private final Double tax_rate;
    private final String reason_close;
    private final String state;
}
