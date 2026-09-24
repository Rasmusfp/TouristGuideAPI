package com.example.touristguideapi.controller;

import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.service.TouristService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;

import static com.example.touristguideapi.model.Category.AMUSEMENT;
import static com.example.touristguideapi.model.Category.CHILD_FRIENDLY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/// Annotation der fortæller Spring at kun web-laget for TouristController skal startes op til denne test,
/// i stedet for hele applikationen. Gør testen hurtigere.
@WebMvcTest(TouristController.class)
class TouristControllerTest {

    /// MockMvc bliver injected af Spring og bruges til at simulere HTTP requests (GET, POST osv.)
    /// uden at der rent faktisk skal startes en server op.
    @Autowired
    private MockMvc mockMvc;

    /// MockitoBean erstatter den rigtige TouristService med en simuleret (mocked) version,
    /// så testen ikke er afhængig af den rigtige implementering.
    @MockitoBean
    private TouristService touristService;

    /// Test der tjekker at GET /attractions returnerer den rigtige side og data.
    @Test
    void getAllAttractions() throws Exception {

        /// Simulerer en GET request til /attractions.
        mockMvc.perform(get("/attractions"))

                /// Tjekker at HTTP status er 200 OK.
                .andExpect(status().isOk())

                /// Tjekker at controlleren returnerer view'et "attractionList".
                .andExpect(view().name("attractionList"))

                /// Tjekker at modellen indeholder et attribut ved navn "attractions".
                .andExpect(model().attributeExists("attractions"));

        /// Bekræfter at controlleren rent faktisk kaldte getAllAttractions() på touristService.
        verify(touristService).getAllAttractions();

    }

    /// Test der tjekker at GET /attractions/{name} returnerer den rigtige attraktion som JSON.
    @Test
    void getAttractionByName() throws Exception {

        /// Opretter et TouristAttraction-object der bruges som "falsk" svar fra servicen.
        TouristAttraction attraction = new TouristAttraction("Tivoli", "Tivoli med mad, drikke og masser af sjov med vores forlystelser!", "København",
                List.of(AMUSEMENT, CHILD_FRIENDLY));;

        /// Fortæller mock'en at når findAttractionByName("Tivoli") bliver kaldt, skal den returnere attraction-objektet ovenfor.
        when(touristService.findAttractionByName("Tivoli")).thenReturn(attraction);

        /// Simulerer en GET request til /attractions/Tivoli.
        mockMvc.perform(get("/attractions/{name}", "Tivoli"))

                /// Tjekker at HTTP status er 200 OK.
                .andExpect(status().isOk())

                /// Tjekker at JSON-svarets "name"-felt har værdien "Tivoli".
                .andExpect(jsonPath("$.name").value("Tivoli"));

        /// Bekræfter at controlleren rent faktisk kaldte findAttractionByName("Tivoli") på touristService.
        verify(touristService).findAttractionByName("Tivoli");
    }

    /// Test der tjekker at POST /attractions opretter en ny attraktion korrekt.
    @Test
    void addAttraction() throws Exception {

        /// Text block der indeholder JSON-dataen, som skal sendes med i POST requesten.
        String requestBody = """
            {
                "name": "Brændesgårdshaven",
                "description": "Bornholms fineste forlystelsespark, og der er også dyr!",
                "location": "Bornholm",
                "tags": ["AMUSEMENT", "CHILD_FRIENDLY"]
            }
            """;

        /// Simulerer en POST request til /attractions med JSON-bodyen ovenfor.
        mockMvc.perform(post("/attractions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))

                /// Tjekker at HTTP status er 201 CREATED.
                .andExpect(status().isCreated());

        /// ArgumentCaptor bruges til at "fange" det TouristAttraction-object, som controlleren sendte videre til touristService.
        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);

        /// Bekræfter at addAttraction() blev kaldt på touristService, og fanger argumentet der blev sendt med.
        verify(touristService).addAttraction(captor.capture());

        /// Henter det fangede object, så vi kan tjekke dets indhold.
        TouristAttraction captured = captor.getValue();

        /// Tjekker at det oprettede object har det rigtige navn.
        assertEquals("Brændesgårdshaven", captured.getName());

        /// Tjekker at det oprettede object har den rigtige lokation.
        assertEquals("Bornholm", captured.getLocation());
    }
}