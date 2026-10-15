package com.example.touristguideapi.repository;

import com.example.touristguideapi.model.TouristAttraction;

import java.util.List;

public interface TouristRepository {

    public List<TouristAttraction> getAllAttractions();

    public TouristAttraction getAttractionById(int id);

    public List<TouristAttraction> getAttractionsByName(String name);

    public void addAttraction(TouristAttraction touristattraction);

    public void updateAttraction(TouristAttraction touristattraction);

    public boolean deleteAttraction(int id);
}