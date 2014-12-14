/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : DAOUser.java
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
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         User DAO
 *****************************************************************************************
 */
package ch.heigvd.amt.dao;

import ch.heigvd.amt.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DAOUser implements DAOUserLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public boolean create(User t) {
        em.persist(t);
        em.flush();
        return true;
    }

    @Override
    public boolean update(User t) {
        em.merge(t);
        em.flush();
        return true;
    }

    @Override
    public boolean delete(User t) {
        em.remove(t);
        em.flush();
        return true;
    }

    @Override
    public User find(Long id) {
        return em.find(User.class, id);
    }
    
    @Override
    public List<User> findByOrg(Long orgId) {
        return findByOrg(orgId, null);
    }
    
    @Override
    public List<User> findByOrg(Long orgId, Long userId) {
        List<User> ret = em.createNamedQuery("find_user_by_org_id").setParameter("orgId", orgId).setParameter("id", userId).getResultList();
        return ret;
    }
  

}
