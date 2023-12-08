package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class S3Write implements Runnable {
    final String key;
    final S3Client client ;

    ObjectMapper mapper = new ObjectMapper();
    S3Write(String s, S3Client client) { key = s; this.client = client; }
    public void run() {

        File teamsFile = new File(key + "-teams.json");
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(Constants.BUCKET_NAME)
                .key(key)
                .build();

        //--------
        ArrayList<Team> teamList = new ArrayList<>();
        Team teams1 = new Team();
        teams1.setPlayer1(1);
        teams1.setPlayer2(2);
        teams1.setPlayer3(3);
        teams1.setPlayer4(4);
        teams1.setPlayer5(5);
        teams1.setPlayer6(6);
        teams1.setPlayer7(7);
        teams1.setPlayer8(8);
        teams1.setPlayer9(9);
        teams1.setPlayer10(10);
        teams1.setPlayer11(11);
        teams1.setUserId(key);
        teamList.add(teams1);


        try {
            String stringJson = mapper.writeValueAsString(teamList);
            mapper.writeValue(teamsFile, stringJson);
            long start = System.nanoTime();
            this.client.putObject(objectRequest,teamsFile.toPath());
            long end = System.nanoTime();
            System.out.println("Thread " + Thread.currentThread().getName() + " completed in "+ (end-start)/1000000+" milliseconds");
            teamsFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //--------



    }
}
