package com.statista.code.challenge.common.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControllerError {
    private int code;
    private String message;
    private String description;
}
