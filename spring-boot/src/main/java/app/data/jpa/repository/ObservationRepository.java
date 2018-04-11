package app.data.jpa.repository;

import java.util.List;
import app.data.jpa.domain.Observation;
import java.time.LocalDateTime;

import org.springframework.data.repository.CrudRepository;

public interface ObservationRepository extends CrudRepository<Observation, Long> {

    public Observation findByMacAddress(String macAddress);
    public List<Observation> findAll();
    public List<Observation> findDistinctObservationsByTimestampBetweenAndXBetweenAndYBetween(LocalDateTime startDate, LocalDateTime endDate, Double x1Min,Double x1Max, Double y1Min, Double y1Max);

}
