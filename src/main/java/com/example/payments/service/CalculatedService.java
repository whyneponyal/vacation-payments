package com.example.payments.service;

import com.example.payments.entity.Holidays;
import com.example.payments.exception.InvalidAverageSalaryException;
import com.example.payments.exception.InvalidVacationDaysException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

/**
 * Service-класс для расчета сумм отпускных выплат с учетом праздничных дней.
 */
@Service
public class CalculatedService {
    /**
     * Основной метод для расчета суммы отпускных выплат с учетом праздничных дней.
     *
     * @param quantityDaysVacation количество дней отпуска
     * @param averageSalary        средняя ежемесячная зарплата работника
     * @param startDate            начало периода отпуска
     * @return сумма отпускных выплат
     */
    public static Double getVacationPaymentWithHolidays(Integer quantityDaysVacation, Double averageSalary,
                                                        LocalDate startDate) throws Exception {

        validateInputParameters(quantityDaysVacation, averageSalary, startDate);

        Integer vacationDaysWithHolidays = getVacationDaysWithHolidays(startDate, quantityDaysVacation);

        Double paymentWithHolidays = (averageSalary / 29.3) * vacationDaysWithHolidays;
        return paymentWithHolidays;
    }

    /**
     * Метод для расчета количества действительных рабочих дней отпуска с учетом суббот, воскресений и праздников.
     *
     * @param startDate            дата начала отпуска
     * @param quantityDaysVacation количество заявленных дней отпуска
     * @return количество фактических рабочих дней отпуска
     */
    private static Integer getVacationDaysWithHolidays(LocalDate startDate, Integer quantityDaysVacation) {
        Integer vacationDaysWithHolidays = 0;
        LocalDate currentDate = startDate;

        Map<Month, List<Integer>> holidays = Holidays.getHolidays();

        for (int i = 0; i < quantityDaysVacation; i++, currentDate = currentDate.plusDays(1)) {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                continue;
            }

            if (holidays.containsKey(currentDate.getMonth())) {
                List<Integer> holidayDays = holidays.get(currentDate.getMonth());
                if (holidayDays.contains(currentDate.getDayOfMonth())) {
                    continue;
                }
            }
            vacationDaysWithHolidays++;
        }
        return vacationDaysWithHolidays;
    }


    /**
     * Валидатор средней заработной платы.
     */
    private static Boolean averageSalaryIsValid(Double averageSalary) {
        return averageSalary != null && averageSalary > 0;
    }

    /**
     * Валидатор количества дней отпуска.
     */
    private static Boolean vacationDaysIsValid(Integer vacationDays) throws InvalidVacationDaysException {
        if (vacationDays == null || vacationDays <= 0) {
            throw new InvalidVacationDaysException("Недопустимое количество дней отпуска!");
        }
        return true;
    }


    /**
     * Валидатор начальной даты отпуска.
     */
    private static Boolean startDateIsValid(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Начальная дата отпуска не указана!");
        }
        return true;
    }

    /**
     * Общий валидатор входных параметров для метода расчета отпускных выплат.
     *
     * @param quantityDaysVacation количество дней отпуска
     * @param averageSalary        средняя зарплата
     * @param startDate            дата начала отпуска
     */
    public static Boolean validateInputParameters(Integer quantityDaysVacation,
                                                  Double averageSalary, LocalDate startDate) throws Exception {

        if (!averageSalaryIsValid(averageSalary)) {
            throw new InvalidAverageSalaryException("Средняя зарплата не должна быть пустой!");
        }
        if (!vacationDaysIsValid(quantityDaysVacation)) {
            throw new InvalidVacationDaysException("Недопустимое количество дней отпуска!");
        }
        if (!startDateIsValid(startDate)) {
            throw new InvalidVacationDaysException("Указан некорректный формат даты начала отпуска!");
        }
        return true;
    }
}

