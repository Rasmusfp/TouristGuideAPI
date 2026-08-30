package com.example.touristguideapi.model;

public class TouristAttraction {
    private String name;
    private String description;


    public TouristAttraction(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName(String name){
        return name;
    }

    public String getDescription(String description){
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }
}