package com.statista.code.challenge;

import com.statista.code.challenge.common.dto.BookingDTO;
import com.statista.code.challenge.common.enums.Currency;

public class TestBookingData {
    public static final String ID_1 = "Id1";
    public static final String ID_2 = "Id2";
    public static final String ID_3 = "Id3";
    public static final String EMAIL = "email";
    public static final double PRICE_1 = 135.0;
    public static final double PRICE_2 = 513.0;
    public static final double PRICE_3 = 123.1;
    public static final Currency EUR = Currency.EUR;
    public static final Currency USD = Currency.USD;
    public static final String WRONG_ID = "Wrong Id";
    public static final String DEPARTMENT_1 = "department1";
    public static final String DEPARTMENT_2 = "department2";
    public static final String DESCRIPTION_1 = "description1";
    public static final String DESCRIPTION_2 = "description2";
    public static final String DESCRIPTION_3 = "description3";
    public static final BookingDTO FIRST_BOOKING = createBookingDTO(DESCRIPTION_1, PRICE_1, DEPARTMENT_1, EUR);
    public static final BookingDTO SECOND_BOOKING = createBookingDTO(DESCRIPTION_2, PRICE_2, DEPARTMENT_2, EUR);
    public static final BookingDTO THIRD_BOOKING = createBookingDTO(DESCRIPTION_3, PRICE_3, DEPARTMENT_2, USD);
    public static final BookingDTO BOOKING = createBookingDTO("DESCRIPTION", 121.0, "DEPARTMENT", Currency.AUD);

    protected static BookingDTO createBookingDTO(
            String description, Double price, String department, Currency currency) {
        return new BookingDTO(
                description,
                price,
                currency,
                System.currentTimeMillis(),
                EMAIL,
                department
        );
    }
}
