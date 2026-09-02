package com.example.touristguideapi.controller;

import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/// Annotation til at fortælle spring at denne klasse håndtere RESTAPI'er - Dataen bliver automatisk sendt tilbage i JSON format.
@RestController

/// Annotationen styrer at alle endpoints i klassen starter med "/attractions".
@RequestMapping("/attractions")
public class TouristController {

    /// Initialisere TouristService
    private final TouristService touristService;

    /// Kontruktør
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    /// @GetMapping håndtere GET requesten, denne specifikke håndtere GET /attraction.
    @GetMapping()

    /// Fortæller hvad der skal returneres når GET metoden er håndteret (Denne returnere alle attraktioner)
    /// og sender en HTTP status 200 eller OK tilbage.
    public ResponseEntity<ArrayList<TouristAttraction>> getAllAttractions() {
        return new ResponseEntity<>(
                touristService.getAllAttractions(),
                HttpStatus.OK
        );
    }

    /// Denne håndtere så GET requesten for GET /attractions/{NAME}
    @GetMapping("{name}")


    /// @PathVariable henter navnet fra URL'en. (Eksempel localhost:8080/attractions/Tivoli returnerer Tivoli.)
    public ResponseEntity<TouristAttraction> getAttractionByName(
            @PathVariable String name) {

        /// Fortæller hvad der skal returneres når GET metoden er håndteret (Denne returnere en specifik attraktion, efter navn)
        TouristAttraction attraction =
                touristService.findAttractionByName(name);

        /// Hvis attraktionen bliver fundet (ikke er null) så returnere den attraktionen og en HTTP OK eller 200 status.
        if (attraction != null) {
            return new ResponseEntity<>(attraction, HttpStatus.OK);
        }

        /// Hvis attraktionen ikke bliver fundet (returnere null) returnerer den 404 NOT_FOUND.
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /// Håndtere POST requests og bruges til at oprette en ny attraktion.
    @PostMapping()

    /// @RequestBody håndterer JSON data fra POST requesten og laver det om til et TouristAttraction-object.
    /// (Hvis ikke man bruger denne, ville controlleren ikke automatisk vide hvad den skulle gøre med dataen fra requesten)
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction touristAttraction) {

        /// Sender den nye attraktion til touristService hvor den tilføjer den nye attraktion
        touristService.addAttraction(touristAttraction);

        /// Når den nye attraktion er tilføjet returnerer den attraktionen og en HTTP status 201 CREATED.
        return new ResponseEntity<>(touristAttraction, HttpStatus.CREATED);
    }

    /// Håndtere PUT requests og bruges til at opdatere eksisterende attraktioner
    @PutMapping("{name}")

    /// Her bliver @PathVariable brugt til at hente NAME fra URL'en, (Hvad er det vi skal ændre)
    /// derefter bliver @RequestBody brugt til at omdanne den hentede JSON data til et TouristAttraction-object. (Hvad skal vi ændre det til)
    public ResponseEntity<TouristAttraction> updateAttraction(@PathVariable String name, @RequestBody TouristAttraction touristAttraction) {

        /// Opdaterer attraktionen via touristService
        TouristAttraction updatedAttraction = touristService.updateAttraction(name, touristAttraction);

        /// Hvis den opdaterede attraktion ikke er null, bliver den nye attraktion og HTTP status 200 OK returneret.
        if(updatedAttraction != null) {
            return new ResponseEntity<>(updatedAttraction, HttpStatus.OK);
        }

        /// Hvis den ikke bliver fundet returnerer den HTTP staus 404 NOT_FOUND.
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /// @DeleteMapping håndterer DELETE requests, som bruges til at slette en turist attraktion.
    @DeleteMapping("{name}")

    /// Metode til at slette attraktioner med, bruger @PathVariable til at fetche navnet fra URL'en.
    public ResponseEntity<TouristAttraction> deleteAttraction(@PathVariable String name) {

        /// Forsøger at slette en attraktion igennem touristService
        TouristAttraction deletedAttraction = touristService.deleteAttraction(name);

        /// Hvis den ikke er null, sletter vi attraktionen og returnere HTTP STATUS 200 OK
        if(deletedAttraction != null) {
            return new ResponseEntity<>(deletedAttraction, HttpStatus.OK);
        }

        /// Hvis den er null, returnere vi HTTP STATUS 404 NOT_FOUND.
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}