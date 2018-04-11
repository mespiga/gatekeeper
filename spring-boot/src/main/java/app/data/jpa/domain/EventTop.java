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

public class EventTop implements Comparable<EventTop> {

    @Getter @Setter public Event event;
    @Getter @Setter public int counter;

    public EventTop(Event event, int counter) {
        this.event = event;
        this.counter = counter;
    }

    @Override
    public int compareTo(EventTop comparestu) {
        int compareage=((EventTop)comparestu).getCounter();
        /* For Ascending order*/
        // return this.counter-compareage;

        /* For Descending order do like this */
        return compareage-this.counter;
    }

    @Override
    public String toString() {
        return "[ event=" + event + ", counter=" + counter + "]";
    }

}

