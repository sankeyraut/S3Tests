package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ChecksumAlgorithm;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class S3TestSync {
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        int roundId = 12345;

        ObjectMapper mapper = new ObjectMapper();
        URI myURI = new URI("https://s3express-usw2-az1.us-west-2.amazonaws.com");

        S3Client client = S3Client.builder()
                .region(Region.US_WEST_2)
                .endpointOverride(myURI)
                .build();

        class S3Write implements Runnable {
            String key;
            S3Write(String s) { key = s; }
            public void run() {
                ArrayList<Team> teamList = new ArrayList<Team>();
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
                teams1.setUserId(key.toString());
                teamList.add(teams1);
                //writeToS3(basePath, s3, mapper, teamList);
                File teamsFile = new File(key + "-teams.json");
                String stringJson = null;
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

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        Thread.Builder builder = Thread.ofVirtual().name("S3 Write", 0);

        Thread thread1 = null;
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