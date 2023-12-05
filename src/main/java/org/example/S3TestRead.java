package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class S3TestRead {
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
                .httpClient(httpClient)
                .endpointOverride(myURI)
                .build();

        S3Client syncclient = S3Client.builder()
                .build();

        try {
            IntStream.range(0, 10000)
                    .boxed()
                    .parallel()
                    .forEach(
                            x -> {
                                StringBuilder sb = new StringBuilder(x.toString());
                                String key = sb.reverse().toString();
                                File teamsFile = new File(x + "-teams.json");
                                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                                        .bucket(Constants.BUCKET_NAME)
                                        .key(key)
                                        .build();


                               // syncclient.getObject(getObjectRequest,teamsFile.toPath());
                                CompletableFuture<GetObjectResponse> future = client.getObject(getObjectRequest,teamsFile.toPath());
                                future.whenComplete((resp, err) -> {
                                    try {
                                        if (resp != null) {
                                            System.out.println("Object downloaded. Details: " + resp);
                                        } else {
                                            // Handle error
                                            err.printStackTrace();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                future.join();

                            });
        }
        finally {
            client.close();
        }

    }



}


