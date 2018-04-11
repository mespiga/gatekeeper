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
public class Observation {

    @Id
    @GeneratedValue
    public Long id;

    @Getter @Setter public String macAddress;
    @Getter @Setter public String x;
    @Getter @Setter public String y;
    @Getter @Setter public String unc;
    @Getter @Setter public String timestamp;
    @Getter @Setter public String os;
    @Getter @Setter public String apMac;

    public Observation(String macAddress, String x, String y, String unc, String timestamp, String os, String apMac) {
        this.macAddress = macAddress;
        this.x = x;
        this.y = y;
        this.unc = unc;
        this.timestamp = timestamp;
        this.os = os;
        this.apMac = apMac;
    }

    public Observation() {
        
    }

   

}
