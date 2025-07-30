package cz.tondracek.inqooltennis.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.tondracek.inqooltennis.core.config.SecurityConfig;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.court.CourtSample;
import cz.tondracek.inqooltennis.reservation.dto.CourtReservationListDto;
import cz.tondracek.inqooltennis.reservation.dto.ReservationPreviewDto;
import cz.tondracek.inqooltennis.reservation.service.ReservationService;
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
import static cz.tondracek.inqooltennis.customer.CustomerSample.CUSTOMER;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_DTO;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.RESERVATION_PREVIEW_DTO;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.UPDATED_RESERVATION_DTO;
import static cz.tondracek.inqooltennis.reservation.ReservationSample.UPDATE_DTO;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService service;

    @Test
    void createReservation() throws Exception {
        when(service.createReservation(CREATE_DTO)).thenReturn(RESERVATION_DTO);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(RESERVATION_DTO)));
    }

    @Test
    void updateReservation() throws Exception {
        when(service.updateReservation(RESERVATION.getId(), UPDATE_DTO)).thenReturn(UPDATED_RESERVATION_DTO);

        mockMvc.perform(put("/api/reservations/{id}", RESERVATION.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(UPDATED_RESERVATION_DTO)));
    }

    @Test
    void deleteReservation() throws Exception {
        mockMvc.perform(delete("/api/reservations/{id}", RESERVATION.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void getReservationById() throws Exception {
        when(service.getReservationById(RESERVATION.getId())).thenReturn(RESERVATION_DTO);

        mockMvc.perform(get("/api/reservations/{id}", RESERVATION.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(RESERVATION_DTO)));
    }

    @Test
    void getAllReservations() throws Exception {
        List<ReservationPreviewDto> result = List.of(RESERVATION_PREVIEW_DTO);
        when(service.getActiveReservations()).thenReturn(result);

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void getReservationsByCourtId() throws Exception {
        CourtReservationListDto result = new CourtReservationListDto(
                CourtSample.COURT_DTO,
                List.of(RESERVATION_PREVIEW_DTO)
        );
        when(service.getCourtReservations(COURT.getId())).thenReturn(result);

        mockMvc.perform(get("/api/reservations/court/{courtId}", COURT.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void getReservationsByPhoneNumber_futureOnlyTrue() throws Exception {
        List<ReservationPreviewDto> result = List.of(RESERVATION_PREVIEW_DTO);
        when(service.getReservationsByPhoneNumber(CUSTOMER.getPhoneNumber(), true)).thenReturn(result);

        mockMvc.perform(get("/api/reservations/phone/{phoneNumber}/futureOnly/{futureOnly}", CUSTOMER.getPhoneNumber(), true))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void getReservationsByPhoneNumber_futureOnlyFalse() throws Exception {
        List<ReservationPreviewDto> result = List.of(RESERVATION_PREVIEW_DTO);
        when(service.getReservationsByPhoneNumber(CUSTOMER.getPhoneNumber(), false)).thenReturn(result);

        mockMvc.perform(get("/api/reservations/phone/{phoneNumber}/futureOnly/{futureOnly}", CUSTOMER.getPhoneNumber(), false))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void deleteReservation_shouldReturnNotFound() throws Exception {
        doThrow(new NotFoundException()).when(service).softDeleteReservation(RESERVATION.getId());

        mockMvc.perform(delete("/api/reservations/{id}", RESERVATION.getId()))
                .andExpect(status().isNotFound());
    }
}
