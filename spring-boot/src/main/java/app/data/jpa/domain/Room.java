/*
 * Author: Miguel Espiga
 */

package app.data.jpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Room {

    @Id
    @GeneratedValue
    public Long id;

    @Getter @Setter public String name;
    @Getter @Setter public String xMin;
    @Getter @Setter public String xMax;
    @Getter @Setter public String yMin;
    @Getter @Setter public String yMax;
    @Getter @Setter public String sparkRoomId;

    public Room() {}

    public Room(String name, String xMin, String yMin, String xMax, String yMax, String sparkRoomId) {
        this.name = name;
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMin = yMax;
        this.sparkRoomId = sparkRoomId;
    }

}

