package com.example.touristguideapi.repository.impl;

import com.example.touristguideapi.model.Category;
import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.repository.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/// Annotation for at lade IDEA vide at denne klasse er et repository
@Repository
public class TouristRepositoryImpl implements TouristRepository {

//    /// Initialisere en ArrayListe der tager imod TouristAttraction objekter.
//    private final List<TouristAttraction> touristAttractions =
//            new ArrayList<>(List.of(
//
//                    new TouristAttraction("Tivoli", "Tivoli med mad, drikke og masser af sjov med vores forlystelser!", "København",
//                            List.of(AMUSEMENT, CHILD_FRIENDLY)),
//
//                    new TouristAttraction("SMK", "Museum for kunst", "København",
//                            List.of(ART, MUSEUM)),
//
//                    new TouristAttraction("Odense Zoo", "Europas bedst zoo", "Odense",
//                            List.of(CHILD_FRIENDLY)),
//
//                    new TouristAttraction("Dyrehaven", "Naturparken", "København",
//                            List.of(NATURE, CHILD_FRIENDLY, FREE)),
//
//                    new TouristAttraction("Brændesgårdshaven", "Bornholms fineste forlystelsespark, og der er også dyr!", "Bornholm",
//                            List.of(AMUSEMENT, CHILD_FRIENDLY))
//                    ));

    /// Konstruktør
    public TouristRepositoryImpl() {
    }

    //    /// Metode til at tilføje attraktion
//    public void addAttraction(TouristAttraction touristAttraction) {
//
//        /// If statement til hvis det TouristAttraction object er null, kaster den en IllegalArguementException og en fejlbesked.
//        if (touristAttraction == null) {
//            throw new IllegalArgumentException(
//                    "Tourist Attractions cannot be null"
//            );
//        }
//
//       touristAttractions.add(touristAttraction);
//    }
//
//    /// Metode til at returnere alle attraktioner i en ArrayListe
//    public List<TouristAttraction> getAllAttractions() {
//        return touristAttractions;
//    }
//
//    /// Metode til at returnere et specifikt TouristAttraction-object fra en ArrayListe
//    public TouristAttraction findAttractionByName(String name) {
//
//        for (TouristAttraction t : touristAttractions) {
//
//            if (t.getName().equalsIgnoreCase(name)) {
//                return t;
//            }
//        }
//        return null;
//    }
//    /// Metode til at opdatere en attraktion
//    public TouristAttraction updateAttraction(String name, TouristAttraction updatedAttraction) {
//
//        TouristAttraction existingAttraction = findAttractionByName(name);
//
//        /// Hvis existingAttraction ikke er null skal den opdatere og returnere existingAttraction
//        if(existingAttraction != null) {
//            existingAttraction.setName(updatedAttraction.getName());
//            existingAttraction.setDescription(updatedAttraction.getDescription());
//            existingAttraction.setLocation(updatedAttraction.getLocation());
//            existingAttraction.setCategory(updatedAttraction.getCategory());
//
//            return existingAttraction;
//        }
//
//        return null;
//    }
//
//    /// Metode til at slette en attraktion
//    public TouristAttraction deleteAttraction(String name) {
//
//        TouristAttraction attraction = findAttractionByName(name);
//
//        /// Hvis attraktionen ikke er null skal den slette attraktionen
//        if(attraction != null) {
//            touristAttractions.remove(attraction);
//        }
//
//        return attraction;
//    }
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /// Metode der returnere alle vores TouristAttraction objekter i en liste.
    @Override
    public List<TouristAttraction> getAllAttractions() {
        String sql = "SELECT a.id, a.name, a.description, a.zip_code, l.city AS location " +
                     "FROM touristattraction a " +
                     "JOIN location l ON a.zip_code = l.zip_code";
        RowMapper<TouristAttraction> rowMapper = new BeanPropertyRowMapper<>(TouristAttraction.class);
        List<TouristAttraction> attractions = jdbcTemplate.query(sql, rowMapper);

        for (TouristAttraction attraction : attractions) {
            //Denne linje bruges til at finde kategorierne da de er gemt i et andet table for at opretholde databasenormalisering 1,2 og 3.
            attraction.setCategory(getCategories(attraction.getId()));
        }
        return attractions;
    }

    /// Metode der returnere netop en attraction ved brug af id.
    @Override
    public TouristAttraction getAttractionById(int id) {
        String sql = "SELECT a.id, a.name, a.description, a.zip_code, l.city AS location " +
                     "FROM touristattraction a " +
                     "JOIN location l ON a.zip_code = l.zip_code " +
                     "WHERE a.id = ?";
        RowMapper<TouristAttraction> rowMapper = new BeanPropertyRowMapper<>(TouristAttraction.class);
        TouristAttraction attraction = jdbcTemplate.queryForObject(sql, rowMapper, id);
        //Denne linje bruges til at finde kategorierne da de er gemt i et andet table for at opretholde databasenormalisering 1,2 og 3.
        attraction.setCategory(getCategories(id));
        return attraction;
    }

    /// Denne er rettet til liste, da hvis vi har flere objekter med navnet "Tivoli" der evt. har forskellig lokation, returnerer den nu en liste med alle disse objekter.
    /// Hvis der kun findes en, returnere den blot en liste med 1 object i.
    @Override
    public List<TouristAttraction> getAttractionsByName(String name) {
        String sql = "SELECT a.id, a.name, a.description, a.zip_code, l.city AS location " +
                     "FROM touristattraction a " +
                     "JOIN location l ON a.zip_code = l.zip_code " +
                     "WHERE a.name = ?";
        RowMapper<TouristAttraction> rowMapper = new BeanPropertyRowMapper<>(TouristAttraction.class);
        List<TouristAttraction> attractions = jdbcTemplate.query(sql, rowMapper, name);

        for (TouristAttraction attraction : attractions) {
            //Denne linje bruges til at finde kategorierne da de er gemt i et andet table for at opretholde databasenormalisering 1,2 og 3.
            attraction.setCategory((getCategories(attraction.getId())));
        }
        return attractions;
    }


    /// Metode til at tilføje en ny attraktion
    @Override
    public void addAttraction(TouristAttraction attraction) {

        // Opretter postnummeret i location, hvis det ikke findes
        saveLocationIfMissing(attraction.getZipCode(), attraction.getLocation());

        String sql = "INSERT INTO touristattraction (name, description, zip_code) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, attraction.getName(), attraction.getDescription(), attraction.getZipCode());

        //Sørger for det nye ID bliver "den næste int i listen" - så hvis vi har 5 attraktioner, bliver det næste id altså 6.
        int newId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM touristattraction", Integer.class);
        saveCategories(newId, attraction.getCategory());
    }


    ///  Metode til at opdatere en attratkion
    @Override
    public void updateAttraction(TouristAttraction attraction) {
        //gemmer ny lokation hvis den ikke allerede findes i tabellem.
        saveLocationIfMissing(attraction.getZipCode(), attraction.getLocation());

        String sql = "UPDATE touristattraction SET name=?, description=?, zip_code=? WHERE id=?";
        jdbcTemplate.update(sql, attraction.getName(), attraction.getDescription(),
                attraction.getZipCode(), attraction.getId());

        jdbcTemplate.update("DELETE FROM attraction_category WHERE attraction_id=?", attraction.getId());
        saveCategories(attraction.getId(), attraction.getCategory());
    }

    /// Metode til at slette en attraktion ved hjælp af id
    @Override
    public boolean deleteAttraction(int id) {
        String sql = "DELETE FROM touristattraction WHERE id=?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    /// Henter alle tags for en attraktion ved at joine de to tabeller
    private List<Category> getCategories(int attractionId) {
        String sql = "SELECT c.name FROM category c " +
                     "JOIN attraction_category ac ON c.id = ac.category_id " +
                     "WHERE ac.attraction_id = ?";
        List<String> names = jdbcTemplate.queryForList(sql, String.class, attractionId);

        List<Category> categories = new ArrayList<>();
        for (String name : names) {
            categories.add(Category.valueOf(name));
        }
        return categories;
    }

    /// Gemmer tags for en attraktion i join-tabellen - en række per tag
    private void saveCategories(int attractionId, List<Category> categories) {
        if (categories == null) {
            return;
        }

        for (Category category : categories) {
            // Finder kategoriens id i category-tabellen
            int categoryId = jdbcTemplate.queryForObject("SELECT id FROM category WHERE name = ?", Integer.class, category.name());

            // Opretter koblingen mellem attraktion og kategori
            jdbcTemplate.update("INSERT INTO attraction_category (attraction_id, category_id) VALUES (?, ?)",
                    attractionId, categoryId);
        }
    }

    /// Opretter postnummeret i location-tabellen, hvis det ikke allerede findes
    private void saveLocationIfMissing(int zipCode, String city) {
        List<Integer> existing = jdbcTemplate.queryForList(
                "SELECT zip_code FROM location WHERE zip_code = ?", Integer.class, zipCode);

        if (existing.isEmpty()) {
            jdbcTemplate.update("INSERT INTO location (zip_code, city) VALUES (?, ?)", zipCode, city);
        }
    }
}