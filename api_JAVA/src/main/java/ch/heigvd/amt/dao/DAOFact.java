/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : DAOFact.java
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
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Fact DAO
 *****************************************************************************************
 */
package ch.heigvd.amt.dao;

import ch.heigvd.amt.model.Fact;
import ch.heigvd.amt.model.FactKey;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Stateless
public class DAOFact implements DAOFactLocal {
    
    @PersistenceContext
    EntityManager em;
    
    @Override
    public boolean create(Fact t) {
        em.persist(t);
        em.flush();
        return true;
    }
    
    @Override
    public boolean update(Fact t) {
        em.merge(t);
        em.flush();
        return true;
    }
    
    @Override
    public boolean delete(Fact t) {
        em.remove(t);
        em.flush();
        return true;
    }
    
    @Override
    public Fact find(Long id) {
        return em.find(Fact.class, id);
    }
    
    @Override
    public List<Fact> findByOrg(Long orgId) {
        return findByOrg(orgId, null);
    }
    
    @Override
    public List<Fact> findByOrg(Long orgId, Long factId) {
        List<Fact> ret = em.createNamedQuery("find_fact_by_org_id").setParameter("orgId", orgId).setParameter("id", factId).getResultList();
        return ret;
    }
    
    @Override
    public List<Fact> findByOrgAndType(Long orgId, String type) {
        List<Fact> ret = em.createNamedQuery("find_fact_by_org_id_and_type").setParameter("orgId", orgId).setParameter("type", type).getResultList();
        return ret;
    }
    
    @Override
    public List<Fact> findByOrgAndPublic(Long orgId, boolean isPublic) {
        List<Fact> ret = em.createNamedQuery("find_fact_by_org_id_and_public").setParameter("orgId", orgId).setParameter("isPublic", isPublic).getResultList();
        return ret;
    }
    
    @Override
    public List<Fact> findByOrgTypeAndPublic(Long orgId, String type, boolean isPublic) {
        List<Fact> ret = em.createNamedQuery("find_fact_by_org_id_type_and_public").setParameter("orgId", orgId).setParameter("type", type).setParameter("isPublic", isPublic).getResultList();
        return ret;
    }
    
    @Override
    public List<Fact> findPublic() {
        List<Fact> ret = em.createNamedQuery("find_fact_public").getResultList();
        return ret;
    }
    
    @Override
    public List<Fact> findPublicById(Long factId) {
        List<Fact> ret = em.createNamedQuery("find_fact_public_by_id").setParameter("id", factId).getResultList();
        return ret;
    }
    
    @Override
    public List<Fact> findPublicByType(String type) {
        List<Fact> ret = em.createNamedQuery("find_fact_public_by_type").setParameter("type", type).getResultList();
        return ret;
    }
    
    @Override
    public List<Fact> findPublicByOrganization(Long orgId) {
        List<Fact> ret = em.createNamedQuery("find_fact_public_by_organization").setParameter("orgId", orgId).getResultList();
        return ret;
    }
    
    @Override
    public List<Fact> findPublicByTypeAndOrganization(String type, Long orgId) {
        List<Fact> ret = em.createNamedQuery("find_fact_public_by_type_and_organization").setParameter("type", type).setParameter("orgId", orgId).getResultList();
        return ret;
    }

    @Override
    public Fact findByIdForUpdate(FactKey key) {
        return em.find(Fact.class, key, LockModeType.PESSIMISTIC_WRITE);
    }
}
