/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : DAOMeasure.java
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
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Measure DAO
 *****************************************************************************************
 */
package ch.heigvd.amt.dao;

import ch.heigvd.amt.model.Measure;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DAOMeasure implements DAOMeasureLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public boolean create(Measure t) {
        em.persist(t);
        em.flush();
        return true;
    }

    @Override
    public boolean update(Measure t) {
        em.merge(t);
        em.flush();
        return true;
    }

    @Override
    public boolean delete(Measure t) {
        em.remove(t);
        em.flush();
        return true;
    }

    @Override
    public Measure find(Long id) {
        return em.find(Measure.class, id);
    }

    @Override
    public List<Measure> findMeasureBySensorId(Long sensorId) {
        List<Measure> ret = em.createNamedQuery("find_measure_by_sensor_id").setParameter("sensorId", sensorId).getResultList();
        return ret;
    }

    @Override
    public List<Measure> findMeasureBySensorIdAndMeasureId(Long sensorId, Long measureId) {
        List<Measure> ret = em.createNamedQuery("find_measure_by_sensor_id_and_measure_id").setParameter("sensorId", sensorId).setParameter("measureId", measureId).getResultList();
        return ret;
    }

    @Override
    public List<Measure> findMeasureBySensorIdAndOrganizationId(Long sensorId, Long organizationId) {
        List<Measure> ret = em.createNamedQuery("find_measure_by_sensor_id_and_organization_id").setParameter("sensorId", sensorId).setParameter("orgId", organizationId).getResultList();
        return ret;
    }

    @Override
    public List<Measure> findMeasureBySensorIdAndOrganizationIdAndMeasureId(Long sensorId, Long organizationId, Long measureId) {
        List<Measure> ret = em.createNamedQuery("find_measure_by_sensor_id_and_organization_id_and_measure_id").setParameter("sensorId", sensorId).setParameter("orgId", organizationId).setParameter("measureId", measureId).getResultList();
        return ret;
    }
}
