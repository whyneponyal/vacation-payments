package com.example.payments;

import com.example.payments.dto.ResponseDTO;
import com.example.payments.entity.Holidays;
import com.example.payments.exception.InvalidAverageSalaryException;
import com.example.payments.exception.InvalidVacationDaysException;
import com.example.payments.service.CalculatedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CalculatedServiceTest {

	LocalDate startDate = LocalDate.of(2025, 2, 1);

	@BeforeEach
	void getHolidays() {
		Holidays.getHolidays();
	}


	@Test
	public void testGetVacationPaymentWithHolidays_Successful() throws Exception {

		double expectedPayment = 34129.6928327645;

		assertEquals(expectedPayment, CalculatedService.getVacationPaymentWithHolidays(28, 50000.0, startDate));
	}

	@Test
	public void testGetVacationPaymentWithHolidays_NullArguments() {

		int quantityDaysVacation = 0;
		double averageSalary = 50000.0;
		LocalDate startDate = null;

		assertThrows(InvalidVacationDaysException.class, () ->
				CalculatedService.getVacationPaymentWithHolidays(quantityDaysVacation, averageSalary, startDate));
	}

	@Test
	public void testGetVacationPaymentWithHolidays_NoVacationDays() {

		Integer quantityDaysVacation = 0;
		Double averageSalary = 50000.0;
		LocalDate startDate = LocalDate.of(2025, 5, 1);

		assertThrows(InvalidVacationDaysException.class, () ->
				CalculatedService.getVacationPaymentWithHolidays(quantityDaysVacation, averageSalary, startDate));
	}

	@Test
	public void testValidateInputParameters() throws Exception {

		Integer validQuantityDaysVacation = 14;
		Double validAverageSalary = 50000.0;
		LocalDate validStartDate = LocalDate.of(2025, 5, 1);

		CalculatedService.validateInputParameters(validQuantityDaysVacation, validAverageSalary, validStartDate);
	}

	@Test
	public void testValidateInputParameters_NegativeVacationDays() {

		Integer invalidQuantityDaysVacation = -14;
		Double validAverageSalary = 50000.0;
		LocalDate validStartDate = LocalDate.of(2025, 5, 1);

		assertThrows(InvalidVacationDaysException.class, () ->
				CalculatedService.validateInputParameters(invalidQuantityDaysVacation, validAverageSalary, validStartDate));
	}

	@Test
	public void testValidateInputParameters_MissingStartDate() {

		Integer validQuantityDaysVacation = 14;
		Double validAverageSalary = 50000.0;
		LocalDate missingStartDate = null;

		assertThrows(IllegalArgumentException.class, () ->
				CalculatedService.validateInputParameters(validQuantityDaysVacation, validAverageSalary, missingStartDate));
	}

	@Test
	public void testValidateInputParameters_NegativeSalary() {

		Integer validQuantityDaysVacation = 14;
		Double negativeAverageSalary = -50000.0;
		LocalDate validStartDate = LocalDate.of(2025, 5, 1);

		assertThrows(InvalidAverageSalaryException.class, () ->
				CalculatedService.validateInputParameters(validQuantityDaysVacation, negativeAverageSalary, validStartDate));
	}

}
