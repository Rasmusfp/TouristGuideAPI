package com.example.touristguideapi.controller;

import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("attraction")

public class TouristController {

    private TouristService touristService;

    public class TouristController(TouristService touristService) {
    this.touristService = touristService;
    }

    @GetMapping()
    public ResponseEntity<ArrayList<TouristAttraction>> getAllAttractions() {

        return new ResponseEntity<>(touristService.getAllAttractions(), HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<ArrayList<TouristAttraction>> getAttractionByName(@PathVariable String name) {
        TouristAttraction attraction = touristService.findMatchingAttractionByName(name);

        if (attraction != null) {
            return new ResponseEntity<>(attraction, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}