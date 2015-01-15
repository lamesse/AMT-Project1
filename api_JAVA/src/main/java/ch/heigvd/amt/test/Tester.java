/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.test;

import java.sql.Date;
import java.util.LinkedList;
import java.util.Random;
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
    private static final int NUMBER_OF_THREAD = 5;
    private static final int NUMBER_OF_MEASURES = 20;
    private static final Random RAND = new Random();
    private static final long ONE_DAY = 86400000; // ms

    private final Buffer<JSONObject> buffer = new Buffer<>();

    public void test() {
        new Worker().start();
        for (int i = 0; i < NUMBER_OF_THREAD; ++i) {
            new TestWorker(PATH + (i + 3) + MEASURE).start();
        }

    }

    private class TestWorker extends Thread {

        private final Client client = ClientBuilder.newClient().register(JacksonFeature.class);
        private final WebTarget target;

        private TestWorker(final String path) {
            target = client.target(LOCALHOST).path(path);
        }

        @Override
        public void run() {
            for (int i = 0; i < NUMBER_OF_MEASURES; ++i) {
                JSONObject json = new JSONObject();
                json.put("value", RAND.nextInt(1000));
                json.put("timestamp", System.currentTimeMillis() - (RAND.nextInt(7) * ONE_DAY));
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

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    JSONObject json = buffer.get();
                    System.out.print(json.toString());
                    System.out.println("\t" + new Date(json.getLong("timestamp")));
                } catch (InterruptedException e) {

                }
            }
        }
    }

    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.test();
    }
}
