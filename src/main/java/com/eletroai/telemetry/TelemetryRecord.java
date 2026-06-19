package com.eletroai.telemetry;

import java.time.Instant;

public record TelemetryRecord(
        String deviceId,
        Instant timestamp,
        double currentWatts,
        double voltage,
        double totalKwh
) {
}