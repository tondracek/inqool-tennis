package cz.tondracek.inqooltennis.court.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.tondracek.inqooltennis.core.config.SecurityConfig;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.court.dto.CourtDetailDto;
import cz.tondracek.inqooltennis.court.service.CourtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cz.tondracek.inqooltennis.court.CourtSample.COURT;
import static cz.tondracek.inqooltennis.court.CourtSample.COURT_2_DTO;
import static cz.tondracek.inqooltennis.court.CourtSample.COURT_DTO;
import static cz.tondracek.inqooltennis.court.CourtSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.court.CourtSample.UPDATED_COURT_DTO;
import static cz.tondracek.inqooltennis.court.CourtSample.UPDATE_DTO;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourtController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class CourtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourtService service;

    @Test
    void createCourt() throws Exception {
        when(service.createCourt(CREATE_DTO)).thenReturn(COURT_DTO);

        mockMvc.perform(post("/api/courts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_DTO))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(COURT_DTO)));
    }

    @Test
    void createCourt_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/courts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCourt() throws Exception {
        when(service.updateCourt(COURT.getId(), UPDATE_DTO)).thenReturn(UPDATED_COURT_DTO);

        mockMvc.perform(put("/api/courts/{id}", COURT.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_DTO))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(UPDATED_COURT_DTO)));
    }

    @Test
    void updateCourt_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/courts/{id}", COURT.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCourt_shouldReturnNotFound() throws Exception {
        when(service.updateCourt(COURT.getId(), UPDATE_DTO)).thenThrow(new NotFoundException());

        mockMvc.perform(put("/api/courts/{id}", COURT.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_DTO))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCourt() throws Exception {
        mockMvc.perform(delete("/api/courts/{id}", COURT.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCourt_shouldReturnNotFound() throws Exception {
        doThrow(new NotFoundException()).when(service).softDeleteCourt(COURT.getId());

        mockMvc.perform(delete("/api/courts/{id}", COURT.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCourtById() throws Exception {
        when(service.getCourtById(COURT.getId()))
                .thenReturn(COURT_DTO);

        mockMvc.perform(get("/api/courts/{id}", COURT.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(COURT_DTO)));
    }

    @Test
    void getCourtById_shouldReturnNotFound() throws Exception {
        when(service.getCourtById(COURT.getId()))
                .thenThrow(new NotFoundException());

        mockMvc.perform(get("/api/courts/{id}", COURT.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllCourts() throws Exception {
        List<CourtDetailDto> DTOs = List.of(COURT_DTO, COURT_2_DTO);

        when(service.getAllCourts()).thenReturn(DTOs);

        mockMvc.perform(get("/api/courts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(DTOs)));
    }
}