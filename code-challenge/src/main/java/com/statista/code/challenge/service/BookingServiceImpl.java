package com.statista.code.challenge.service;

import com.statista.code.challenge.common.dto.BookingDTO;
import com.statista.code.challenge.common.dto.CurrencySumDTO;
import com.statista.code.challenge.common.enums.Currency;
import com.statista.code.challenge.common.error.exception.MissingDataException;
import com.statista.code.challenge.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final EmailService emailService;
    private final BookingRepository repository;
    @Override
    public void createOrUpdateBooking(String bookingId, BookingDTO bookingDTO) {
        repository.addOrUpdateBooking(bookingId, bookingDTO);
        emailService.sendEmail(bookingDTO.getEmail(), bookingDTO.getDescription());
    }

    @Override
    public BookingDTO getBooking(String bookingId) {
        return Optional.ofNullable(repository.getBooking(bookingId))
                .orElseThrow(() -> new MissingDataException("No booking found for bookingId: " + bookingId));
    }

    @Override
    public Set<String> getDepartmentBookingIds(String department) {
        return Optional.ofNullable(repository.getDepartmentBookings(department))
                .orElseThrow(() -> new MissingDataException("No ids found for department: " + department));
    }

    @Override
    public Set<String> getBookingCurrencies() {
        return repository.getBookingCurrencies();
    }

    @Override
    public CurrencySumDTO getCurrencySum(String currencyString) {
        try {
            Currency currency = Currency.fromString(currencyString);
            return new CurrencySumDTO(repository.getSum(currency));
        } catch (IllegalArgumentException e) {
            throw new MissingDataException(currencyString);
        }
    }

    @Override
    public String doBusiness(String bookingId) {
        return "Business is done perfectly!";
    }

}
