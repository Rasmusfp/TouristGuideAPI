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
import static org.mockito.ArgumentMatchers.any;
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

    /// Test der tjekker at GET /attractions returnerer view'et "attractionList"
    /// og lægger alle attraktioner i modellen.
    @Test
    void getAllAttractions_returnsAttractionListViewWithAllAttractions() throws Exception {

        /// Opretter en TouristAttraction der bruges som "falsk" svar fra servicen.
        TouristAttraction attraction = new TouristAttraction(1, "Tivoli",
                "Tivoli med mad, drikke og masser af sjov med vores forlystelser!",
                "København", 1620, List.of(AMUSEMENT, CHILD_FRIENDLY));

        /// Fortæller mock'en at når getAllAttractions() bliver kaldt,
        /// skal den returnere en liste med attraktionen ovenfor.
        when(touristService.getAllAttractions()).thenReturn(List.of(attraction));

        /// Simulerer en GET request til /attractions.
        mockMvc.perform(get("/attractions"))

                /// Tjekker at HTTP status er 200 OK.
                .andExpect(status().isOk())

                /// Tjekker at controlleren returnerer view'et "attractionList".
                .andExpect(view().name("attractionList"))

                /// Tjekker at modellen indeholder listen "attractions" med den rigtige attraktion.
                .andExpect(model().attribute("attractions", List.of(attraction)));

        /// Bekræfter at controlleren kaldte getAllAttractions() på touristService.
        verify(touristService).getAllAttractions();
    }

    /// Test der tjekker at GET /attractions/name/{name} returnerer view'et "attractionList"
    /// og lægger de fundne attraktioner i modellen, så Thymeleaf kan vise dem.
    @Test
    void getAttractionByName_returnsAttractionListViewWithMatchingAttractions() throws Exception {

        /// Opretter en TouristAttraction der bruges som "falsk" svar fra servicen.
        TouristAttraction attraction = new TouristAttraction(1, "Tivoli",
                "Tivoli med mad, drikke og masser af sjov med vores forlystelser!",
                "København", 1620, List.of(AMUSEMENT, CHILD_FRIENDLY));

        /// Fortæller mock'en at når getAttractionsByName("Tivoli") bliver kaldt,
        /// skal den returnere en liste med attraktionen ovenfor (i stedet for at bruge den rigtige service).
        when(touristService.getAttractionsByName("Tivoli")).thenReturn(List.of(attraction));

        /// Simulerer en GET request til /attractions/name/Tivoli.
        /// Bemærk: URL'en skal indeholde "/name/", ellers rammer den "/{id}" og giver 400,
        /// fordi "Tivoli" ikke kan laves om til et heltal.
        mockMvc.perform(get("/attractions/name/{name}", "Tivoli"))

                /// Tjekker at HTTP status er 200 OK.
                .andExpect(status().isOk())

                /// Tjekker at controlleren returnerer view'et "attractionList" (HTML-siden).
                /// Controlleren er en @Controller og returnerer altså ikke JSON.
                .andExpect(view().name("attractionList"))

                /// Tjekker at modellen indeholder en attribut der hedder "attractions".
                .andExpect(model().attributeExists("attractions"))

                /// Tjekker at "attractions" i modellen er præcis den liste, som mock'en returnerede.
                .andExpect(model().attribute("attractions", List.of(attraction)));

        /// Bekræfter at controlleren rent faktisk kaldte getAttractionsByName("Tivoli") på touristService.
        verify(touristService).getAttractionsByName("Tivoli");
    }

    /// Test der tjekker at GET /attractions/add viser formularen til at oprette en attraktion.
    @Test
    void addAttraction_showsAddAttractionForm() throws Exception {

        /// Simulerer en GET request til /attractions/add.
        mockMvc.perform(get("/attractions/add"))

                /// Tjekker at HTTP status er 200 OK.
                .andExpect(status().isOk())

                /// Tjekker at controlleren returnerer view'et "addAttraction" (formularen).
                .andExpect(view().name("addAttraction"))

                /// Tjekker at modellen indeholder et tomt "attraction"-objekt til formularen.
                .andExpect(model().attributeExists("attraction"))

                /// Tjekker at modellen indeholder "categories", så man kan vælge kategorier i formularen.
                .andExpect(model().attributeExists("categories"));
    }

    /// Test der tjekker at POST /attractions/save gemmer attraktionen
    /// og sender brugeren videre til /attractions (redirect).
    @Test
    void saveAttraction_savesAttractionAndRedirectsToAttractionList() throws Exception {

        /// Simulerer en POST request med formulardata, som om brugeren havde udfyldt formularen.
        mockMvc.perform(post("/attractions/save")
                        .param("name", "Tivoli")
                        .param("description", "Forlystelsespark i København"))

                /// Tjekker at HTTP status er 302 (redirect).
                .andExpect(status().is3xxRedirection())

                /// Tjekker at brugeren bliver sendt videre til /attractions.
                .andExpect(redirectedUrl("/attractions"));

        /// Bekræfter at controlleren kaldte addAttraction(...) på touristService
        /// med et TouristAttraction-objekt (any() betyder at vi ikke tjekker præcis hvilket).
        verify(touristService).addAttraction(any(TouristAttraction.class));
    }
}






/* package com.example.touristguideapi.controller;

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
        TouristAttraction attraction = new TouristAttraction(1, "Tivoli", "Tivoli med mad, drikke og masser af sjov med vores forlystelser!", "København", 1620,
                List.of(AMUSEMENT, CHILD_FRIENDLY));;

        /// Fortæller mock'en at når findAttractionByName("Tivoli") bliver kaldt, skal den returnere attraction-objektet ovenfor.
        when(touristService.getAttractionsByName("Tivoli")).thenReturn(List.of(attraction));

        /// Simulerer en GET request til /attractions/Tivoli.
        mockMvc.perform(get("/attractions/{name}", "Tivoli"))

                /// Tjekker at HTTP status er 200 OK.
                .andExpect(status().isOk())

                /// Tjekker at JSON-svarets "name"-felt har værdien "Tivoli".
                .andExpect(jsonPath("$.name").value("Tivoli"));

        /// Bekræfter at controlleren rent faktisk kaldte findAttractionByName("Tivoli") på touristService.
        verify(touristService).getAttractionsByName("Tivoli");
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

 */