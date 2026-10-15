package com.example.touristguideapi.service;

import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.repository.TouristRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /// Metode til at returnere en specifik attraktion ved hjælp af id
    public TouristAttraction getAttractionById(int id) {
        try {
            return repository.getAttractionById(id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<TouristAttraction> getAttractionsByName(String name) {
            return repository.getAttractionsByName(name);
    }

    /// Transactional gemmer attraktion + alle tags som en samlet handling
    /// dvs. fejler et af SQL-kaldene, rulles alt tilbage.
    @Transactional
    public void addAttraction(TouristAttraction attraction) {
        repository.addAttraction(attraction);
    }

    /// Opdaterer attraktionen, sletter de gamle tags og gemmer de nye.
    /// Uden transaktion kunne de gamle tags være slettet, uden at de nye blev gemt.
    @Transactional
    public void updateAttraction(TouristAttraction attraction) {
        repository.updateAttraction(attraction);
    }

    /// Metode til at fjerne attraktion
    public boolean deleteAttraction(int id) {
        return repository.deleteAttraction(id);
    }
}