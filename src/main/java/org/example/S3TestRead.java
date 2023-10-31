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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class S3TestRead {
    public static void main(String[] args) throws IOException {
        int roundId = 12345;

        ObjectMapper mapper = new ObjectMapper();

        SdkAsyncHttpClient httpClient = NettyNioAsyncHttpClient.builder()
                .connectionTimeout(Duration.ofSeconds(20))
                .connectionAcquisitionTimeout(Duration.ofSeconds(20))
                .connectionMaxIdleTime(Duration.ofSeconds(5))
                .build();

        S3AsyncClient client = S3AsyncClient.builder()
                .httpClient(httpClient)
                .build();

        S3Client syncclient = S3Client.builder()
                .build();

        try {
            IntStream.range(0, 10000)
                    .boxed()
                    .parallel()
                    .forEach(
                            x -> {

                                File teamsFile = new File(x + "-teams.json");
                                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                                        .bucket("s3test-xxx")
                                        .key(x.toString())
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


