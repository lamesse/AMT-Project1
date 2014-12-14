package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author bischof
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ch.heigvd.amt.webservices.ServiceFact.class);
        resources.add(ch.heigvd.amt.webservices.ServiceGenerator.class);
        resources.add(ch.heigvd.amt.webservices.ServiceOrganization.class);
        resources.add(ch.heigvd.amt.webservices.ServiceSensor.class);
    }
    
}
