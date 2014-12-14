/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : Sensor.java
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
 * 1.0      27.11.2014    Jonathan Bischof, Antoine Messerli         Sensor model
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

@Entity
@NamedQueries({
    @NamedQuery(
            name = "find_sensor_by_org_id", query = "SELECT s FROM Sensor s WHERE (s.org.id = :orgId AND (:id IS NULL OR s.id = :id))"
    ),
    @NamedQuery(
            name = "find_sensor_by_org_id_and_type", query = "SELECT s FROM Sensor s WHERE (s.org.id = :orgId AND s.type = :type)"
    ),
    @NamedQuery(
            name = "find_sensor_by_org_id_and_public", query = "SELECT s FROM Sensor s WHERE (s.org.id = :orgId AND s.isPublic = :isPublic)"
    ),
    @NamedQuery(
            name = "find_sensor_by_org_id_type_and_public", query = "SELECT s FROM Sensor s WHERE (s.org.id = :orgId AND s.type = :type AND s.isPublic = :isPublic)"
    ),
    @NamedQuery(
            name = "find_sensor_public", query = "SELECT s FROM Sensor s WHERE s.isPublic = true"
    ),
    @NamedQuery(
            name = "find_sensor_public_by_id", query = "SELECT s FROM Sensor s WHERE (s.isPublic = true AND s.id = :id)"
    ),
    @NamedQuery(
            name = "find_sensor_public_by_type", query = "SELECT s FROM Sensor s WHERE (s.isPublic = true AND s.type = :type)"
    ),
    @NamedQuery(
            name = "find_sensor_public_by_organization", query = "SELECT s FROM Sensor s WHERE (s.isPublic = true AND s.org.id = :orgId)"
    ),
    @NamedQuery(
            name = "find_sensor_public_by_type_and_organization", query = "SELECT s FROM Sensor s WHERE (s.isPublic = true AND s.type = :type AND s.org.id = :orgId)"
    )
})
@Table(name = "sensor")
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String type;
    private boolean isPublic;

    @ManyToOne
    private Organization org;

    public Sensor() {
    }

    public Sensor(String name, String description, String type, boolean isPublic, Organization org) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.isPublic = isPublic;
        this.org = org;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrganization(Organization org) {
        this.org = org;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
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
        if (!(object instanceof Sensor)) {
            return false;
        }
        Sensor other = (Sensor) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ch.heigvd.amt.model.Sensor[ id=" + id + " ]";
    }

}
