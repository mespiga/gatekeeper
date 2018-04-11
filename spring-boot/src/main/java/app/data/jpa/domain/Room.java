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
    @Getter @Setter public String x1Min;
    @Getter @Setter public String x1Max;
    @Getter @Setter public String y1Min;
    @Getter @Setter public String y1Max;
    @Getter @Setter public String sparkRoomId;

    public Room(String name, String xMin, String yMin, String xMax, String yMax, String sparkRoomId) {
        this.name = name;
        this.x1Min = xMin;
        this.y1Min = yMin;
        this.x1Max = xMax;
        this.y1Max = yMax;
        this.sparkRoomId = sparkRoomId;
    }

    public Room() {}

}

