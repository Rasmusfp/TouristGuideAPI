package com.example.touristguideapi.controller;

import com.example.touristguideapi.model.Category;
import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    /// GetMapping håndtere GET requesten, denne specifikke håndtere GET /attraction.
    @GetMapping()

    /// Fortæller hvad der skal returneres når GET metoden er håndteret (Denne returnere alle attraktioner)
    /// og sender en HTTP status 200 eller OK tilbage.
    public String getAllAttractions(Model model) {
        model.addAttribute("attractions", touristService.getAllAttractions());
        return "attractionList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable int id, Model model) {
        TouristAttraction attraction = touristService.getAttractionById(id);

        if (attraction != null) {
            model.addAttribute ("attraction", attraction);
            return "attraction";
        }
        return "redirect:/attractions";
    }

    @GetMapping("/name/{name}")
    public String findByName(@PathVariable String name) {
        TouristAttraction attraction = touristService.getAttractionByName(name);

        if (attraction != null) {
            return "redirect:/attractions/" + attraction.getId();
        }

        return "redirect:/attractions";
    }


    @GetMapping("/{id}/edit")
    public String updateAttraction(@PathVariable int id, Model model) {

        TouristAttraction attraction = touristService.getAttractionById(id);

        if(attraction != null) {
            model.addAttribute("attraction", attraction);
            model.addAttribute("categories", Category.values());
            return "updateAttraction";
        }

        return "redirect:/attractions";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction touristAttraction) {

        touristService.updateAttraction(touristAttraction);

        return "redirect:/attractions";
    }

    @GetMapping("/add")
    public String addAttraction(Model model) {

        model.addAttribute("attraction", new TouristAttraction());
        model.addAttribute("categories", Category.values());

            return "addAttraction";
    }

    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction touristAttraction) {
        touristService.addAttraction(touristAttraction);
        return "redirect:/attractions";
    }

    @PostMapping("/{id}/delete")
    public String deleteAttraction(@PathVariable int id) {

        touristService.deleteAttraction(id);
        return "redirect:/attractions";
}

    @GetMapping("/{id}/tags")
    public String getAttractionTags(@PathVariable int id, Model model) {
        TouristAttraction attraction = touristService.getAttractionById(id);

        if (attraction != null) {
            model.addAttribute("attraction", attraction);
            return "tags";
        }

        throw new NoSuchElementException("Attraction not found: " + id);
    }


}