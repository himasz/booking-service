package com.statista.code.challenge.repository;

import com.statista.code.challenge.common.enums.Currency;
import com.statista.code.challenge.common.dto.BookingDTO;
import com.statista.code.challenge.common.error.exception.MissingDataException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookingRepository {
    public static final String SPLIT = ":";
    private final Map<String, BookingDTO> repository = new HashMap<>();

    private final Map<String, Set<String>> departments = new HashMap<>();

    private final Map<String, Set<String>> currencies = new HashMap<>();

    public void addOrUpdateBooking(String bookingId, BookingDTO bookingDTO) {
        repository.put(bookingId, bookingDTO);
        addToCache(departments, bookingDTO.getDepartment(), bookingId);
        addToCache(currencies, bookingDTO.getCurrency().name(), bookingId + SPLIT + bookingDTO.getPrice());
    }

    public BookingDTO getBooking(String bookingId) {
        return repository.get(bookingId);
    }

    public Double getSum(Currency currency) {
        return Optional.ofNullable(currencies.get(currency.name()))
                .orElseThrow(() -> new MissingDataException("No prices found for currency: " + currency.name()))
                .stream()
                .map(idCurrency -> Double.valueOf(idCurrency.split(SPLIT)[1]))
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public Set<String> getDepartmentBookings(String department) {
        return departments.get(department);
    }

    public Set<String> getBookingCurrencies() {
        return currencies.keySet();
    }

    public void removeBooking(String bookingId) {
        BookingDTO bookingDTO = repository.get(bookingId);
        repository.remove(bookingId);
        removeFromCache(departments, bookingDTO.getDepartment(), bookingId);
        removeFromCache(currencies, bookingDTO.getCurrency().name(), bookingId + ":" + bookingDTO.getPrice());
    }

    private <T> void removeFromCache(Map<String, Set<T>> cache, String id, T value) {
        cache.get(id).remove(value);
    }

    private <T> void addToCache(Map<String, Set<T>> cache, String id, T value) {
        Set<T> cachedList = cache.getOrDefault(id, new HashSet<>());
        cachedList.add(value);
        cache.put(id, cachedList);
    }
}
