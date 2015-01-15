/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.test;

import ch.heigvd.amt.model.Sensor;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author bischof
 */
public class Tester {

    ExecutorService executor = Executors.newFixedThreadPool(10);

    public void test() {

        Client client = ClientBuilder.newClient().register(JacksonFeature.class);
        final WebTarget sensorsTarget = client.target("http://localhost:8080/api_JAVA/api").path("sensors");
        final WebTarget factTarget = client.target("http://localhost:8080/api_JAVA/api").path("facts");
        
        
        Response response = sensorsTarget.request().get();
        LinkedList<Sensor> sensors = response.readEntity(LinkedList.class);
        
        for(Sensor s : sensors) {
            System.out.println("Sensor : " + s.getName());
        }
        
        // Response response = target.request().post(Entity.json(new TransactionDTO(accountId, 1)));

    }

    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.test();
    }
}
