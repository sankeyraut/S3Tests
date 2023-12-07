package org.example;

import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class S3TestReadSync {
    public static void main(String[] args) throws  InterruptedException, URISyntaxException {


        InstanceProfileCredentialsProvider awsCredProvider =InstanceProfileCredentialsProvider.builder().build();
        URI myURI = new URI("https://s3express-usw2-az1.us-west-2.amazonaws.com");
        S3Client client = S3Client.builder()
                .region(Region.US_WEST_2)
                .endpointOverride(myURI)
                //.credentialsProvider(awsCredProvider)
                .build();

        Thread.Builder builder = Thread.ofVirtual().name("S3 Read", 0);

        Thread thread =null;
        ArrayList<Thread> threads = new ArrayList<>();
        runThreads(thread, builder, client, threads);
        System.out.println("All threads warmed. Running the test again");
        threads = new ArrayList<>();
        runThreads(thread, builder, client, threads);

        client.close();


    }

    private static void runThreads(Thread thread, Thread.Builder builder, S3Client client, ArrayList<Thread> threads) throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            thread = builder.start(new S3Read(i + "", client));
            threads.add(thread);

        }
        for (Thread availablethread : threads) {
            availablethread.join();
        }
    }


}
