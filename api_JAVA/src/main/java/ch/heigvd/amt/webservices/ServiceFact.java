/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : ServiceFact.java
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
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Service fact
 *****************************************************************************************
 */
package ch.heigvd.amt.webservices;

import ch.heigvd.amt.dao.DAOFactLocal;
import ch.heigvd.amt.dto.FactPublicDTO;
import ch.heigvd.amt.model.Fact;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/facts")
@Stateless
public class ServiceFact {

    @EJB
    DAOFactLocal factDAO;

    @GET // OK
    @Produces("application/json")
    public List<FactPublicDTO> getFacts(@QueryParam("orgId") String orgId, @QueryParam("type") String type) {
        if (orgId == null && type == null) {
            // no query param
            return convertFactsToFactsDTO(factDAO.findPublic());
        } else if (type == null) {
            // query param : orgId
            return convertFactsToFactsDTO(factDAO.findPublicByOrganization(Long.parseLong(orgId)));
        } else if (orgId == null) {
            // query param : type
            return convertFactsToFactsDTO(factDAO.findPublicByType(type));
        } else {
            // query param : orgId && type
            return convertFactsToFactsDTO(factDAO.findPublicByTypeAndOrganization(type, Long.parseLong(orgId)));
        }
    }

    @GET // OK
    @Path("/{factId}")
    @Produces("application/json")
    public FactPublicDTO getFactById(@PathParam("factId") String factId) {
        return convertFactsToFactsDTO(factDAO.findPublicById(Long.parseLong(factId))).get(0);
    }

//    @GET // OK
//    @Path("/filter/type/{type}")
//    @Produces("application/json")
//    public List<FactPublicDTO> getFilteredFactsByType(@PathParam("type") String type) {
//        return convertFactsToFactsDTO(factDAO.findPublicByType(type));
//    }
//
//    @GET // OK
//    @Path("/filter/organization/{orgId}")
//    @Produces("application/json")
//    public List<FactPublicDTO> getFilteredFactsByOrganization(@PathParam("orgId") String orgId) {
//        return convertFactsToFactsDTO(factDAO.findPublicByOrganization(Long.parseLong(orgId)));
//    }
//
//    @GET // OK
//    @Path("/filter/type/{type}/organization/{orgId}")
//    @Produces("application/json")
//    public List<FactPublicDTO> getFilteredFactsByTypeAndOrganization(@PathParam("type") String type, @PathParam("orgId") String orgId) {
//        return convertFactsToFactsDTO(factDAO.findPublicByTypeAndOrganization(type, Long.parseLong(orgId)));
//    }

    private List<FactPublicDTO> convertFactsToFactsDTO(List<Fact> list) {
        List<FactPublicDTO> result = new LinkedList<>();
        for (Fact f : list) {
            FactPublicDTO dto = new FactPublicDTO();
            dto.setKey(f.getKey());
            result.add(dto);
        }
        return result;
    }
}
