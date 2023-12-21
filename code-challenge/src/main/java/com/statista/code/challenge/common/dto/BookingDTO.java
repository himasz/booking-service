package com.statista.code.challenge.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statista.code.challenge.common.enums.Currency;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BookingDTO {
    @NotNull
    private String description;
    @NotNull
    private double price;
    @NotNull
    private Currency currency;
    @NotNull
    @JsonProperty("subscription_start_date")
    private long subscriptionStartDate;
    @NotNull
    private String email;
    @NotNull
    private String department;
}
