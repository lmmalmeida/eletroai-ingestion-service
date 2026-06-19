package com.eletroai.telemetry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Instant;

public record TelemetryRecord(
        @NotBlank(message = "O ID do dispositivo não pode estar vazio")
        String deviceId,
        Instant timestamp,
        @NotNull(message = "A leitura de energia é obrigatória")
        @PositiveOrZero(message = "Os watts não podem ser negativos")
        double currentWatts,
        @PositiveOrZero(message = "A voltagem deve ser um valor positivo")
        double voltage,
        @PositiveOrZero(message = "A soma de Kwh deve ser um valor positivo")
        double totalKwh
) {
}