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
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Interface measure DAO
 *****************************************************************************************
 */
package ch.heigvd.amt.dao;

import ch.heigvd.amt.model.Measure;
import java.util.List;
import javax.ejb.Local;

@Local
public interface DAOMeasureLocal {

    public abstract boolean create(Measure t);

    public abstract boolean update(Measure t);

    public abstract boolean delete(Measure t);

    public abstract Measure find(Long id);

    public abstract List<Measure> findMeasureBySensorId(Long sensorId);

    public abstract List<Measure> findMeasureBySensorIdAndMeasureId(Long sensorId, Long measureId);
    
    public abstract List<Measure> findMeasureBySensorIdAndOrganizationId(Long sensorId, Long organizationId);
    
    public abstract List<Measure> findMeasureBySensorIdAndOrganizationIdAndMeasureId(Long sensorId, Long organizationId, Long measureId);
}
