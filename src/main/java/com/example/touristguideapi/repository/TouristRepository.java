package com.example.touristguideapi.repository;

import com.example.touristguideapi.model.Category;
import com.example.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.example.touristguideapi.model.Category.*;

/// Annotation for at lade IDEA vide at denne klasse er et repository
@Repository
public class TouristRepository {

    /// Initialisere en ArrayListe der tager imod TouristAttraction objekter.
    private final List<TouristAttraction> touristAttractions =
            new ArrayList<>(List.of(

                    new TouristAttraction("Tivoli", "Tivoli med mad, drikke og masser af sjov med vores forlystelser!", "København",
                            List.of(AMUSEMENT, CHILD_FRIENDLY)),

                    new TouristAttraction("SMK", "Museum for kunst", "København",
                            List.of(ART, MUSEUM)),

                    new TouristAttraction("Odense Zoo", "Europas bedst zoo", "Odense",
                            List.of(CHILD_FRIENDLY)),

                    new TouristAttraction("Dyrehaven", "Naturparken", "København",
                            List.of(NATURE, CHILD_FRIENDLY, FREE)),

                    new TouristAttraction("Brændesgårdshaven", "Bornholms fineste forlystelsespark, og der er også dyr!", "Bornholm",
                            List.of(AMUSEMENT, CHILD_FRIENDLY))
                    ));

    /// Konstruktør
    public TouristRepository() {
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
    public List<TouristAttraction> getAllAttractions() {
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