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
            name = "find_fact_by_org_id", query = "SELECT f FROM Fact f WHERE (f.org.id = :orgId AND (:id IS NULL OR f.id = :id))"
    ),
    @NamedQuery(
            name = "find_fact_by_org_id_and_type", query = "SELECT f FROM Fact f WHERE (f.org.id = :orgId AND f.type = :type)"
    ),
    @NamedQuery(
            name = "find_fact_by_org_id_and_public", query = "SELECT f FROM Fact f WHERE (f.org.id = :orgId AND f.isPublic = :isPublic)"
    ),
    @NamedQuery(
            name = "find_fact_by_org_id_type_and_public", query = "SELECT f FROM Fact f WHERE (f.org.id = :orgId AND f.type = :type AND f.isPublic = :isPublic)"
    ),
    @NamedQuery(
            name = "find_fact_public", query = "SELECT f FROM Fact f WHERE f.isPublic = true"
    ),
    @NamedQuery(
            name = "find_fact_public_by_id", query = "SELECT f FROM Fact f WHERE (f.isPublic = true AND f.id = :id)"
    ),
    @NamedQuery(
            name = "find_fact_public_by_type", query = "SELECT f FROM Fact f WHERE (f.isPublic = true AND f.type = :type)"
    ),
    @NamedQuery(
            name = "find_fact_public_by_organization", query = "SELECT f FROM Fact f WHERE (f.isPublic = true AND f.org.id = :orgId)"
    ),
    @NamedQuery(
            name = "find_fact_public_by_type_and_organization", query = "SELECT f FROM Fact f WHERE (f.isPublic = true AND f.type = :type AND f.org.id = :orgId)"
    )
})
@Table(name = "fact")
public class Fact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private String description;
    private boolean isPublic;

    @ManyToOne
    private Organization org;

    public Fact() {
    }

    public Fact(String type, String description, boolean isPublic, Organization org) {
        this.type = type;
        this.description = description;
        this.isPublic = isPublic;
        this.org = org;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fact)) {
            return false;
        }
        Fact other = (Fact) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ch.heigvd.amt.model.Fact[ id=" + id + " ]";
    }

}
