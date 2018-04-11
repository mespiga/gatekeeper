package app.data.jpa.repository;

import java.util.List;
import app.data.jpa.domain.Event;

import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

    public Event findByName(String name);
    public List<Event> findAll();
    public List<Event> findByRoomId(String roomId);

}
