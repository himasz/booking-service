package com.statista.code.challenge.controller;

import com.statista.code.challenge.common.dto.BookingDTO;
import com.statista.code.challenge.common.dto.CurrencySumDTO;
import com.statista.code.challenge.common.error.ControllerError;
import com.statista.code.challenge.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bookingservice/v1")
@RequiredArgsConstructor
public class BookingControllerV1 {
    private final BookingService bookingService;

    @PutMapping("/bookings/{booking_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Create or update a booking")
    @ApiResponse(responseCode = "204", description = "Creates a new booking and sends an e-mail with the details")
    @ApiResponse(responseCode = "400", description = "Some parameters are missing or invalid",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ControllerError.class)
            ))
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public void createOrUpdateBooking(@PathVariable(value = "booking_id") @Min(1) String bookingId, @Valid @RequestBody BookingDTO bookingDTO) {
        bookingService.createOrUpdateBooking(bookingId, bookingDTO);
    }

    @GetMapping("/bookings/{booking_id}")
    @Operation(summary = "Get a booking by ID")
    @ApiResponse(responseCode = "200", description = "Returns the specified booking as JSON",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookingDTO.class)
            ))
    @ApiResponse(responseCode = "400", description = "when there is no booking with given bookingId",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ControllerError.class)
            ))
    public ResponseEntity<BookingDTO> getBooking(@PathVariable(value = "booking_id") @Min(1) String bookingId) {
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }

    @GetMapping("/bookings/department/{department}")
    @Operation(summary = "Get booking IDs for a department")
    @ApiResponse(responseCode = "200", description = "Returns booking IDs for a department",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = List.class)
            ))
    @ApiResponse(responseCode = "400", description = "when there is no booking with given department",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ControllerError.class)
            ))
    public ResponseEntity<Set<String>> getDepartmentBookingIds(@PathVariable @Min(1) String department) {
        return ResponseEntity.ok(bookingService.getDepartmentBookingIds(department));
    }

    @GetMapping("/bookings/currencies")
    @Operation(summary = "Get all booking currencies")
    @ApiResponse(responseCode = "200", description = "Returns all booking currencies",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Set.class)
            ))
    public ResponseEntity<Set<String>> getBookingCurrencies() {
        return ResponseEntity.ok(bookingService.getBookingCurrencies());
    }

    @GetMapping("/sum/{currency}")
    @Operation(summary = "Get the sum for a specific currency")
    @ApiResponse(responseCode = "200", description = "Returns the sum for a specific currency",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CurrencySumDTO.class)
            ))
    @ApiResponse(responseCode = "400", description = "when there is no booking with given currency",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ControllerError.class)
            ))
    public ResponseEntity<CurrencySumDTO> getCurrencySum(@PathVariable @Min(1) String currency) {
        return ResponseEntity.ok(bookingService.getCurrencySum(currency));
    }

    @GetMapping("/bookings/dobusiness/{booking_id}")
    @Operation(summary = "Perform business logic for a booking")
    @ApiResponse(responseCode = "200", description = "Successfully performed business logic")
    public ResponseEntity<String> doBusiness(@PathVariable(value = "booking_id") @Min(1) String bookingId) {
        return ResponseEntity.ok(bookingService.doBusiness(bookingId));
    }

}