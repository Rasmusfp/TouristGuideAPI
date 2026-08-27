package com.example.touristguideapi.repository;

import com.example.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private ArrayList<TouristAttraction> touristAttractions;

///Konstruktør til vores TouristRepository.
public TouristRepository() {
    this.touristAttractions = new ArrayList<>();
}

///Hardcoder 2 objecter ind i arraylisten.
touristAttractions.add(new TouristAttraction("Tivoli", "Tivoli med mad, drikke og masser af sjov med vores forlystelser!"));
touristAttractions.add(new TouristAttraction("Rundetårn", "So tårn! Much Round! - Har du nogensinde set et tårn så rundt?"));

///Metode til at tilføje en tourist attraktion.
public void addAttraction(TouristAttraction touristAttraction){
    if (touristAttraction == null) {
        throw new IllegalArgumentException("Tourist Attractions cannot be null");
    }
    touristAttractions.add(touristAttraction);
}


}
