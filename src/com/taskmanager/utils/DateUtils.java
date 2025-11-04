package com.taskmanager.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    // making a object from DateTimeFormatter from this class and we defining the
    // date format !!!!
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // making the special function using special datatype LocalDate
    // making function take the user date and convert into the data object main
    // (LocalDate) and than we use dateString.year()
    public static LocalDate parseDate(String dateString) throws IllegalArgumentException {
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (IllegalAccessError e) {
            throw new IllegalAccessError("Invalid date format. Use DD-MM-YYYY " + e.getMessage());

        }

    }

    public static String formatDate(LocalDate date) {
        return date.format(formatter);
    }

    // function to check is this future date or this is the current date

    public static boolean isfurtureDate(LocalDate date) {
        LocalDate today_date = LocalDate.now();
        return !date.isBefore(today_date);

        // comparing two dates date and current date
        // date is earlier than today date return true so we use ! before this
    }
}