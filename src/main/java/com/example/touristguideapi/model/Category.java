package com.example.touristguideapi.model;

public enum Category {
    ART ("Kunst"),
    MUSEUM ("Museum"),
    CHILD_FRIENDLY ("Børnevenlig"),
    AMUSEMENT ("Forlystelse"),
    NATURE ("Natur"),
    FREE ("Gratis");

    //Initialiserer en string til brug i konstruktøren
    private final String tag;

    //Konstruktør der tager imod string tag
    Category(String tag){
        this.tag = tag;
    }

    //Public getter, der bliver kaldt i tags.html så den returnerer strengen der passer til ENUM-værdien i stedet for at returmere ENUM-værdien
    public String getTag(){
        return tag;
    }
}
