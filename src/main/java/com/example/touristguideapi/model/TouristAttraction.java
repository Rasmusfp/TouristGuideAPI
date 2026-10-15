package com.example.touristguideapi.model;

import java.util.List;

public class TouristAttraction {
    private int id;
    private String name;
    private String description;
    private int zipCode;
    private String location;
    private List<Category> category;


    /// Konstruktører

    public TouristAttraction(int id, String name, String description, String location, int zipCode, List<Category> category){
        this.id = id;
        this.name = name;
        this.description = description;
        this.zipCode = zipCode;
        this.location = location;
        this.category = category;
    }


    public TouristAttraction(String name, String description, int zipCode, String location, List<Category> category){
        this.name = name;
        this.description = description;
        this.zipCode = zipCode;
        this.location = location;
        this.category = category;
    }

    public  TouristAttraction(){

    }


    /// Getters
    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getZipCode() { return zipCode; }

    public String getLocation() {return location;}

    public List<Category> getCategory() {return category;}



    /// Setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setZipCode(int zipCode) { this.zipCode = zipCode; }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCategory(List<Category> category){
        this.category = category;
    }



    /// toString metode.
    @Override
    public String toString(){
        return "Attraktion: " + name + "\n" +
                "Beskrivelse: " + description;
    }
}