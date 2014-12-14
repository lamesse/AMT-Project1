/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : DAOSensorLocal.java
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
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Interface sensor DAO
 *****************************************************************************************
 */
package ch.heigvd.amt.dao;

import ch.heigvd.amt.model.Sensor;
import java.util.List;
import javax.ejb.Local;

@Local
public interface DAOSensorLocal {

    public abstract boolean create(Sensor t);

    public abstract boolean update(Sensor t);

    public abstract boolean delete(Sensor t);

    public abstract Sensor find(Long id);
    
    public abstract List<Sensor> findByOrg(Long orgId);
    
    public abstract List<Sensor> findByOrg(Long orgId, Long sensorId);
    
    public abstract List<Sensor> findByOrgAndType(Long orgId, String type);
    
    public abstract List<Sensor> findByOrgAndPublic(Long orgId, boolean isPublic);
    
    public abstract List<Sensor> findByOrgTypeAndPublic(Long orgId, String type, boolean isPublic);
    
    public abstract List<Sensor> findPublic();
    
    public abstract List<Sensor> findPublicById(Long sensorId);
    
    public abstract List<Sensor> findPublicByType(String type);
    
    public abstract List<Sensor> findPublicByOrganization(Long orgId);
    
    public abstract List<Sensor> findPublicByTypeAndOrganization(String type, Long orgId);
}
