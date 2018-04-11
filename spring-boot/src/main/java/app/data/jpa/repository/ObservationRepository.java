package app.data.jpa.repository;

import java.util.List;
import app.data.jpa.domain.Observation;

import org.springframework.data.repository.CrudRepository;

public interface ObservationRepository extends CrudRepository<Observation, Long> {

    public Observation findByMacAddress(String macAddress);
    public List<Observation> findAll();

}
