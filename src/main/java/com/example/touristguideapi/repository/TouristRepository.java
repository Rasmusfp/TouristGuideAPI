package com.example.touristguideapi.repository;

import com.example.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class TouristRepository {

    private ArrayList<TouristAttraction> touristAttractions;

    public TouristRepository() {
        this.touristAttractions = new ArrayList<>();

        touristAttractions.add(
                new TouristAttraction(
                        "Tivoli",
                        "Tivoli med mad, drikke og masser af sjov med vores forlystelser!"
                )
        );

        touristAttractions.add(
                new TouristAttraction(
                        "Rundetårn",
                        "Så tårn! Much Round! - Har du nogensinde set et tårn så rundt?"
                )
        );
    }

    public void addAttraction(TouristAttraction touristAttraction) {

        if (touristAttraction == null) {
            throw new IllegalArgumentException(
                    "Tourist Attractions cannot be null"
            );
        }

        touristAttractions.add(touristAttraction);
    }

    public ArrayList<TouristAttraction> getAllAttractions() {
        return touristAttractions;
    }

    public TouristAttraction findAttractionByName(String name) {

        for (TouristAttraction t : touristAttractions) {

            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }

        return null;
    }
}