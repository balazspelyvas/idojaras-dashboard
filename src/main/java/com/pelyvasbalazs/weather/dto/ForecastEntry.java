package com.pelyvasbalazs.weather.dto;

import java.time.LocalDateTime;

public record ForecastEntry(
    LocalDateTime dateTime,
    double temperature,
    String description
) {}
