package com.pelyvasbalazs.weather.service;

import com.pelyvasbalazs.weather.dto.ForecastEntry;
import com.pelyvasbalazs.weather.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final WebClient webClient;
    private final String apiKey;

    public WeatherService(
            @Value("${weather.api.url}") String apiUrl,
            @Value("${weather.api.key}") String apiKey
    ) {
        this.webClient = WebClient.builder()
                                  .baseUrl(apiUrl)
                                  .build();
        this.apiKey = apiKey;
    }

    public Mono<WeatherResponse> getForecast(String city) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", city)
                        .queryParam("units", "metric")
                        .queryParam("appid", apiKey)
                        .build()
                )
                .retrieve()
                .bodyToMono(Map.class)
                .map(this::toWeatherResponse);
    }

    @SuppressWarnings("unchecked")
    private WeatherResponse toWeatherResponse(Map<String, Object> json) {
        Map<String,Object> city = (Map<String, Object>) json.get("city");
        List<Map<String,Object>> list = (List<Map<String, Object>>) json.get("list");

        List<ForecastEntry> entries = list.stream()
            .map(item -> {
                long ts = ((Number)item.get("dt")).longValue();
                LocalDateTime dt = LocalDateTime.ofInstant(Instant.ofEpochSecond(ts), ZoneId.systemDefault());

                Map<String,Object> main = (Map<String,Object>) item.get("main");
                double temp = ((Number)main.get("temp")).doubleValue();

                List<Map<String,Object>> weatherArr = (List<Map<String,Object>>) item.get("weather");
                String desc = weatherArr.get(0).get("description").toString();

                return new ForecastEntry(dt, temp, desc);
            })
            .collect(Collectors.toList());

        return new WeatherResponse(city.get("name").toString(), entries);
    }
}
