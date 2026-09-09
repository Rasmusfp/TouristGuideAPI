package com.example.touristguideapi.model;

import java.util.List;

public class TouristAttraction {
    private String name;
    private String description;
    private String location;
    private List<Category> category;


    public TouristAttraction(String name, String description, String location, List<Category> category){
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
    }

    public  TouristAttraction(){

    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getLocation() {return location;}

    public List<Category> getCategory() {return category;}

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setLocation() {this.location = location;}

    @Override
    public String toString(){
        return "Attraktion: " + name + "\n" +
                "Beskrivelse: " + description;
    }
}