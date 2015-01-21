/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : Fact.java
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
 * 1.0      27.11.2014    Jonathan Bischof, Antoine Messerli         Fact model
 *****************************************************************************************
 */
package ch.heigvd.amt.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@NamedQueries({
    @NamedQuery(
            name = "find_fact_by_org_id", query = "SELECT f FROM Fact f WHERE (f.org.id = :orgId)"
    ),
    @NamedQuery(
            name = "find_fact_by_org_id_and_type", query = "SELECT f FROM Fact f WHERE (f.org.id = :orgId AND f.key.factType = :type)"
    ),
    @NamedQuery(
            name = "find_fact_by_org_id_and_public", query = "SELECT f FROM Fact f WHERE (f.org.id = :orgId AND f.isPublic = :isPublic)"
    ),
    @NamedQuery(
            name = "find_fact_by_org_id_type_and_public", query = "SELECT f FROM Fact f WHERE (f.org.id = :orgId AND f.key.factType = :type AND f.isPublic = :isPublic)"
    ),
    @NamedQuery(
            name = "find_fact_public", query = "SELECT f FROM Fact f WHERE f.isPublic = true"
    ),
    @NamedQuery(
            name = "find_fact_public_by_type", query = "SELECT f FROM Fact f WHERE (f.isPublic = true AND f.key.factType = :type)"
    ),
    @NamedQuery(
            name = "find_fact_public_by_organization", query = "SELECT f FROM Fact f WHERE (f.isPublic = true AND f.org.id = :orgId)"
    ),
    @NamedQuery(
            name = "find_fact_public_by_type_and_organization", query = "SELECT f FROM Fact f WHERE (f.isPublic = true AND f.key.factType = :type AND f.org.id = :orgId)"
    )
})
@Table(name = "fact")
public class Fact implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private FactKey key;
    private double minimum;
    private double average;
    private double maximum;
    private double avgCounter;
    private boolean isPublic;

    @ManyToOne
    private Organization org;
    
    @Version
    int version;

    public Fact() {
    }

    public Fact(FactKey key, boolean isPublic, Organization org) {
        this.key = key;
        this.isPublic = isPublic;
        this.org = org;
    }

    public Fact(String type, String sensorType, String description, boolean isPublic, Organization org) {
        key = new FactKey(type, sensorType, new Date(System.currentTimeMillis()));
        this.isPublic = isPublic;
        this.org = org;
    }

    public FactKey getKey() {
        return key;
    }

    public void setKey(FactKey key) {
        this.key = key;
    }

    public String getType() {
        return key.getFactType();
    }

    public void setType(String type) {
        key.setFactType(type);
    }

    public String getSensorType() {
        return key.getSensType();
    }

    public void setSensorType(String sensorType) {
        key.setSensType(sensorType);
    }

    public Date getDate() {
        return key.getDate();
    }

    public void setDate(Date date) {
        key.setDate(date);
    }

    public double getMin() {
        return minimum;
    }

    public void setMin(double min) {
        this.minimum = min;
    }

    public double getAvg() {
        return average;
    }

    public void setAvg(double avg) {
        this.average = avg;
    }

    public double getMax() {
        return maximum;
    }

    public void setMax(double max) {
        this.maximum = max;
    }

    public double getAvgCounter() {
        return avgCounter;
    }

    public void setAvgCounter(double avgCounter) {
        this.avgCounter = avgCounter;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }
}
