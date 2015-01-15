/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : ServiceGenerator.java
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
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Service generator
 *****************************************************************************************
 */
package ch.heigvd.amt.webservices;

import ch.heigvd.amt.generator.IGenerator;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/generator")
@Stateless
public class ServiceGenerator {
    
    @EJB
    IGenerator generator;
    
    @GET
    public Response getFacts() {
//        generator.generate();
        generator.generateTest();
        return Response.status(200).entity("Generating test data...").build();
    }
    
}
