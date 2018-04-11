package app.data.jpa.repository;

import java.util.List;
import app.data.jpa.domain.Room;

import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {

    public Room findByName(String name);
    public List<Room> findBySparkRoomId(String sparkRoomId);
    public List<Room> findAll();

}
