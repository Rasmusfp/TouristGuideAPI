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

@WebMvcTest(TouristController.class)
class TouristControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TouristService touristService;

    @Test
    void getAllAttractions() throws Exception {
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionList"))
                .andExpect(model().attributeExists("attractions"));
        verify(touristService).getAllAttractions();

    }

    @Test
    void getAttractionByName() throws Exception {
        TouristAttraction attraction = new TouristAttraction("Tivoli", "Tivoli med mad, drikke og masser af sjov med vores forlystelser!", "København",
                List.of(AMUSEMENT, CHILD_FRIENDLY));;
        when(touristService.findAttractionByName("Tivoli")).thenReturn(attraction);

        mockMvc.perform(get("/attractions/{name}", "Tivoli"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tivoli"));
        verify(touristService).findAttractionByName("Tivoli");
    }

    @Test
    void addAttraction() throws Exception {
        String requestBody = """
            {
                "name": "Brændesgårdshaven",
                "description": "Bornholms fineste forlystelsespark, og der er også dyr!",
                "location": "Bornholm",
                "tags": ["AMUSEMENT", "CHILD_FRIENDLY"]
            }
            """;

        mockMvc.perform(post("/attractions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(touristService).addAttraction(captor.capture());

        TouristAttraction captured = captor.getValue();
        assertEquals("Brændesgårdshaven", captured.getName());
        assertEquals("Bornholm", captured.getLocation());
    }
}