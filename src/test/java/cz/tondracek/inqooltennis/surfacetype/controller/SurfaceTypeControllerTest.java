package cz.tondracek.inqooltennis.surfacetype.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.tondracek.inqooltennis.core.config.SecurityConfig;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.surfacetype.dto.SurfaceTypeDetailDto;
import cz.tondracek.inqooltennis.surfacetype.service.SurfaceTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_2_DTO;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.SURFACE_TYPE_DTO;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.UPDATED_SURFACE_TYPE;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.UPDATED_SURFACE_TYPE_DTO;
import static cz.tondracek.inqooltennis.surfacetype.SurfaceTypeSample.UPDATE_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SurfaceTypeController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class SurfaceTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SurfaceTypeService service;

    @Test
    void createSurfaceType() throws Exception {
        when(service.createSurfaceType(CREATE_DTO)).thenReturn(SURFACE_TYPE_DTO);

        mockMvc.perform(post("/api/surface-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SURFACE_TYPE_DTO)));
    }

    @Test
    void createSurfaceType_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/surface-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateSurfaceType() throws Exception {
        when(service.updateSurfaceType(UPDATED_SURFACE_TYPE.getId(), UPDATE_DTO)).thenReturn(UPDATED_SURFACE_TYPE_DTO);

        mockMvc.perform(put("/api/surface-types/{id}", UPDATED_SURFACE_TYPE.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(UPDATED_SURFACE_TYPE_DTO)));
    }

    @Test
    void updateSurfaceType_shouldReturnBadRequest() throws Exception {
        when(service.updateSurfaceType(UPDATED_SURFACE_TYPE.getId(), UPDATE_DTO)).thenReturn(UPDATED_SURFACE_TYPE_DTO);
        mockMvc.perform(put("/api/surface-types/{id}", UPDATED_SURFACE_TYPE.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateSurfaceType_shouldReturnNotFound() throws Exception {
        when(service.updateSurfaceType(UPDATED_SURFACE_TYPE.getId(), UPDATE_DTO)).thenThrow(new NotFoundException());

        mockMvc.perform(put("/api/surface-types/{id}", UPDATED_SURFACE_TYPE.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_DTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSurfaceType() throws Exception {
        mockMvc.perform(delete("/api/surface-types/{id}", SURFACE_TYPE.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSurfaceType_shouldReturnNotFound() throws Exception {
        doThrow(new NotFoundException()).when(service).softDeleteSurfaceType(any(UUID.class));

        mockMvc.perform(delete("/api/surface-types/{id}", SURFACE_TYPE.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllSurfaceTypes() throws Exception {
        List<SurfaceTypeDetailDto> DTOs = List.of(SURFACE_TYPE_DTO, SURFACE_TYPE_2_DTO);
        when(service.getAllSurfaceTypes()).thenReturn(DTOs);

        mockMvc.perform(get("/api/surface-types")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(DTOs)));
    }
}
