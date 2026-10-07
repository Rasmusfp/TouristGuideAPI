package com.example.touristguideapi.service;

import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.repository.TouristRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/// Annotation for at lade IDEA vide at denne klasse er en service klasse
@Service
public class TouristService {

    /// Initialisere TouristRepository klassen som final
    private final TouristRepository repository;

    /// Konstruktør til TouristService
    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    /// Metode til at returnere alle attraktioner ved hjælp af ArrayList
    public List<TouristAttraction> getAllAttractions() {
        return repository.getAllAttractions();
    }

    /// Metode til at returnere en specifik attraktion ved hjælp af navn
    public TouristAttraction getAttractionById(int id) {
        try {
            return repository.getAttractionById(id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public TouristAttraction getAttractionByName(String name) {
        try {
            return repository.getAttractionByName(name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /// Metode til at tilføje attraktion
    public void addAttraction(TouristAttraction attraction) {
        repository.addAttraction(attraction);
    }

    /// Metode til at opdatere attraktion
    public void updateAttraction(TouristAttraction attraction) {
        repository.updateAttraction(attraction);
    }

    /// Metode til at fjerne attraktion
    public Boolean deleteAttraction(int id) {
        return repository.deleteAttraction(id);
    }
}