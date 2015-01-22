package ch.heigvd.amt.test;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.json.JSONObject;

/**
 * @author Jonathan Bischof <jonathan.bischof@heig-vd.ch>
 * @author Rui Reis         <rui.reis@heig-vd.ch>
 */
public class Tester2 {

    private static final Logger LOG = Logger.getLogger(Tester2.class.getName());

    private static final String LOCALHOST = "http://localhost:8080/AMTProject1-1.0-SNAPSHOT/api/";
    private static final String PATH = "observations/";

    private static final int FIRST_SENSOR_ID = 3;
    private static final int NUMBER_OF_SENSORS = 10;
    private static final int NUMBER_OF_OBSERVATIONS = 100;

    private static final Random rand = new Random();

    private static final String KEY_SENSOR_ID = "sensorValue";
    private static final String KEY_VALUE = "value";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_RESPONSE = "response";

    private static final Long ts = System.currentTimeMillis();
    private static final long MS_FOR_ONE_DAY = 86400000;

    private final Client client = ClientBuilder.newClient().register(JacksonFeature.class);

    private final Buffer<JSONObject> counterBuffer = new Buffer<>();

    public void test() {
        new Worker().start();
        for (int i = FIRST_SENSOR_ID; i < NUMBER_OF_SENSORS + FIRST_SENSOR_ID; ++i) {
            new TestWorker(i).start();
        }
    }

    private class TestWorker extends Thread {

        private final WebTarget target;
        private final int sensorId;

        private TestWorker(int sensorId) {
            this.sensorId = sensorId;
            this.target = client.target(LOCALHOST).path(PATH);
        }

        @Override
        public void run() {

            for (int i = 0; i < NUMBER_OF_OBSERVATIONS; ++i) {
                JSONObject obj = new JSONObject();
                obj.put(KEY_SENSOR_ID, sensorId);
                obj.put(KEY_VALUE, rand.nextInt(1000));
                obj.put(KEY_TIMESTAMP, ts - (rand.nextInt(7) * MS_FOR_ONE_DAY));
                Response r = target.request().post(Entity.json(obj.toString()));
                obj.put(KEY_RESPONSE, r.getStatus());
                counterBuffer.put(obj);
            }
        }
    }

    private class Buffer<E> {

        private final LinkedList<E> list = new LinkedList<>();

        public synchronized void put(E e) {
            list.addLast(e);
            notifyAll();
        }

        public synchronized E get() throws InterruptedException {
            while (list.isEmpty()) {
                wait();
            }
            return list.removeFirst();
        }
    }

    private class Worker extends Thread {

        private final Map<Integer, Integer> counterMap = new HashMap<>();
        private final Map<Date, LinkedList<JSONObject>> dailyMap = new HashMap<>();

        @Override
        public void run() {
            int i = 0;
            while (i < NUMBER_OF_SENSORS * NUMBER_OF_OBSERVATIONS) {
                try {
                    JSONObject obj = counterBuffer.get();
                    if (obj.getInt(KEY_RESPONSE) != 201) {
                        LOG.log(Level.SEVERE, "Status code : {0}", obj.getInt(KEY_RESPONSE));
                    }
                    counterMap.put(obj.getInt(KEY_SENSOR_ID), counterMap.get(obj.getInt(KEY_SENSOR_ID)) + 1);

                    Date d = new Date(obj.getLong(KEY_TIMESTAMP));
                    LinkedList<JSONObject> list = dailyMap.get(d);
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(obj);
                    dailyMap.put(d, list);

                } catch (InterruptedException e) {
                    LOG.log(Level.SEVERE, e.getMessage());
                }

                ++i;
            }
            printCounterFact();
            printDailyFact();
        }

        private void printCounterFact() {
            Set<Integer> keys = counterMap.keySet();
            for (Integer i : keys) {
                System.out.println("Sensors id (" + i + ") - #Observations (" + counterMap.get(i) + ")");
            }
        }

        private void printDailyFact() {
            Set<Date> keys = dailyMap.keySet();
            for (Date d : keys) {
                LinkedList<JSONObject> list = dailyMap.get(d);
                double min = -1;
                double max = Double.MAX_VALUE;
                double sum = 0;
                for (JSONObject obj : list) {
                    double value = obj.getDouble(KEY_VALUE);
                    if (min > value) {
                        min = value;
                    }

                    if (max < value) {
                        max = value;
                    }

                    sum += value;
                }
                double avg = sum / list.size();
                System.out.print(d + " : { min : " + min + ", max : " + max + ", ");
                System.out.print("sum : " + sum + ", avg : " + avg + ", counter : " + list.size() + " }");
                System.out.println("\n");
            }
        }
    }

    public static void main(String... args) {
        new Tester2().test();
    }
}
