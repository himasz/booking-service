package com.statista.code.challenge.service;


import com.statista.code.challenge.TestBookingData;
import com.statista.code.challenge.common.error.exception.MissingDataException;
import com.statista.code.challenge.repository.BookingRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class BookingServiceImplTest extends TestBookingData {

    /*
     *
     * Please note that most of the case were tested in BookingRepositoryTest
     * This class should be used to test thrown exception
     *
     * */

    BookingService service = new BookingServiceImpl(mock(EmailService.class), new BookingRepository());

    @Test
    void test_getBooking_noFoundBookingThrowsInvalidParamException() {
        assertThrows(MissingDataException.class, () -> service.getBooking(ID_1));
    }

    @Test
    void test_getDepartmentBookingIds_noFoundDepartmentThrowsInvalidParamException() {
        assertThrows(MissingDataException.class, () -> service.getDepartmentBookingIds(DEPARTMENT_1));
    }

    @Test
    void test_getCurrencySum_noFoundCurrencyThrowsInvalidParamException() {
        assertThrows(MissingDataException.class, () -> service.getCurrencySum(EUR.name()));
    }

}