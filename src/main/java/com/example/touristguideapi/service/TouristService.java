package com.example.touristguideapi.service;

import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
    public ArrayList<TouristAttraction> getAllAttractions() {
        return repository.getAllAttractions();
    }

    /// Metode til at returnere en specifik attraktion ved hjælp af navn
    public TouristAttraction findAttractionByName(String name) {
        return repository.findAttractionByName(name);
    }

    /// Metode til at tilføje attraktion
    public void addAttraction(TouristAttraction touristAttraction) {
        repository.addAttraction(touristAttraction);
    }

    /// Metode til at opdatere attraktion
    public TouristAttraction updateAttraction(String name, TouristAttraction updatedAttraction) {
        return repository.updateAttraction(name, updatedAttraction);
    }

    /// Metode til at fjerne attraktion
    public TouristAttraction deleteAttraction(String name) {
        return repository.deleteAttraction(name);
    }
}