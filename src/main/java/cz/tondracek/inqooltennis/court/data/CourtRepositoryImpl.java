package cz.tondracek.inqooltennis.court.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.court.mapper.CourtMapper;
import cz.tondracek.inqooltennis.court.model.Court;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CourtRepositoryImpl implements CourtRepository {

    private final CourtDao dao;
    private final CourtMapper mapper;

    public CourtRepositoryImpl(CourtDao courtDao, CourtMapper mapper) {
        this.dao = courtDao;
        this.mapper = mapper;
    }

    @NotNull
    @Override
    public Court create(Court entity) {
        CourtEntity courtEntity = mapper.toEntity(entity);

        dao.save(courtEntity);
        return mapper.toModel(courtEntity);
    }

    @NotNull
    @Override
    public Court update(Court entity) {
        CourtEntity courtEntity = mapper.toEntity(entity);

        dao.update(courtEntity);
        return mapper.toModel(courtEntity);
    }

    @NotNull
    @Override
    public Court findById(UUID id) {
        CourtEntity courtEntity = dao.findById(id).orElseThrow(NotFoundException::new);
        return mapper.toModel(courtEntity);
    }

    @Override
    public List<Court> findAllActive() {
        return dao.findAllActive()
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
