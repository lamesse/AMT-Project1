/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : ServiceSensor.java
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
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Service sensor
 *****************************************************************************************
 */
package ch.heigvd.amt.webservices;

import ch.heigvd.amt.dao.DAOMeasureLocal;
import ch.heigvd.amt.dao.DAOSensorLocal;
import ch.heigvd.amt.dto.MeasureDTO;
import ch.heigvd.amt.dto.SensorPublicDTO;
import ch.heigvd.amt.model.Measure;
import ch.heigvd.amt.model.Sensor;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/sensors")
@Stateless
public class ServiceSensor {

    @EJB
    DAOSensorLocal sensDAO;

    @EJB
    DAOMeasureLocal mesDAO;

    @GET // OK
    @Produces("application/json")
    public List<SensorPublicDTO> getSensors() {
        return convertSensorsToSensorsDTO(sensDAO.findPublic());
    }

    @GET // OK
    @Path("/{sensorId}")
    @Produces("application/json")
    public SensorPublicDTO getSensorById(@PathParam("sensorId") String sensorId) {
        return convertSensorsToSensorsDTO(sensDAO.findPublicById(Long.parseLong(sensorId))).get(0);
    }

    @GET // OK
    @Path("/filter/type/{type}")
    @Produces("application/json")
    public List<SensorPublicDTO> getFilteredSensorsByType(@PathParam("type") String type) {
        return convertSensorsToSensorsDTO(sensDAO.findPublicByType(type));
    }

    @GET // OK
    @Path("/filter/organization/{orgId}")
    @Produces("application/json")
    public List<SensorPublicDTO> getFilteredSensorsByOrganization(@PathParam("orgId") String orgId) {
        return convertSensorsToSensorsDTO(sensDAO.findPublicByOrganization(Long.parseLong(orgId)));
    }

    @GET // OK
    @Path("/filter/type/{type}/organization/{orgId}")
    @Produces("application/json")
    public List<SensorPublicDTO> getFilteredSensorsByTypeAndOrganization(@PathParam("type") String type, @PathParam("orgId") String orgId) {
        return convertSensorsToSensorsDTO(sensDAO.findPublicByTypeAndOrganization(type, Long.parseLong(orgId)));
    }

    @GET // OK
    @Path("/{sensorId}/measures")
    @Produces("application/json")
    public List<MeasureDTO> getMeasures(@PathParam("sensorId") String sensorId) {
        return convertMeasuresToMeasuresDTO(mesDAO.findMeasureBySensorId(Long.parseLong(sensorId)));
    }

    @GET // OK
    @Path("/{sensorId}/measures/{measureId}")
    @Produces("application/json")
    public MeasureDTO getMeasureById(@PathParam("sensorId") String sensorId, @PathParam("measureId") String measureId) {
        return convertMeasuresToMeasuresDTO(mesDAO.findMeasureBySensorIdAndMeasureId(Long.parseLong(sensorId), Long.parseLong(measureId))).get(0);
    }

    private List<SensorPublicDTO> convertSensorsToSensorsDTO(List<Sensor> list) {
        List<SensorPublicDTO> result = new LinkedList<>();
        for (Sensor s : list) {
            SensorPublicDTO dto = new SensorPublicDTO();
            dto.setName(s.getName());
            dto.setType(s.getType());
            dto.setDescription(s.getDescription());
            dto.setId(s.getId());
            result.add(dto);
        }
        return result;
    }

    private List<MeasureDTO> convertMeasuresToMeasuresDTO(List<Measure> list) {
        List<MeasureDTO> result = new LinkedList<>();
        for (Measure m : list) {
            MeasureDTO dto = new MeasureDTO();
            dto.setId(m.getId());
            dto.setTimestamp(m.getTimestamp());
            dto.setValue(m.getValue());
            result.add(dto);
        }
        return result;
    }
}
