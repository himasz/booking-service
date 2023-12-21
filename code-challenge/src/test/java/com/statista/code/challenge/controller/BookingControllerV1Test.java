package com.statista.code.challenge.controller;


import com.statista.code.challenge.TestBookingData;
import com.statista.code.challenge.common.dto.BookingDTO;
import com.statista.code.challenge.common.dto.CurrencySumDTO;
import com.statista.code.challenge.common.error.ControllerError;
import com.statista.code.challenge.service.EmailService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.statista.code.challenge.common.error.ErrorCode.MISSING_BODY_FIELD;
import static com.statista.code.challenge.common.error.ErrorCode.MISSING_DATA_VALUE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingControllerV1Test extends TestBookingData {
    @MockBean
    private EmailService emailService;
    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URI = "/bookingservice/v1";
    private final String BOOKING_BASE_URL = BASE_URI + "/bookings/";
    private final String CURRENCY_URL = BASE_URI + "/sum/";
    private final String CURRENCIES_URL = BOOKING_BASE_URL + "currencies";
    private final String DEPARTMENT_URL = BOOKING_BASE_URL + "department/";

    @Test
    void whenAddNewBookingAndGetItCorrectly() {
        String url = BOOKING_BASE_URL + ID_1;

        ResponseEntity<Void> putBookingResponse =
                restTemplate.exchange(url, PUT, new HttpEntity<>(BOOKING), Void.class);

        verify(emailService, times(1)).sendEmail(BOOKING.getEmail(), BOOKING.getDescription());
        assertThat(putBookingResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<BookingDTO> getBookingResponse =
                restTemplate.exchange(url, GET, null, BookingDTO.class);

        assertThat(getBookingResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        BookingDTO body = getBookingResponse.getBody();

        assertThat(body).isNotNull();
        assertThat(body.getEmail()).isEqualTo(BOOKING.getEmail());
        assertThat(body.getPrice()).isEqualTo(BOOKING.getPrice());
        assertThat(body.getCurrency()).isEqualTo(BOOKING.getCurrency());
        assertThat(body.getDepartment()).isEqualTo(BOOKING.getDepartment());
        assertThat(body.getDescription()).isEqualTo(BOOKING.getDescription());
    }

    @Test
    void whenCallingPutBookingWithoutBody() {
        String url = BOOKING_BASE_URL + ID_1;
        ResponseEntity<ControllerError> putBookingResponse =
                restTemplate.exchange(url, PUT, new HttpEntity<>(new BookingDTO()), ControllerError.class);

        ControllerError controllerError = putBookingResponse.getBody();

        assertThat(controllerError).isNotNull();
        assertThat(controllerError.getDescription()).isNotEmpty();
        assertThat(controllerError.getCode()).isEqualTo(MISSING_BODY_FIELD.getCode());
        assertThat(controllerError.getMessage()).isEqualTo(MISSING_BODY_FIELD.name());
        assertThat(putBookingResponse.getStatusCode()).isEqualTo(MISSING_BODY_FIELD.getHttpStatus());
    }

    @Test
    void testingDepartmentsAndCurrencyScenarios() {
        //given
        restTemplate.exchange(BOOKING_BASE_URL + ID_1, PUT, new HttpEntity<>(FIRST_BOOKING), Void.class);
        restTemplate.exchange(BOOKING_BASE_URL + ID_2, PUT, new HttpEntity<>(SECOND_BOOKING), Void.class);
        restTemplate.exchange(BOOKING_BASE_URL + ID_3, PUT, new HttpEntity<>(THIRD_BOOKING), Void.class);

        //when
        ResponseEntity<CurrencySumDTO> eurSumResponse =
                restTemplate.exchange(CURRENCY_URL + EUR.name(), GET, null, CurrencySumDTO.class);

        ResponseEntity<List> currenciesResponse =
                restTemplate.exchange(CURRENCIES_URL, GET, null, List.class);

        ResponseEntity<List> secondDepartmentResponse =
                restTemplate.exchange(DEPARTMENT_URL + DEPARTMENT_2, GET, null, List.class);

        //then
        assertThat(eurSumResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(eurSumResponse.getBody().getSum()).isEqualTo(FIRST_BOOKING.getPrice() + SECOND_BOOKING.getPrice());

        assertThat(currenciesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(currenciesResponse.getBody()).contains(EUR.name(), USD.name());

        assertThat(secondDepartmentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(secondDepartmentResponse.getBody()).contains(ID_2, ID_3).doesNotContain(ID_1);
    }

    @Test
    void missingDataException() {
        ResponseEntity<ControllerError> sumErrorResponse =
                restTemplate.exchange(CURRENCY_URL + "any", GET, null, ControllerError.class);

        ResponseEntity<ControllerError> departmentErrorResponse =
                restTemplate.exchange(DEPARTMENT_URL + "any", GET, null, ControllerError.class);

        ControllerError sumControllerErrorResponseBody = sumErrorResponse.getBody();

        assertThat(sumControllerErrorResponseBody).isNotNull();
        assertThat(sumControllerErrorResponseBody.getDescription()).isNotEmpty();
        assertThat(sumControllerErrorResponseBody.getCode()).isEqualTo(MISSING_DATA_VALUE.getCode());
        assertThat(sumControllerErrorResponseBody.getMessage()).isEqualTo(MISSING_DATA_VALUE.name());
        assertThat(sumErrorResponse.getStatusCode()).isEqualTo(MISSING_DATA_VALUE.getHttpStatus());

        ControllerError departmentControllerErrorResponseBody = departmentErrorResponse.getBody();

        assertThat(departmentControllerErrorResponseBody).isNotNull();
        assertThat(departmentControllerErrorResponseBody.getDescription()).isNotEmpty();
        assertThat(departmentControllerErrorResponseBody.getCode()).isEqualTo(MISSING_DATA_VALUE.getCode());
        assertThat(departmentControllerErrorResponseBody.getMessage()).isEqualTo(MISSING_DATA_VALUE.name());
        assertThat(sumErrorResponse.getStatusCode()).isEqualTo(MISSING_DATA_VALUE.getHttpStatus());
    }

}