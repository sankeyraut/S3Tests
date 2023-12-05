package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.endpoints.S3EndpointProvider;
import software.amazon.awssdk.services.s3.model.ChecksumAlgorithm;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
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

public class S3Test {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int roundId = 12345;

        ObjectMapper mapper = new ObjectMapper();

        SdkAsyncHttpClient httpClient = NettyNioAsyncHttpClient.builder()
                .connectionTimeout(Duration.ofSeconds(20))
                .connectionAcquisitionTimeout(Duration.ofSeconds(20))
                .connectionMaxIdleTime(Duration.ofSeconds(5))
                .build();
        URI myURI = new URI("https://s3express-usw2-az1.us-west-2.amazonaws.com");

        S3AsyncClient client = S3AsyncClient.builder()
                .region(Region.US_WEST_2)
                .endpointOverride(myURI)
                .httpClient(httpClient)
                .build();



        try {
            IntStream.range(0, 10000)
                    .boxed()
                    .parallel()
                    .forEach(
                            x -> {
                                UUID key = UUID.randomUUID();
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
                                File teamsFile = new File(x + "-teams.json");
                                try {
                                    StringBuilder sb = new StringBuilder(x.toString());
                                    String objectKey = sb.reverse().toString();
                                    String stringJson = mapper.writeValueAsString(teamList);
                                    mapper.writeValue(teamsFile, stringJson);
                                    PutObjectRequest objectRequest = PutObjectRequest.builder()
                                            .bucket(Constants.BUCKET_NAME)
                                            .key(objectKey)
                                            .checksumAlgorithm(ChecksumAlgorithm.CRC32)
                                            .build();

                                    CompletableFuture<PutObjectResponse> future = client.putObject(objectRequest,
                                            AsyncRequestBody.fromFile(Paths.get(teamsFile.getAbsolutePath()))
                                    );
                                    future.whenComplete((resp, err) -> {
                                        try {
                                            if (resp != null) {
                                                System.out.println("Object uploaded. Details: " + resp);
                                            } else {
                                                // Handle error
                                                err.printStackTrace();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    future.join();

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
        }
        finally {
            client.close();
        }

    }



}