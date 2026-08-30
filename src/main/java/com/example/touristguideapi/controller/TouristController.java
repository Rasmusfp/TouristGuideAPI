package com.example.touristguideapi.controller;

import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping()
    public ResponseEntity<ArrayList<TouristAttraction>> getAllAttractions() {
        return new ResponseEntity<>(
                touristService.getAllAttractions(),
                HttpStatus.OK
        );
    }

    @GetMapping("{name}")
    public ResponseEntity<TouristAttraction> getAttractionByName(
            @PathVariable String name) {

        TouristAttraction attraction =
                touristService.findAttractionByName(name);

        if (attraction != null) {
            return new ResponseEntity<>(attraction, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction touristAttraction) {

        touristService.addAttraction(touristAttraction);

        return new ResponseEntity<>(touristAttraction, HttpStatus.CREATED);
    }

    @PutMapping("{name}")
    public ResponseEntity<TouristAttraction> updateAttraction(@PathVariable String name, @RequestBody TouristAttraction touristAttraction) {

        TouristAttraction updatedAttraction = touristService.updateAttraction(name, touristAttraction);

        if(updatedAttraction != null) {
            return new ResponseEntity<>(updatedAttraction, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{name}")
    public ResponseEntity<TouristAttraction> deleteAttraction(@PathVariable String name) {

        TouristAttraction deletedAttraction = touristService.deleteAttraction(name);

        if(deletedAttraction != null) {
            return new ResponseEntity<>(deletedAttraction, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}