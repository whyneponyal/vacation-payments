package com.example.payments.dto;

import lombok.*;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseDTO {
    private Double averageSalary;
    private Integer quantityVacationDays;
    private LocalDate startDate;
    private Double paymentWithHolidays;

    public ResponseDTO(Double paymentWithHolidays) {
        this.paymentWithHolidays = paymentWithHolidays;
    }
}
