/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.test;

import ch.heigvd.amt.generator.Generator;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.json.JSONObject;

/**
 *
 * @author bischof
 */
public class Tester {

    private static final Logger LOG = Logger.getLogger(Tester.class.getName());

    private static final String LOCALHOST = "http://localhost:8080/api_JAVA/api";
    private static final String PATH = "organizations/1/sensors/";
    private static final String MEASURE = "/measures";
    private static final int NUMBER_OF_THREAD = Generator.NUMBER_OF_SENSORS_PER_TYPE * 2;
    private static final int FIRST_SENSOR_ID = 3;
    private static final int NUMBER_OF_MEASURES = 100;
    private static final Random RAND = new Random();
    private static final long ONE_DAY = 86400000; // ms
    private static final int MAX_RANGE = 1000;
    private static final String KEY_TYPE = "sensorType";
    private static final String KEY_VALUE = "value";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final Map<Integer, String> sensorType = new HashMap<>();

    {
        for (int i = FIRST_SENSOR_ID; i < NUMBER_OF_THREAD + FIRST_SENSOR_ID; ++i) {
            sensorType.put(i, Generator.sensorType[(i - FIRST_SENSOR_ID) / Generator.NUMBER_OF_SENSORS_PER_TYPE]);
        }
    }

    private final Buffer<JSONObject> buffer = new Buffer<>();

    public void test() {
        new Worker().start();
        for (int i = FIRST_SENSOR_ID; i < NUMBER_OF_THREAD + FIRST_SENSOR_ID; ++i) {
            new TestWorker(i).start();
        }
    }

    private class TestWorker extends Thread {

        private final Client client = ClientBuilder.newClient().register(JacksonFeature.class);
        private final WebTarget target;
        private final int sensorId;

        private TestWorker(final int sensorId) {
            this.sensorId = sensorId;
            target = client.target(LOCALHOST).path(PATH + sensorId + MEASURE);
        }

        @Override
        public void run() {
            for (int i = 0; i < NUMBER_OF_MEASURES; ++i) {
                JSONObject json = new JSONObject();
                json.put(KEY_TYPE, sensorType.get(sensorId));
                json.put(KEY_VALUE, RAND.nextInt(MAX_RANGE));
                json.put(KEY_TIMESTAMP, System.currentTimeMillis() - (RAND.nextInt(7) * ONE_DAY));
                target.request().post(Entity.json(json.toString()));
                buffer.put(json);
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

        private final Map<String, Map<String, LinkedList<Integer>>> map = new HashMap<>();

        @Override
        public void run() {
            int i = 0;
            while (i < NUMBER_OF_THREAD * NUMBER_OF_MEASURES) {
                try {
                    JSONObject json = buffer.get();
                    Map<String, LinkedList<Integer>> tmp = map.get(json.getString(KEY_TYPE));
                    if (tmp == null) {
                        tmp = new HashMap<>();
                    }
                    LinkedList<Integer> list = tmp.get(json.getString(KEY_TIMESTAMP));
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(json.getInt(KEY_VALUE));
                    tmp.put(KEY_TIMESTAMP, list);
                    map.put(KEY_TYPE, tmp);
                } catch (InterruptedException e) {

                }
                ++i;
            }
            Set<String> keys = map.keySet();
            for (String s : keys) {
                Set<String> timeKeys = map.get(s).keySet();
                for (String ts : timeKeys) {
                    int min = 0;
                    int avg = 0;
                    int max = 0;
                    int counter = 0;
                    for (Integer num : map.get(s).get(ts)) {
                        ++counter;
                        if (min > num) {
                            min = num;
                        }
                        if (max < num) {
                            max = num;
                        }
                        avg += num;
                    }
                    prettyPrint(ts, min, max, avg, counter);
                }
            }
        }
    }
    
    private void prettyPrint(String timestamp, int min, int max, int avg, int counter) {
        System.out.print(new Date(Long.parseLong(timestamp)) + " : { min : " + min + ", ");
        System.out.print( "max : " + max + ", avg : " + (avg / counter) + ", counter : " + counter + " }");
        System.out.println("\n");
    }

    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.test();
    }
}
