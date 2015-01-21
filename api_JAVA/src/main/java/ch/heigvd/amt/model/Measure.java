/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : Measure.java
 * Author               : Jonathan Bischof
 *                        Antoine Messerli
 * Email                : jonathan.bischof@heig-vd.ch
 *                        antoine.messerli@heig-vd.ch
 * Date                 : 27 nov. 2014
 * Project              : Project 1 AMT
 *
 *****************************************************************************************
 * Modifications :
 * Ver      Date          Engineer                                   Comments
 * 1.0      27.11.2014    Jonathan Bischof, Antoine Messerli         Measure model
 *****************************************************************************************
 */
package ch.heigvd.amt.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@NamedQueries({
    @NamedQuery(
            name = "find_measure_by_sensor_id", query = "SELECT m FROM Measure m WHERE (m.sensor.id = :sensorId)"
    ),
    @NamedQuery(
            name = "find_measure_by_sensor_id_and_measure_id", query = "SELECT m FROM Measure m WHERE (m.sensor.id = :sensorId AND m.id = :measureId)"
    ),
    @NamedQuery(
            name = "find_measure_by_sensor_id_and_organization_id", query = "SELECT m FROM Measure m WHERE (m.sensor.id = :sensorId AND m.sensor.org.id = :orgId)"
    ),
     @NamedQuery(
            name = "find_measure_by_sensor_id_and_organization_id_and_measure_id", query = "SELECT m FROM Measure m WHERE (m.sensor.id = :sensorId AND m.sensor.org.id = :orgId AND m.id = :measureId)"
    )
})
@Table(name = "measure")
public class Measure implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double value;
    private long timestamp;

    @ManyToOne
    private Sensor sensor;
    
    @Version
    int version;

    public Measure() {
    }

    public Measure(double value, long timestamp, Sensor sensor) {
        this.value = value;
        this.timestamp = timestamp;
        this.sensor = sensor;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Measure)) {
            return false;
        }
        Measure other = (Measure) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.heigvd.amt.model.Measure[ id=" + id + " ]";
    }

}
