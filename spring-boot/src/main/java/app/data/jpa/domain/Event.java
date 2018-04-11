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
import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue
    public Long id;

    @Getter @Setter public String name;
    @Getter @Setter public String speaker;
    @Getter @Setter public String speakerImgUrl;
    @Getter @Setter public LocalDateTime startDate;
    @Getter @Setter public LocalDateTime endDate;
    @Getter @Setter public String tags;
    @Getter @Setter public Long roomId;

    public Event(String name, String speaker, LocalDateTime startDate, LocalDateTime endDate, String tags, Long roomId, String speakerImgUrl) {
        this.name = name;
        this.speaker = speaker;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = tags;
        this.roomId = roomId;
        this.speakerImgUrl = speakerImgUrl;
    }

    public Event() {
        
    }
}

