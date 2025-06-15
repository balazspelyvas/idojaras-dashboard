package com.pelyvasbalazs.weather.controller;

import com.pelyvasbalazs.weather.dto.WeatherResponse;
import com.pelyvasbalazs.weather.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/forecast")
    public String forecast(String city, Model model) {
        // egyszerűsítésként blokkoljuk a Mono-t
        WeatherResponse response = weatherService.getForecast(city).block();
        model.addAttribute("weather", response);
        return "forecast";
    }
    
    @GetMapping("/forecast")
    public String forecast(@RequestParam String city, Model model) {
      try {
        WeatherResponse resp = weatherService.getForecast(city).block();
        model.addAttribute("weather", resp);
      } catch (Exception ex) {
        model.addAttribute("error", "Nem sikerült lekérdezni: " + ex.getMessage());
      }
      return "forecast";
    }
}
