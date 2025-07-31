package cz.tondracek.inqooltennis.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.tondracek.inqooltennis.core.config.SecurityConfig;
import cz.tondracek.inqooltennis.core.exception.ConflictException;
import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.user.dto.UserDetailDto;
import cz.tondracek.inqooltennis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cz.tondracek.inqooltennis.user.UserSample.ADMIN_DTO;
import static cz.tondracek.inqooltennis.user.UserSample.CREATE_DTO;
import static cz.tondracek.inqooltennis.user.UserSample.UPDATED_USER_DTO;
import static cz.tondracek.inqooltennis.user.UserSample.UPDATE_DTO;
import static cz.tondracek.inqooltennis.user.UserSample.USER;
import static cz.tondracek.inqooltennis.user.UserSample.USER_DTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;

    @Test
    void createUser() throws Exception {
        when(service.createUser(CREATE_DTO)).thenReturn(USER_DTO);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_DTO)));
    }

    @Test
    void createUser_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_shouldReturnConflict() throws Exception {
        when(service.createUser(CREATE_DTO)).thenThrow(new ConflictException());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CREATE_DTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void updateUser() throws Exception {
        when(service.updateUser(USER.id(), UPDATE_DTO)).thenReturn(UPDATED_USER_DTO);

        mockMvc.perform(put("/api/users/{id}", USER.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(UPDATED_USER_DTO)));
    }

    @Test
    void updateUser_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/users/{id}", USER.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser_shouldReturnNotFound() throws Exception {
        when(service.updateUser(USER.id(), UPDATE_DTO)).thenThrow(new NotFoundException());

        mockMvc.perform(put("/api/users/{id}", USER.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_DTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void findUserById() throws Exception {
        when(service.findUserById(USER.id().toString())).thenReturn(USER_DTO);

        mockMvc.perform(get("/api/users/{id}", USER.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_DTO)));
    }

    @Test
    void findUserById_shouldReturnNotFound() throws Exception {
        when(service.findUserById(USER.id().toString())).thenThrow(new NotFoundException());

        mockMvc.perform(get("/api/users/{id}", USER.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void findUserByEmail() throws Exception {
        when(service.findUserByEmail(USER.email())).thenReturn(USER_DTO);

        mockMvc.perform(get("/api/users/email/{email}", USER.email())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_DTO)));
    }

    @Test
    void findUserByEmail_shouldReturnNotFound() throws Exception {
        when(service.findUserByEmail(USER.email())).thenThrow(new NotFoundException());

        mockMvc.perform(get("/api/users/email/{email}", USER.email())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllActiveUsers() throws Exception {
        List<UserDetailDto> list = List.of(USER_DTO, ADMIN_DTO);
        when(service.findAllActiveUsers()).thenReturn(list);

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }
}
