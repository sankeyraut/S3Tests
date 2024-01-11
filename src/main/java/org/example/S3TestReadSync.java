package org.example;

import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.http.crt.AwsCrtHttpClient;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;

public class S3TestReadSync {
    public static void main(String[] args) throws  InterruptedException {


        //InstanceProfileCredentialsProvider awsCredProvider =InstanceProfileCredentialsProvider.builder().build();
        //URI myURI = new URI("https://s3express-usw2-az1.us-west-2.amazonaws.com");
        SdkHttpClient crtHttpClient = AwsCrtHttpClient.builder()
                .connectionTimeout(Duration.ofSeconds(3))
                .maxConcurrency(100)
                .build();

        S3Client client = S3Client.builder()
                .httpClient(crtHttpClient)
                .region(Region.US_WEST_2)
                .build();

        Thread.Builder builder = Thread.ofVirtual().name("S3 Read", 0);


        ArrayList<Thread> threads = new ArrayList<>();
        runThreads(builder, client, threads);
        System.out.println("All threads warmed. Running the test again");
        threads = new ArrayList<>();
        runThreads (builder, client, threads);

        client.close();


    }

    private static void runThreads( Thread.Builder builder, S3Client client, ArrayList<Thread> threads) throws InterruptedException {
        Thread thread;
        for (int i = 0; i < 50; i++) {
            thread = builder.start(new S3Read(i + "", client));
            threads.add(thread);

        }
        for (Thread availablethread : threads) {
            availablethread.join();
        }
    }


}
