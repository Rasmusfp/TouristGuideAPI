package com.example.touristguideapi.service;

import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TouristService {

    private final TouristRepository repository;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public ArrayList<TouristAttraction> getAllAttractions() {
        return repository.getAllAttractions();
    }

    public TouristAttraction findAttractionByName(String name) {
        return repository.findAttractionByName(name);
    }

    public void addAttraction(TouristAttraction touristAttraction) {
        repository.addAttraction(touristAttraction);
    }

    public TouristAttraction updateAttraction(String name, TouristAttraction updatedAttraction) {
        return repository.updateAttraction(name, updatedAttraction);
    }

    public TouristAttraction deleteAttraction(String name) {
        return repository.deleteAttraction(name);
    }
}