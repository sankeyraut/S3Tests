package org.example;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.File;

class S3Read implements Runnable {
    final String key;
    final S3Client client ;
    S3Read(String s, S3Client client) { key = s; this.client = client; }
    public void run() {

        File teamsFile = new File(key + "-teams.json");
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(Constants.BUCKET_NAME)
                .key(key)
                .build();
        long start = System.nanoTime();
        this.client.getObject(objectRequest,teamsFile.toPath());
        long end = System.nanoTime();
        System.out.println("Thread " + Thread.currentThread().getName() + " completed in "+ (end-start)/1000000+" milliseconds");
        teamsFile.delete();


    }
}
