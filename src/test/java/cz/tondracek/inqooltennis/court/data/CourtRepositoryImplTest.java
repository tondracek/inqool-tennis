package cz.tondracek.inqooltennis.court.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.court.mapper.CourtEntityMapper;
import cz.tondracek.inqooltennis.court.model.Court;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static cz.tondracek.inqooltennis.court.CourtSample.COURT;
import static cz.tondracek.inqooltennis.court.CourtSample.COURT_2;
import static cz.tondracek.inqooltennis.court.CourtSample.COURT_2_ENTITY;
import static cz.tondracek.inqooltennis.court.CourtSample.COURT_ENTITY;
import static cz.tondracek.inqooltennis.court.CourtSample.UPDATED_COURT;
import static cz.tondracek.inqooltennis.court.CourtSample.UPDATED_COURT_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourtRepositoryImplTest {

    @Mock
    private CourtDao dao;
    @Mock
    private CourtEntityMapper mapper;

    @InjectMocks
    private CourtRepositoryImpl repository;

    @Test
    void create() {
        when(mapper.toEntity(COURT)).thenReturn(COURT_ENTITY);

        Court result = repository.create(COURT);

        assertEquals(COURT, result);
        Mockito.verify(dao).save(COURT_ENTITY);
    }

    @Test
    void update() {
        when(mapper.toEntity(UPDATED_COURT)).thenReturn(UPDATED_COURT_ENTITY);

        Court result = repository.update(UPDATED_COURT);

        assertEquals(UPDATED_COURT, result);
        Mockito.verify(dao).update(UPDATED_COURT_ENTITY);
    }

    @Test
    void findById() {
        when(dao.findById(COURT.getId())).thenReturn(Optional.of(COURT_ENTITY));
        when(mapper.toModel(COURT_ENTITY)).thenReturn(COURT);

        Court result = repository.findById(COURT.getId());

        assertEquals(COURT, result);
    }

    @Test
    void findById_shouldFailOnNotFound() {
        when(dao.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> repository.findById(UUID.randomUUID()));
    }

    @Test
    void findAllActive() {
        List<CourtEntity> entities = List.of(COURT_ENTITY, COURT_2_ENTITY);

        when(dao.findAllActive()).thenReturn(entities);
        when(mapper.toModel(COURT_ENTITY)).thenReturn(COURT);
        when(mapper.toModel(COURT_2_ENTITY)).thenReturn(COURT_2);

        List<Court> result = repository.findAllActive();

        assertEquals(2, result.size());
        assertEquals(COURT, result.get(0));
        assertEquals(COURT_2, result.get(1));
    }
}