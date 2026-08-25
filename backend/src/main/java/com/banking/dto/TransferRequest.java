package com.banking.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TransferRequest(@NotBlank String recipientAccount, @NotNull @DecimalMin ("0.01") BigDecimal amount,
                              String description) {
}
