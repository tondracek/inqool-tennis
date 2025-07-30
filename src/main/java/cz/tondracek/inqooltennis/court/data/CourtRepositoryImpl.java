package cz.tondracek.inqooltennis.court.data;

import cz.tondracek.inqooltennis.core.exception.NotFoundException;
import cz.tondracek.inqooltennis.court.mapper.CourtEntityMapper;
import cz.tondracek.inqooltennis.court.model.Court;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CourtRepositoryImpl implements CourtRepository {

    private final CourtDao dao;
    private final CourtEntityMapper mapper;

    public CourtRepositoryImpl(CourtDao courtDao, CourtEntityMapper courtEntityMapper) {
        this.dao = courtDao;
        this.mapper = courtEntityMapper;
    }

    @Override
    public Court create(Court court) {
        CourtEntity courtEntity = mapper.toEntity(court);
        dao.save(courtEntity);

        return court;
    }

    @Override
    public Court update(Court court) {
        CourtEntity courtEntity = mapper.toEntity(court);
        dao.update(courtEntity);

        return court;
    }

    @Override
    public Court findById(UUID id) {
        return dao.findById(id).map(mapper::toModel)
                .orElseThrow(() -> new NotFoundException("Court not found; id: " + id));
    }

    @Override
    public Court findActiveById(UUID id) {
        return dao.findActiveById(id).map(mapper::toModel)
                .orElseThrow(() -> new NotFoundException("Active court not found; id: " + id));
    }

    @Override
    public List<Court> findAllActive() {
        return dao.findAllActive()
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
