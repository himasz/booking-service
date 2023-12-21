package com.statista.code.challenge.service;

import com.statista.code.challenge.common.dto.BookingDTO;
import com.statista.code.challenge.common.dto.CurrencySumDTO;

import java.util.List;
import java.util.Set;

public interface BookingService {

    void createOrUpdateBooking(String bookingId, BookingDTO bookingDTO);

    BookingDTO getBooking(String bookingId);

    Set<String> getDepartmentBookingIds(String department);

    Set<String> getBookingCurrencies();

    CurrencySumDTO getCurrencySum(String currency);

    String doBusiness(String bookingId);
}
