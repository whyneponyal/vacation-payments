package com.example.payments.entity;

import java.time.Month;
import java.util.List;
import java.util.Map;

public class Holidays {
    public static Map<Month, List<Integer>> getHolidays() {
        return  Map.of(Month.JANUARY, List.of(1, 2, 3, 4, 5, 6, 7, 8),
                Month.FEBRUARY, List.of(22, 23),
                Month.MARCH, List.of(8, 9),
                Month.MAY, List.of(1, 2, 3, 4, 8, 9, 10, 11),
                Month.JUNE, List.of(12, 13, 14, 15),
                Month.NOVEMBER, List.of(2, 3, 4));
    }
}
