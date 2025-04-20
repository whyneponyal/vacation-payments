package com.example.payments.controller;

import com.example.payments.dto.ResponseDTO;
import com.example.payments.service.CalculatedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CalculatedController {

    private final CalculatedService calculatedService;

    @GetMapping("/calculate")
    public ResponseEntity<Object> calculate(@RequestParam int quantityVacationDays,
                                            @RequestParam double averageSalary,
                                            @RequestParam LocalDate startDate){
        try{
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setAverageSalary(averageSalary);
            responseDTO.setQuantityVacationDays(quantityVacationDays);
            responseDTO.setStartDate(startDate);

            Double paymentWithHolidays = calculatedService.getVacationPaymentWithHolidays(quantityVacationDays,
                    averageSalary, startDate);

            responseDTO.setPaymentWithHolidays(paymentWithHolidays);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
