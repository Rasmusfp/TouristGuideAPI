package com.example.touristguideapi.controller;

import com.example.touristguideapi.model.Category;
import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

/// Annotation til at fortælle spring at denne klasse håndtere HTTP requests.
@Controller

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
    public String getAllAttractions(Model model) {
        model.addAttribute("attractions", touristService.getAllAttractions());
        return "attractionList";
    }

    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {

        TouristAttraction attraction = touristService.findAttractionByName(name);

        if(attraction != null) {
            model.addAttribute("attraction", attraction);
            model.addAttribute("categories", Category.values());
            return "updateAttraction";
        }

        return "redirect:/attractions";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction touristAttraction) {

        touristService.updateAttraction(touristAttraction.getName(), touristAttraction);

        return "redirect:/attractions";
    }

    @GetMapping("/add")
    public String addAttraction(Model model) {

        model.addAttribute("attraction", new TouristAttraction());
        model.addAttribute("categories", Category.values());

            return "addAttraction";
    }

    @PostMapping("/{name}/delete")
    public String deleteAttraction(@PathVariable String name) {

        touristService.deleteAttraction(name);
        return "redirect:/attractions";
}

    @GetMapping("/{name}/tags")
    public String getAttractionTags(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.findAttractionByName(name);

        if (attraction != null) {
            model.addAttribute("attraction", attraction);
            return "tags";
        }

        throw new NoSuchElementException("Attraction not found: " + name);
    }
}