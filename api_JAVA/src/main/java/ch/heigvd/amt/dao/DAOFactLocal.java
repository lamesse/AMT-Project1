/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : DAOFactLocal.java
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
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Interface fact DAO
 *****************************************************************************************
 */
package ch.heigvd.amt.dao;

import ch.heigvd.amt.model.Fact;
import ch.heigvd.amt.model.FactKey;
import java.util.List;
import javax.ejb.Local;

@Local
public interface DAOFactLocal {

    public abstract boolean create(Fact t);

    public abstract boolean update(Fact t);

    public abstract boolean delete(Fact t);

    public abstract Fact find(Long id);
    
    public abstract List<Fact> findByOrg(Long orgId);
    
    public abstract List<Fact> findByOrg(Long orgId, Long factId);
    
    public abstract List<Fact> findByOrgAndType(Long orgId, String type);
    
    public abstract List<Fact> findByOrgAndPublic(Long orgId, boolean isPublic);
    
    public abstract List<Fact> findByOrgTypeAndPublic(Long orgId, String type, boolean isPublic);
    
    public abstract List<Fact> findPublic();
    
    public abstract List<Fact> findPublicById(Long factId);
    
    public abstract List<Fact> findPublicByType(String type);
    
    public abstract List<Fact> findPublicByOrganization(Long orgId);
    
    public abstract List<Fact> findPublicByTypeAndOrganization(String type, Long orgId);
    
    public abstract Fact findByIdForUpdate(FactKey key);
    
}
