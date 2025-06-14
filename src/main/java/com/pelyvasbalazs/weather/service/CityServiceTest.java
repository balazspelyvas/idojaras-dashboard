// src/test/java/com/pelyvasbalazs/weather/service/CityServiceTest.java
package com.pelyvasbalazs.weather.service;

import com.pelyvasbalazs.weather.model.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CityServiceTest {

  @Autowired
  CityService cityService;

  @Test
  void testCreateAndList() {
    City budapest = cityService.create("Budapest");
    City sopron = cityService.create("Sopron");

    List<City> all = cityService.listAll();
    assertThat(all)
      .extracting(City::getName)
      .containsExactlyInAnyOrder("Budapest","Sopron");
  }
}
