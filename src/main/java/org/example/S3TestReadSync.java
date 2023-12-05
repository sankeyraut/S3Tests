package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ChecksumAlgorithm;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

public class S3TestReadSync {
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        int roundId = 12345;

        ObjectMapper mapper = new ObjectMapper();
        URI myURI = new URI("https://s3express-usw2-az1.us-west-2.amazonaws.com");

        S3Client client = S3Client.builder()
                .region(Region.US_WEST_2)
                .endpointOverride(myURI)
                .build();


        Thread.Builder builder = Thread.ofVirtual().name("S3 Read", 0);
        class S3Read implements Runnable {
            String key;
            S3Read(String s) { key = s; }
            public void run() {

                File teamsFile = new File(key + "-teams.json");
                GetObjectRequest objectRequest = GetObjectRequest.builder()
                        .bucket(Constants.BUCKET_NAME)
                        .key(key)
                        .build();
                long start = System.nanoTime();
                client.getObject(objectRequest,teamsFile.toPath());
                long end = System.nanoTime();
                System.out.println("Thread " + Thread.currentThread().getName() + " completed in "+ (end-start)/1000000+" milliseconds");

            }
        }
        Thread thread1 = null;
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i=0;i<50;i++){
            thread1 = builder.start(new S3Read(i+""));
            threads.add(thread1);

        }
        for (Thread thread : threads) {
            thread.join();
        }


    }



}