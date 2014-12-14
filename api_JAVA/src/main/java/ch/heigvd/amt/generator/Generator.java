/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.generator;

import ch.heigvd.amt.dao.DAOFactLocal;
import ch.heigvd.amt.dao.DAOMeasureLocal;
import ch.heigvd.amt.dao.DAOOrganizationLocal;
import ch.heigvd.amt.dao.DAOSensorLocal;
import ch.heigvd.amt.dao.DAOUserLocal;
import ch.heigvd.amt.model.Fact;
import ch.heigvd.amt.model.Measure;
import ch.heigvd.amt.model.Organization;
import ch.heigvd.amt.model.Sensor;
import ch.heigvd.amt.model.User;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author bischof
 */
@Stateless
public class Generator implements IGenerator {
    
    @EJB
    DAOOrganizationLocal orgManager;
    @EJB
    DAOUserLocal userManager;
    @EJB
    DAOSensorLocal sensManager;
    @EJB
    DAOMeasureLocal measManager;
    @EJB
    DAOFactLocal factManager;
    
    private final Random r = new Random();

    // Constants
    private final static String[] organizations = {"HEIG-VD", "HES-SO", "EPFL"};
    private final static String[] maleFirstNames = {"butters", "stan", "kenny", "eric", "homer", "kyle"};
    private final static String[] femaleFirstNames = {"julie", "sarah", "marge", "delphine", "celine", "chloe"};
    private final static String[] lastNames = {"smith", "haineken", "samsung", "doe", "simpson", "marsh", "mccormick", "cartman", "broflovski", "stotch"};
    private final static String[] sensorsType = {"Alarm sensor", "Barometer", "Biosensor", "Calorimeter",
        "Pressure sensor", "Proximity sensor", "Quantum sensor", "Rain sensor", "Sonar", "Speed sensor", "Thermometer"};
    
    private final static int NUMBER_OF_USERS_PER_ORGANIZATION = 10;
    private final static int NUMBER_OF_SENSORS_PER_ORGANIZATION = 200;
    private final static int NUMBER_OF_MEASURES_PER_SENSORS = 10;
    private final static int NUMBER_OF_MEASURES_PER_FACTS = 100;
    
    private final static Map<Sensor, List<Measure>> sensorMeasures = new HashMap<>();
    
    @Override
    public void generate() {
        for (int i = 0; i < organizations.length; ++i) {
            Organization o = new Organization(organizations[i], "");
            orgManager.create(o);
            // Generating users
            for (int j = 0; j < NUMBER_OF_USERS_PER_ORGANIZATION; ++j) {
                String firstName = r.nextBoolean() ? maleFirstNames[r.nextInt(maleFirstNames.length)] : femaleFirstNames[r.nextInt(femaleFirstNames.length)];
                String lastName = lastNames[r.nextInt(lastNames.length)];
                String email = firstName + "." + lastName + "@" + organizations[i] + ".ch";
                User u = new User(lastName, firstName, email, o, "");
                userManager.create(u);
                if (j == 0) {
                    o.setContact(u);
                    orgManager.update(o);
                }
            }
            // Generating sensors
            List<Sensor> sensorsList = new LinkedList<>();
            for (int j = 0; j < NUMBER_OF_SENSORS_PER_ORGANIZATION; ++j) {
                Sensor s = generateSensor();
                List<Measure> measuresList = new LinkedList<>();
                s.setOrganization(o);
                sensManager.create(s);
                // Generating measures
                for (int k = 0; k < NUMBER_OF_MEASURES_PER_SENSORS; ++k) {
                    Measure m = new Measure(r.nextDouble(), System.currentTimeMillis() - r.nextInt(999999), s);
                    measManager.create(m);
                    measuresList.add(m);
                }
                sensorsList.add(s);
                sensorMeasures.put(s, measuresList);
            }
            // Generating facts
            Map<String, List<Sensor>> typeSensors = new HashMap<>();
            for (Sensor s : sensorsList) {
                // Sorting sensors by type
                List<Sensor> list;
                if (typeSensors.get(s.getType()) == null) {
                    list = new LinkedList<>();
                } else {
                    list = typeSensors.get(s.getType());
                }
                list.add(s);
                typeSensors.put(s.getType(), list);
            }
            Set<String> keysType = typeSensors.keySet();
            int counter = 0;
            double minValue = 0;
            double maxValue = 0;
            double averageValue = 0;
            for (String type : keysType) {
                List<Sensor> list = typeSensors.get(type);
                for (Sensor sensor : list) {
                    List<Measure> measures = sensorMeasures.get(sensor);
                    for (Measure m : measures) {
                        if (minValue > m.getValue()) {
                            minValue = m.getValue();
                        }
                        if (maxValue < m.getValue()) {
                            maxValue = m.getValue();
                        }
                        averageValue += m.getValue();
                        ++counter;
                        if (counter == NUMBER_OF_MEASURES_PER_FACTS || counter == measures.size()) {
                            averageValue /= counter;
                            String description = "minValue : " + minValue + ", averageValue : " + averageValue + ", maxValue : " + maxValue;
                            Fact f = new Fact(type, description, sensor.isIsPublic(), o);
                            factManager.create(f);
                            minValue = 0;
                            maxValue = 0;
                            averageValue = 0;
                            counter = 0;
                        }
                    }
                }
            }
        }
    }
    
    private Sensor generateSensor() {
        Sensor s = new Sensor();
        int i = r.nextInt(sensorsType.length);
        s.setName(sensorsType[i] + " #" + r.nextInt(99999));
        s.setType(sensorsType[i]);
        s.setDescription("");
        s.setIsPublic(r.nextBoolean());
        return s;
    }
}
