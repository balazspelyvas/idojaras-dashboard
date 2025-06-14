package com.pelyvasbalazs.weather.service;

import com.pelyvasbalazs.weather.model.City;
import com.pelyvasbalazs.weather.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository repo;

    public CityService(CityRepository repo) {
        this.repo = repo;
    }

    public City create(String name) {
        return repo.save(new City(name));
    }

    public List<City> listAll() {
        return repo.findAll();
    }
}
