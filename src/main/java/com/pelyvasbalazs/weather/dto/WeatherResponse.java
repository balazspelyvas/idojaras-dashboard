package com.pelyvasbalazs.weather.dto;

import java.util.List;

public record WeatherResponse(
    String city,
    List<ForecastEntry> forecastList
) {}
