/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : ServiceOrganization.java
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
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Service organization
 *****************************************************************************************
 */
package ch.heigvd.amt.webservices;

import ch.heigvd.amt.dao.DAOFactLocal;
import ch.heigvd.amt.dao.DAOMeasureLocal;
import ch.heigvd.amt.dao.DAOOrganizationLocal;
import ch.heigvd.amt.dao.DAOSensorLocal;
import ch.heigvd.amt.dao.DAOUserLocal;
import ch.heigvd.amt.dto.FactDTO;
import ch.heigvd.amt.dto.MeasureDTO;
import ch.heigvd.amt.dto.OrganizationDTO;
import ch.heigvd.amt.dto.SensorDTO;
import ch.heigvd.amt.dto.UserDTO;
import ch.heigvd.amt.model.Fact;
import ch.heigvd.amt.model.Measure;
import ch.heigvd.amt.model.Organization;
import ch.heigvd.amt.model.Sensor;
import ch.heigvd.amt.model.User;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/organizations")
@Stateless
public class ServiceOrganization {

    @EJB
    DAOOrganizationLocal organizationDAO;

    @EJB
    DAOUserLocal userDAO;

    @EJB
    DAOSensorLocal sensDAO;

    @EJB
    DAOFactLocal factDAO;

    @EJB
    DAOMeasureLocal mesDAO;

    /*=====================================================================*/
    @GET // OK
    @Produces("application/json")
    public List<OrganizationDTO> getOrganizations() {
        return convertOrgnanizationsToOrganizationsDTO(organizationDAO.find(null));
    }

    @POST // OK
    @Consumes("application/json")
    public Response addOrganization(Organization org) {
        organizationDAO.create(org);
        return Response.status(201).entity("CREATED").build();
    }
    /*=====================================================================*/

    /*=====================================================================*/
    @GET // OK
    @Path("/{orgId}")
    @Produces("application/json")
    public List<OrganizationDTO> getOrganizationById(@PathParam("orgId") String id) {
        return convertOrgnanizationsToOrganizationsDTO(organizationDAO.find(Long.parseLong(id)));
    }

    @DELETE // OK
    @Path("/{orgId}")
    public Response deleteOrganizationById(@PathParam("orgId") String id) {
        organizationDAO.delete(organizationDAO.find(Long.parseLong(id)).get(0));
        return Response.status(200).entity("OK").build();
    }

    @PUT // OK
    @Path("/{orgId}")
    @Consumes("application/json")
    public Response updateOrganizationById(Organization org, @PathParam("orgId") String orgId) {
        org.setId(Long.parseLong(orgId));
        organizationDAO.update(org);
        return Response.status(201).entity("UPDATED").build();
    }
    /*=====================================================================*/

    /*=====================================================================*/
    @GET // OK
    @Path("/{orgId}/users")
    @Produces("application/json")
    public List<UserDTO> getOrganizationUsers(@PathParam("orgId") String id) {
        return convertUsersToUsersDTO(userDAO.findByOrg(Long.parseLong(id)));
    }

    @POST // OK
    @Path("/{orgId}/users")
    @Consumes("application/json")
    public Response addOrganizationUser(User user, @PathParam("orgId") String id) {
        user.setOrganization(organizationDAO.find(Long.parseLong(id)).get(0));
        userDAO.create(user);
        return Response.status(201).entity("CREATED").build();
    }
    /*=====================================================================*/

    /*=====================================================================*/
    @GET // OK
    @Path("/{orgId}/users/{userId}")
    @Produces("application/json")
    public UserDTO getOrganizationUserById(@PathParam("orgId") String orgId, @PathParam("userId") String userId) {
        return convertUsersToUsersDTO(userDAO.findByOrg(Long.parseLong(orgId), Long.parseLong(userId))).get(0);
    }

    @DELETE // OK
    @Path("/{orgId}/users/{userId}")
    public Response deleteOrganizationUserById(@PathParam("orgId") String orgId, @PathParam("userId") String userId) {
        User user = userDAO.find(Long.parseLong(userId));
        if (user.getOrg().getId() == Long.parseLong(orgId)) {
            userDAO.delete(user);
            return Response.status(200).entity("OK").build();
        } else {
            return Response.status(405).entity("User not in the organization").build();
        }
    }

    @PUT // OK
    @Path("/{orgId}/users/{userId}")
    @Consumes("application/json")
    public Response updateOrganizationUserById(User user, @PathParam("orgId") String orgId, @PathParam("userId") String userId) {
        user.setId(Long.parseLong(userId));
        user.setOrg(organizationDAO.find(Long.parseLong(orgId)).get(0));
        userDAO.update(user);
        return Response.status(201).entity("UPDATED").build();
    }
    /*=====================================================================*/

    /*=====================================================================*/
    @GET // OK
    @Path("/{orgId}/sensors")
    @Produces("application/json")
    public List<SensorDTO> getOrganizationSensors(@PathParam("orgId") String id) {
        return convertSensorsToSensorsDTO(sensDAO.findByOrg(Long.parseLong(id)));
    }

    @POST // OK
    @Path("/{orgId}/sensors")
    @Consumes("application/json")
    public Response addOrganizationSensor(Sensor sensor, @PathParam("orgId") String id) {
        sensor.setOrganization(organizationDAO.find(Long.parseLong(id)).get(0));
        sensDAO.create(sensor);
        return Response.status(201).entity("CREATED").build();
    }
    /*=====================================================================*/

    /*=====================================================================*/
    @GET // OK
    @Path("/{orgId}/sensors/{sensorId}")
    @Produces("application/json")
    public SensorDTO getOrganizationSensorById(@PathParam("orgId") String orgId, @PathParam("sensorId") String sensorId) {
        return convertSensorsToSensorsDTO(sensDAO.findByOrg(Long.parseLong(orgId), Long.parseLong(sensorId))).get(0);
    }

    @DELETE // OK
    @Path("/{orgId}/sensors/{sensorId}")
    public Response deleteOrganizationSensorById(@PathParam("orgId") String orgId, @PathParam("sensorId") String sensorId) {
        Sensor sensor = sensDAO.find(Long.parseLong(sensorId));
        if (sensor.getOrg().getId() == Long.parseLong(orgId)) {
            sensDAO.delete(sensor);
            return Response.status(200).entity("OK").build();
        } else {
            return Response.status(405).entity("Sensor not in the organization").build();
        }
    }

    @PUT // OK
    @Path("/{orgId}/sensors/{sensorId}")
    @Consumes("application/json")
    public Response updateOrganizationSensorById(Sensor sensor, @PathParam("sensorId") String sensorId, @PathParam("orgId") String orgId) {
        sensor.setId(Long.parseLong(sensorId));
        sensor.setOrganization(organizationDAO.find(Long.parseLong(orgId)).get(0));
        sensDAO.update(sensor);
        return Response.status(201).entity("UPDATED").build();
    }

    @GET // OK
    @Path("/{orgId}/sensors/{sensorId}/measures")
    @Produces("application/json")
    public List<MeasureDTO> getMeasures(@PathParam("orgId") String ordId, @PathParam("sensorId") String sensorId) {
        return convertMeasuresToMeasuresDTO(mesDAO.findMeasureBySensorIdAndOrganizationId(Long.parseLong(sensorId), Long.parseLong(ordId)));
    }

    @GET // OK
    @Path("/{orgId}/sensors/{sensorId}/measures/{measureId}")
    @Produces("application/json")
    public MeasureDTO getMeasureById(@PathParam("orgId") String ordId, @PathParam("sensorId") String sensorId, @PathParam("measureId") String measureId) {
        return convertMeasuresToMeasuresDTO(mesDAO.findMeasureBySensorIdAndOrganizationIdAndMeasureId(Long.parseLong(sensorId), Long.parseLong(ordId), Long.parseLong(measureId))).get(0);
    }
    /*=====================================================================*/

    /*=====================================================================*/
    @GET // OK
    @Path("/{orgId}/sensors/filter/type/{type}")
    @Produces("application/json")
    public List<SensorDTO> getFilteredSensorsByType(@PathParam("orgId") String orgId, @PathParam("type") String type) {
        return convertSensorsToSensorsDTO(sensDAO.findByOrgAndType(Long.parseLong(orgId), type));
    }

    @GET // OK
    @Path("/{orgId}/sensors/filter/public/{isPublic}")
    @Produces("application/json")
    public List<SensorDTO> getFilteredSensorsByPublic(@PathParam("orgId") String orgId, @PathParam("isPublic") String isPublic) {
        return convertSensorsToSensorsDTO(sensDAO.findByOrgAndPublic(Long.parseLong(orgId), isPublic.equals("true")));
    }

    @GET // OK
    @Path("/{orgId}/sensors/filter/type/{type}/public/{isPublic}")
    @Produces("application/json")
    public List<SensorDTO> getFilteredSensorsByTypeAndPublic(@PathParam("orgId") String orgId, @PathParam("type") String type, @PathParam("isPublic") String isPublic) {
        return convertSensorsToSensorsDTO(sensDAO.findByOrgTypeAndPublic(Long.parseLong(orgId), type, isPublic.equals("true")));
    }
    /*=====================================================================*/

    /*===============================================================================================================================*/
    /*===============================================================================================================================*/
    /*===============================================================================================================================*/
    /*===============================================================================================================================*/
    /*===============================================================================================================================*/
    @POST // OK
    @Path("/{orgId}/sensors/{sensorId}/measures")
    @Consumes("application/json")
    public Response addMeasure(Measure measure, @PathParam("sensorId") String sensorId) {
        measure.setSensor(sensDAO.find(Long.parseLong(sensorId)));
        mesDAO.create(measure);
        return Response.status(201).entity("CREATED").build();
    }
    
    
    @GET // OK
    @Path("/{orgId}/facts")
    @Produces("application/json")
    public List<FactDTO> getOrganizationFacts(@PathParam("orgId") String orgId, @QueryParam("type") String type, @QueryParam("public") String isPublic) {
        if (type == null && isPublic == null) {
            // no query param
            return convertFactsToFactsDTO(factDAO.findByOrg(Long.parseLong(orgId)));
        } else if (isPublic == null) {
            // query param : type
            return convertFactsToFactsDTO(factDAO.findByOrgAndType(Long.parseLong(orgId), type));
        } else if (type == null) {
            // query param : isPublic
            return convertFactsToFactsDTO(factDAO.findByOrgAndPublic(Long.parseLong(orgId), isPublic.equals("true")));
        } else {
            // query param : type && isPublic
            return convertFactsToFactsDTO(factDAO.findByOrgTypeAndPublic(Long.parseLong(orgId), type, isPublic.equals("true")));
        }
    }

//    @POST // OK
//    @Path("/{orgId}/facts")
//    @Consumes("application/json")
//    public Response addOrganizationFact(Fact fact, @PathParam("orgId") String id) {
//        fact.setOrg(organizationDAO.find(Long.parseLong(id)).get(0));
//        factDAO.create(fact);
//        return Response.status(201).entity("CREATED").build();
//    }
    @GET // OK
    @Path("/{orgId}/facts/{factId}")
    @Produces("application/json")
    public FactDTO getOrganizationFactById(@PathParam("orgId") String orgId, @PathParam("factId") String factId) {
        return convertFactsToFactsDTO(factDAO.findByOrg(Long.parseLong(orgId), Long.parseLong(factId))).get(0);
    }

//    @GET // OK
//    @Path("/{orgId}/facts/filter/type/{type}")
//    @Produces("application/json")
//    public List<FactDTO> getFilteredFactsByType(@PathParam("orgId") String orgId, @PathParam("type") String type) {
//        return convertFactsToFactsDTO(factDAO.findByOrgAndType(Long.parseLong(orgId), type));
//    }
//
//    @GET // OK
//    @Path("/{orgId}/facts/filter/public/{isPublic}")
//    @Produces("application/json")
//    public List<FactDTO> getFilteredFactsByPublic(@PathParam("orgId") String orgId, @PathParam("isPublic") String isPublic) {
//        return convertFactsToFactsDTO(factDAO.findByOrgAndPublic(Long.parseLong(orgId), isPublic.equals("true")));
//    }
//
//    @GET // OK
//    @Path("/{orgId}/facts/filter/type/{type}/public/{isPublic}")
//    @Produces("application/json")
//    public List<FactDTO> getFilteredFactsByTypeAndPublic(@PathParam("orgId") String orgId, @PathParam("type") String type, @PathParam("isPublic") String isPublic) {
//        return convertFactsToFactsDTO(factDAO.findByOrgTypeAndPublic(Long.parseLong(orgId), type, isPublic.equals("true")));
//    }
    /*===============================================================================================================================*/
    /*===============================================================================================================================*/
    /*===============================================================================================================================*/
    /*===============================================================================================================================*/
    /*===============================================================================================================================*/
    private List<OrganizationDTO> convertOrgnanizationsToOrganizationsDTO(List<Organization> list) {
        List<OrganizationDTO> result = new LinkedList<>();
        for (Organization o : list) {
            OrganizationDTO dto = new OrganizationDTO();
            dto.setId(o.getId());
            dto.setName(o.getName());
            dto.setDescription(o.getDescription());
            result.add(dto);
        }
        return result;
    }

    private List<UserDTO> convertUsersToUsersDTO(List<User> list) {
        List<UserDTO> result = new LinkedList<>();
        for (User u : list) {
            UserDTO dto = new UserDTO();
            dto.setFirstName(u.getFirstName());
            dto.setLastName(u.getLastName());
            dto.setEmail(u.getEmail());
            dto.setId(u.getId());
            result.add(dto);
        }
        return result;
    }

    private List<SensorDTO> convertSensorsToSensorsDTO(List<Sensor> list) {
        List<SensorDTO> result = new LinkedList<>();
        for (Sensor s : list) {
            SensorDTO dto = new SensorDTO();
            dto.setName(s.getName());
            dto.setType(s.getType());
            dto.setDescription(s.getDescription());
            dto.setIsPublic(s.isIsPublic());
            dto.setId(s.getId());
            result.add(dto);
        }
        return result;
    }

    private List<FactDTO> convertFactsToFactsDTO(List<Fact> list) {
        List<FactDTO> result = new LinkedList<>();
        for (Fact f : list) {
            FactDTO dto = new FactDTO();
            dto.setType(f.getType());
            dto.setDescription(f.getDescription());
            dto.setIsPublic(f.isIsPublic());
            dto.setId(f.getId());
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
