package ch.heigvd.amt.test;

import ch.heigvd.amt.dto.FactDTO;
import ch.heigvd.amt.generator.Generator;
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
 *
 * @author Jonathan Bischof
 * @author Antoine Messerli
 */
public class Tester {

    private static final Logger LOG = Logger.getLogger(Tester.class.getName());

    private static final String LOCALHOST = "http://localhost:8080/api_JAVA/api";
    private static final String PATH = "organizations/1/sensors/";
    private static final String PATH_GET_FACT = "organizations/1/facts/";
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
    private static final String KEY_STATUS_CODE = "status";
    private static final String KEY_SENSOR_ID = "sensorId";
    private static final Map<Integer, String> sensorType = new HashMap<>();

    private static final Long ts = System.currentTimeMillis();

    static {
        for (int i = FIRST_SENSOR_ID; i < NUMBER_OF_THREAD + FIRST_SENSOR_ID; ++i) {
            sensorType.put(i, Generator.sensorType[(i - FIRST_SENSOR_ID) / Generator.NUMBER_OF_SENSORS_PER_TYPE]);
        }
    }

    private final Buffer<JSONObject> bufferDaily = new Buffer<>();
    private final Buffer<JSONObject> bufferCounter = new Buffer<>();
    private final Client client = ClientBuilder.newClient().register(JacksonFeature.class);
    private final WorkerDaily wd = new WorkerDaily();
    private final WorkerCounter wc = new WorkerCounter();

    public void test() {
        wd.start();
        wc.start();
        for (int i = FIRST_SENSOR_ID; i < NUMBER_OF_THREAD + FIRST_SENSOR_ID; ++i) {
            new TestWorker(i).start();
        }
    }

    private class TestWorker extends Thread {

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
                json.put(KEY_TIMESTAMP, ts - (RAND.nextInt(7) * ONE_DAY));
                Response r = target.request().post(Entity.json(json.toString()));
                json.put(KEY_STATUS_CODE, r.getStatus());
                bufferDaily.put(json);
                JSONObject counter = new JSONObject();
                counter.put(KEY_SENSOR_ID, sensorId);
                bufferCounter.put(counter);
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

    private class WorkerDaily extends Thread {

        private final Map<String, Map<Long, LinkedList<JSONObject>>> map = new HashMap<>();

        @Override
        public void run() {
            int i = 0;
            while (i < NUMBER_OF_THREAD * NUMBER_OF_MEASURES) {
                try {
                    JSONObject json = bufferDaily.get();
                    Map<Long, LinkedList<JSONObject>> tmp = map.get(json.getString(KEY_TYPE));
                    if (tmp == null) {
                        tmp = new HashMap<>();
                    }
                    LinkedList<JSONObject> list = tmp.get(json.getLong(KEY_TIMESTAMP));
                    if (list == null) {
                        list = new LinkedList<>();
                    }
                    list.add(json);
                    tmp.put(json.getLong(KEY_TIMESTAMP), list);
                    map.put(json.getString(KEY_TYPE), tmp);
                } catch (InterruptedException e) {
                    LOG.log(Level.SEVERE, e.getMessage());
                }
                ++i;
            }
            Set<String> keys = map.keySet();
            for (String s : keys) {
                Set<Long> timeKeys = map.get(s).keySet();
                for (Long ts : timeKeys) {
                    int min = 0;
                    int avg = 0;
                    int max = 0;
                    int counter = 0;
                    for (JSONObject obj : map.get(s).get(ts)) {
                        ++counter;
                        int num = obj.getInt(KEY_VALUE);
                        if (min > num) {
                            min = num;
                        }
                        if (max < num) {
                            max = num;
                        }
                        avg += num;
                        long status = obj.getLong(KEY_STATUS_CODE);
                        if (status != 201) {
                            System.out.println("Status code : " + obj.getLong(KEY_STATUS_CODE));
                        }
                    }
                    prettyPrint(ts, min, max, avg, counter);
                }
            }
        }
    }

    private synchronized void prettyPrint(Long timestamp, int min, int max, int avg, int counter) {
        System.out.print(new Date(timestamp) + " : { min : " + min + ", ");
        System.out.print("max : " + max + ", avg : " + (avg / counter) + ", counter : " + counter + " }");
        System.out.println("\n");
    }

    private class WorkerCounter extends Thread {

        private final Map<Integer, Integer> map = new HashMap<>();

        @Override
        public void run() {
            int i = 0;
            while (i < NUMBER_OF_THREAD * NUMBER_OF_MEASURES) {
                try {
                    JSONObject json = bufferCounter.get();
                    int sensId = json.getInt(KEY_SENSOR_ID);
                    int count;
                    if (map.containsKey(sensId)) {
                        count = map.get(sensId);
                    } else {
                        count = 0;
                    }
                    ++count;
                    map.put(sensId, count);
                } catch (InterruptedException e) {
                    LOG.log(Level.SEVERE, e.getMessage());
                }
                ++i;
            }
            try {
                wd.join();
            } catch (InterruptedException e) {
                LOG.log(Level.SEVERE, e.getMessage());
            } finally {
                prettyPrint(map);
            }
        }
    }

    private synchronized void prettyPrint(Map<Integer, Integer> map) {
        Set<Integer> keys = map.keySet();
        for (Integer i : keys) {
            System.out.println("Sensor " + i + " has " + map.get(i) + " measures.");
        }
    }

    public static void main(String[] args) {
        new Tester().test();
    }
}
