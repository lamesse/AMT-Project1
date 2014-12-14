/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : DAOSensor.java
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
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Sensor DAO
 *****************************************************************************************
 */
package ch.heigvd.amt.dao;

import ch.heigvd.amt.model.Sensor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DAOSensor implements DAOSensorLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public boolean create(Sensor t) {
        em.persist(t);
        em.flush();
        return true;
    }

    @Override
    public boolean update(Sensor t) {
        em.merge(t);
        em.flush();
        return true;
    }

    @Override
    public boolean delete(Sensor t) {
        em.remove(t);
        em.flush();
        return true;
    }

    @Override
    public Sensor find(Long id) {
        return em.find(Sensor.class, id);
    }

    @Override
    public List<Sensor> findByOrg(Long orgId) {
        return findByOrg(orgId, null);
    }

    @Override
    public List<Sensor> findByOrg(Long orgId, Long sensorId) {
        List<Sensor> ret = em.createNamedQuery("find_sensor_by_org_id").setParameter("orgId", orgId).setParameter("id", sensorId).getResultList();
        return ret;
    }

    @Override
    public List<Sensor> findByOrgAndType(Long orgId, String type) {
        List<Sensor> ret = em.createNamedQuery("find_sensor_by_org_id_and_type").setParameter("orgId", orgId).setParameter("type", type).getResultList();
        return ret;
    }

    @Override
    public List<Sensor> findByOrgAndPublic(Long orgId, boolean isPublic) {
        List<Sensor> ret = em.createNamedQuery("find_sensor_by_org_id_and_public").setParameter("orgId", orgId).setParameter("isPublic", isPublic).getResultList();
        return ret;
    }

    @Override
    public List<Sensor> findPublic() {
        List<Sensor> ret = em.createNamedQuery("find_sensor_public").getResultList();
        return ret;
    }

    @Override
    public List<Sensor> findPublicById(Long sensorId) {
        List<Sensor> ret = em.createNamedQuery("find_sensor_public_by_id").setParameter("id", sensorId).getResultList();
        return ret;
    }

    @Override
    public List<Sensor> findByOrgTypeAndPublic(Long orgId, String type, boolean isPublic) {
        List<Sensor> ret = em.createNamedQuery("find_sensor_by_org_id_type_and_public").setParameter("orgId", orgId).setParameter("type", type).setParameter("isPublic", isPublic).getResultList();
        return ret;
    }

    @Override
    public List<Sensor> findPublicByType(String type) {
        List<Sensor> ret = em.createNamedQuery("find_sensor_public_by_type").setParameter("type", type).getResultList();
        return ret;
    }

    @Override
    public List<Sensor> findPublicByOrganization(Long orgId) {
        List<Sensor> ret = em.createNamedQuery("find_sensor_public_by_organization").setParameter("orgId", orgId).getResultList();
        return ret;
    }

    @Override
    public List<Sensor> findPublicByTypeAndOrganization(String type, Long orgId) {
        List<Sensor> ret = em.createNamedQuery("find_sensor_public_by_type_and_organization").setParameter("type", type).setParameter("orgId", orgId).getResultList();
        return ret;
    }

}
