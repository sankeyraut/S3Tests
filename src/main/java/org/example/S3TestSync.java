package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ChecksumAlgorithm;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class S3TestSync {
    public static void main(String[] args) throws  InterruptedException, URISyntaxException {


        ObjectMapper mapper = new ObjectMapper();
        URI myURI = new URI("https://s3express-usw2-az1.us-west-2.amazonaws.com");

        S3Client client = S3Client.builder()
                .region(Region.US_WEST_2)
                .endpointOverride(myURI)
                .build();

        HeadBucketRequest headRequest = HeadBucketRequest.builder().bucket(Constants.BUCKET_NAME).build();
        client.headBucket(headRequest);
        System.out.println("Connected to " + Constants.BUCKET_NAME);

        class S3Write implements Runnable {
            final String key;
            S3Write(String s) { key = s; }
            public void run() {
                ArrayList<Team> teamList = getTeams();
                //writeToS3(basePath, s3, mapper, teamList);
                File teamsFile = new File(key + "-teams.json");
                String stringJson ;
                try {
                    stringJson = mapper.writeValueAsString(teamList);
                    mapper.writeValue(teamsFile, stringJson);
                    PutObjectRequest objectRequest = PutObjectRequest.builder()
                            .bucket(Constants.BUCKET_NAME)
                            .key(key)
                            .checksumAlgorithm(ChecksumAlgorithm.CRC32)
                            .build();
                    long start = System.nanoTime();
                    client.putObject(objectRequest,teamsFile.toPath());
                    long end = System.nanoTime();
                    System.out.println("Thread " + Thread.currentThread().getName() + " completed in "+ (end-start)/1000000 +" milliseconds");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            private ArrayList<Team> getTeams() {
                ArrayList<Team> teamList = new ArrayList<>();
                Team team = new Team();
                team.setPlayer1(1);
                team.setPlayer2(2);
                team.setPlayer3(3);
                team.setPlayer4(4);
                team.setPlayer5(5);
                team.setPlayer6(6);
                team.setPlayer7(7);
                team.setPlayer8(8);
                team.setPlayer9(9);
                team.setPlayer10(10);
                team.setPlayer11(11);
                team.setUserId(key);
                teamList.add(team);
                return teamList;
            }
        }

        Thread.Builder builder = Thread.ofVirtual().name("S3 Write", 0);

        Thread thread1 ;
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i=0;i<50;i++){
            thread1 = builder.start(new S3Write(i+""));
            threads.add(thread1);

        }
        for (Thread thread : threads) {
            thread.join();
        }


    }



}