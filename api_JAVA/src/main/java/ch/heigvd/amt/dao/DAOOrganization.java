/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : DAOOrganization.java
 * Author               : Jonathan Bischof
 *                        Antoine Messerli
 * Email                : jonathan.bischof@heig-vd.ch
 *                        antoine.messerli@heig-vd.ch
 * Date                 : 04 dec. 2014
 * Project              : Project 1 AMT
 *
 *****************************************************************************************
 * Modifications :
 * Ver      Date          Engineer                                   Comments
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Organization DAO
 *****************************************************************************************
 */
package ch.heigvd.amt.dao;

import ch.heigvd.amt.model.Organization;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DAOOrganization implements DAOOrganizationLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public boolean create(Organization t) {
        em.persist(t);
        em.flush();
        return true;
    }

    @Override
    public boolean update(Organization t) {
        em.merge(t);
        em.flush();
        return true;
    }

    @Override
    public boolean delete(Organization t) {
        em.remove(t);
        em.flush();
        return true;
    }

    @Override
    public List<Organization> find(Long id) {
        return em.createNamedQuery("find_organization").setParameter("id", id).getResultList();
    }

}
