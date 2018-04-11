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
import javax.persistence.Column;
import javax.persistence.FetchType;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;


@Entity
public class Floor{
	@Id
    @Column(name = "BUILDING_ID", nullable = false)
    @GeneratedValue
    // @JsonIgnore
    @Getter @Setter private Long id;
    @Getter @Setter private String name;

    Floor() { // jpa only
    }

    public Floor(String name){
    	this.name = name;
    }
}