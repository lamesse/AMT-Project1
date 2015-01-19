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
import javax.persistence.TypedQuery;

@Stateless
public class DAOFact implements DAOFactLocal {
    
    @PersistenceContext
    EntityManager em;
    
    @Override
    public boolean create(Fact f) {
        em.persist(f);
        em.flush();
        return true;
    }
    
    @Override
    public boolean update(Fact f) {
        em.merge(f);
        em.flush();
        return true;
    }
    
    @Override
    public boolean delete(Fact f) {
        em.remove(f);
        em.flush();
        return true;
    }
    
    @Override
    public List<Fact> findByOrg(Long orgId) {
        TypedQuery<Fact> q = em.createNamedQuery("find_fact_by_org_id", Fact.class).setParameter("orgId", orgId);
        q.setLockMode(LockModeType.PESSIMISTIC_READ);
        return q.getResultList();
    }
    
    @Override
    public List<Fact> findByOrgAndType(Long orgId, String type) {
        TypedQuery<Fact> q = em.createNamedQuery("find_fact_by_org_id_and_type", Fact.class).setParameter("orgId", orgId).setParameter("type", type);
        q.setLockMode(LockModeType.PESSIMISTIC_READ);
        return q.getResultList();
    }
    
    @Override
    public List<Fact> findByOrgAndPublic(Long orgId, boolean isPublic) {
        TypedQuery<Fact> q = em.createNamedQuery("find_fact_by_org_id_and_public", Fact.class).setParameter("orgId", orgId).setParameter("isPublic", isPublic);
        q.setLockMode(LockModeType.PESSIMISTIC_READ);
        return q.getResultList();
    }
    
    @Override
    public List<Fact> findByOrgTypeAndPublic(Long orgId, String type, boolean isPublic) {
        TypedQuery<Fact> q = em.createNamedQuery("find_fact_by_org_id_type_and_public", Fact.class).setParameter("orgId", orgId).setParameter("type", type).setParameter("isPublic", isPublic);
        q.setLockMode(LockModeType.PESSIMISTIC_READ);
        return q.getResultList();
    }
    
    @Override
    public List<Fact> findPublic() {
        TypedQuery<Fact> q = em.createNamedQuery("find_fact_public", Fact.class);
        q.setLockMode(LockModeType.PESSIMISTIC_READ);
        return q.getResultList();
    }
    
    @Override
    public List<Fact> findPublicByType(String type) {
        TypedQuery<Fact> q = em.createNamedQuery("find_fact_public_by_type", Fact.class).setParameter("type", type); 
        q.setLockMode(LockModeType.PESSIMISTIC_READ);
        return q.getResultList();
    }
    
    @Override
    public List<Fact> findPublicByOrganization(Long orgId) {
        TypedQuery<Fact> q = em.createNamedQuery("find_fact_public_by_organization", Fact.class).setParameter("orgId", orgId);
        q.setLockMode(LockModeType.PESSIMISTIC_READ);
        return q.getResultList();
    }
    
    @Override
    public List<Fact> findPublicByTypeAndOrganization(String type, Long orgId) {
        TypedQuery<Fact> q = em.createNamedQuery("find_fact_public_by_type_and_organization", Fact.class).setParameter("type", type).setParameter("orgId", orgId);
        q.setLockMode(LockModeType.PESSIMISTIC_READ);
        return q.getResultList();
    }

    @Override
    public Fact findByIdForUpdate(FactKey key) {
        return em.find(Fact.class, key, LockModeType.PESSIMISTIC_WRITE);
    }
}
