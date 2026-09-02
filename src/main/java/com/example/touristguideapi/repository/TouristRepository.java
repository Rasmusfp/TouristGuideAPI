package com.example.touristguideapi.repository;

import com.example.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/// Annotation for at lade IDEA vide at denne klasse er et repository
@Repository
public class TouristRepository {

    /// Initialisere en ArrayListe der tager imod TouristAttraction objekter.
    private ArrayList<TouristAttraction> touristAttractions;

    /// Konstruktør
    public TouristRepository() {
        this.touristAttractions = new ArrayList<>();

        /// Hardcorded atraktion
        touristAttractions.add(
                new TouristAttraction(
                        "Tivoli",
                        "Tivoli med mad, drikke og masser af sjov med vores forlystelser!"
                )
        );

        /// Hardcorded atraktion
        touristAttractions.add(
                new TouristAttraction(
                        "Rundetårn",
                        "Så tårn! Much Round! - Har du nogensinde set et tårn så rundt?"
                )
        );
    }

    /// Metode til at tilføje attraktion
    public void addAttraction(TouristAttraction touristAttraction) {

        /// If statement til hvis det TouristAttraction object er null, kaster den en IllegalArguementException og en fejlbesked.
        if (touristAttraction == null) {
            throw new IllegalArgumentException(
                    "Tourist Attractions cannot be null"
            );
        }

        touristAttractions.add(touristAttraction);
    }

    /// Metode til at returnere alle attraktioner i en ArrayListe
    public ArrayList<TouristAttraction> getAllAttractions() {
        return touristAttractions;
    }

    /// Metode til at returnere et specifikt TouristAttraction-object fra en ArrayListe
    public TouristAttraction findAttractionByName(String name) {

        for (TouristAttraction t : touristAttractions) {

            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }

        return null;
    }

    /// Metode til at opdatere en attraktion
    public TouristAttraction updateAttraction(String name, TouristAttraction updatedAttraction) {

        TouristAttraction existingAttraction = findAttractionByName(name);

        /// Hvis existingAttraction ikke er null skal den opdatere og returnere existingAttraction
        if(existingAttraction != null) {
            existingAttraction.setName(updatedAttraction.getName());
            existingAttraction.setDescription(updatedAttraction.getDescription());

            return existingAttraction;
        }

        return null;
    }

    /// Metode til at slette en attraktion
    public TouristAttraction deleteAttraction(String name) {

        TouristAttraction attraction = findAttractionByName(name);

        /// Hvis attraktionen ikke er null skal den slette attraktionen
        if(attraction != null) {
            touristAttractions.remove(attraction);
        }

        return attraction;
    }
}