package com.example.touristguideapi.service;

import com.example.touristguideapi.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TouristService {
    private final TouristRepository repository;

    public TouristService (TouristRepository repository) {
        this.repository = repository;
    }
    public ArrayList<TouristAttraction> getTouristAttractions() {
        return repository.getAllTouristAttraction();
    }

    public TouristAttraction findAttractionByName(String name) {
        TouristAttraction touristAttraction = repository.findAttractionByName(name);
            return new TouristAttraction(touristAttraction.getName());

        }

    }

