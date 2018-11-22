package com.gupao.web.tomcat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) throws IOException {
        List<Client> clientList = new ArrayList<Client>();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        final Accptor accptor = new Accptor();

        try {
            for (int i = 0; i < 3; i++) {
                final Client client = new Client("Client" + i);
    //            clientList.add(new Client("Client" + i));
                executorService.submit(new Runnable() {
                    public void run() {
                        client.send(accptor);
                    }
                });
            }
        } finally {
            System.in.read();
            executorService.shutdown();
        }

    }
}