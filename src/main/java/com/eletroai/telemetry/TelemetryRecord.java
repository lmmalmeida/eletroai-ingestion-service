package com.eletroai.telemetry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "MetricPayload", description = "Representação das métricas de energia enviadas pelo dispositivo Shelly")
public record TelemetryRecord(
        @Schema(description = "Identificador único do dispositivo Shelly", example = "shelly-em-cozinha", required = true)
        @NotBlank(message = "O ID do dispositivo não pode estar vazio")
        String deviceId,
        Instant timestamp,
        @Schema(description = "Consumo instantâneo em Watts", example = "1420.5", required = true)
        @NotNull(message = "A leitura de energia é obrigatória")
        @PositiveOrZero(message = "Os watts não podem ser negativos")
        double currentWatts,
        @Schema(description = "Voltagem da rede no momento da leitura", example = "230.2", required = false)
        @PositiveOrZero(message = "A voltagem deve ser um valor positivo")
        double voltage,
        @Schema(description = "Soma de Kwh", example = "2000", required = false)
        @PositiveOrZero(message = "A soma de Kwh deve ser um valor positivo")
        double totalKwh
) {
}

