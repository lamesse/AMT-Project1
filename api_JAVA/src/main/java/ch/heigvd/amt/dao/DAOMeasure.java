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

import ch.heigvd.amt.model.Fact;
import ch.heigvd.amt.model.FactKey;
import ch.heigvd.amt.model.Measure;
import ch.heigvd.amt.model.Sensor;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DAOMeasure implements DAOMeasureLocal {

    @PersistenceContext
    EntityManager em;

    @EJB
    DAOFactLocal daoFact;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean create(Measure m) {
        em.persist(m);
        em.flush();
        updateDailyFact(m);
        updateCounterFact(m);
        return true;
    }

    private void updateDailyFact(Measure m) {
        FactKey key = new FactKey("daily", m.getSensor().getType(), new Date(m.getTimestamp()));
        Fact fact = daoFact.findByIdForUpdate(key);
        double min, max, avg, counter;
        if (fact == null) {
            Sensor s = m.getSensor();
            fact = new Fact(key, s.isIsPublic(), s.getOrg());
            max = m.getValue();
            avg = m.getValue();
            min = m.getValue();
            counter = 1;
        } else {
            max = fact.getMax();
            min = fact.getMin();
            avg = fact.getAvg();
            counter = fact.getAvgCounter();
            double value = m.getValue();
            if (min > value) {
                min = value;
            }

            if (max < value) {
                max = value;
            }
            avg = ((avg * counter) + value) / ++counter;
        }
        fact.setAvg(avg);
        fact.setMin(min);
        fact.setMax(max);
        fact.setAvgCounter(counter);

        em.persist(fact);
        em.flush();
    }

    private void updateCounterFact(Measure m) {
        FactKey key = new FactKey("sensorCounter", m.getSensor().getId().toString(), new Date(0));
        Fact fact = daoFact.findByIdForUpdate(key);
        double counter;
        if (fact == null) {
            Sensor s = m.getSensor();
            fact = new Fact(key, s.isIsPublic(), s.getOrg());
            fact.setMax(0);
            fact.setAvg(0);
            fact.setMin(0);
            counter = 1;
        } else {
            counter = fact.getAvgCounter();
            ++counter;
        }
        fact.setAvgCounter(counter);
        em.persist(fact);
        em.flush();
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
